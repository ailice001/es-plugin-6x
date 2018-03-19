package com.esplugin.demo;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.LeafReaderContext;
import org.elasticsearch.common.xcontent.support.XContentMapValues;
import org.elasticsearch.script.ScriptContext;
import org.elasticsearch.script.ScriptEngine;
import org.elasticsearch.script.SearchScript;

public class MyScriptEngine implements ScriptEngine {
    private final static Logger logger = LogManager.getLogger(MyScriptEngine.class);

    @Override
    public String getType() {
        return "demoPlugin"; // script name
    }

    @Override
    public <T> T compile(String scriptName, String scriptSource, ScriptContext<T> context, Map<String, String> params) {
        logger.info("use params the scriptName {} ,scriptSource  {}, context {},params {}",scriptName,scriptSource,context.name,params.entrySet());
        if (!context.equals(SearchScript.CONTEXT)) {
            throw new IllegalArgumentException(getType() + " scripts cannot be used for context [" + context.name + "]");
        }
        final String first = XContentMapValues.nodeStringValue(params.get("first"), null);
        final String second = XContentMapValues.nodeStringValue(params.get("second"), null);
        logger.info("this is first value : {} ,second : {} ", first, second);

        SearchScript.Factory factory = (p, lookup) -> new SearchScript.LeafFactory() {
            @Override
            public SearchScript newInstance(LeafReaderContext context) throws IOException {
                return new SearchScript(p, lookup, context) {
                    @Override
                    public double runAsDouble() {
                       final String test = (String) lookup.source().get("test");
                       if (first.equals(test)){
                           return 1.0D;
                       }
                       return Double.MAX_VALUE;
                    }
                };
            }

            @Override
            public boolean needs_score() {
                return false;
            }
        };
        return context.factoryClazz.cast(factory);
    }

    @Override
    public void close() {
        // optionally close resources
    }
}
