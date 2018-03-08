package com.esplugin.demo;


import com.sun.istack.internal.Nullable;
import org.elasticsearch.common.xcontent.support.XContentMapValues;
import org.elasticsearch.script.ExecutableScript;
import org.elasticsearch.script.NativeScriptFactory;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DemoPluginHandle implements NativeScriptFactory {
  private final static Logger logger = LogManager.getLogger(DemoPluginHandle.class);

  /**
   *  定义传入参数
   * */
  public ExecutableScript newScript(@Nullable Map<String, Object> params) {
    logger.info("es-plugin start to get score !");
    String first = params == null ? null : XContentMapValues.nodeStringValue(params.get("firstString"), null);
    logger.info("this is first value : {} ",first);
    String send =  params == null ? null : XContentMapValues.nodeStringValue(params.get("sendBytes"), null);
    logger.info("this is first value : {} ",send);
    return new GetScoreScript(first,send);
  }

  public boolean needsScores() {
    return false;
  }

  // 定义插件名
  public String getName() {
    return "demoPlugin";
  }
}

