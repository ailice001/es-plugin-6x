package com.esplugin.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.script.AbstractDoubleSearchScript;

public class GetScoreScript extends AbstractDoubleSearchScript {

    private final static Logger logger = LogManager.getLogger(GetScoreScript.class);

    private String temp1;
    private String temp2;

    GetScoreScript(String str1, String str2) {
        temp1 = str1;
        temp2 = str2;
    }

    /**
     * 计算单元
     * */
    @Override
    public double runAsDouble() {
        logger.info("into success!");
        if (temp1.contains("test")||temp2.contains("test"))
            return 1;
        return Double.MAX_VALUE;
    }

}
