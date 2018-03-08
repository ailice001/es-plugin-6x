package com.esplugin.demo;


import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.ScriptPlugin;
import org.elasticsearch.script.NativeScriptFactory;

public class DemoPlugin extends Plugin implements ActionPlugin, ScriptPlugin {
    private final static Logger logger = LogManager.getLogger(DemoPlugin.class);

    // 初始化插件
    public DemoPlugin() {
        super();
        logger.warn("Create the demo Score Plugin success !");
    }  

    // lang  使用  native
    public List<NativeScriptFactory> getNativeScripts() {
        return Collections.<NativeScriptFactory>singletonList(new DemoPluginHandle());
    }  
}
