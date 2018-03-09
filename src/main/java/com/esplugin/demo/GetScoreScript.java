package com.esplugin.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.script.AbstractDoubleSearchScript;

public class GetScoreScript extends AbstractDoubleSearchScript {

    private final static Logger logger = LogManager.getLogger(GetScoreScript.class);

    private String first;
    private String second;

    GetScoreScript(String str1, String str2) {
        first = str1;
        second = str2;
    }

    /**
     * 计算单元
     * */
    @Override
    public double runAsDouble() {
        logger.info("into success!");
        // 获取es结构数据
        String test = (String) source().get("test");

        logger.info("get result test: {} ， first : {} ，send : {}",test , first , second);
        if (first.contains("test")&&test.contains(second))
            return 1;
        return Double.MAX_VALUE;
    }

}
