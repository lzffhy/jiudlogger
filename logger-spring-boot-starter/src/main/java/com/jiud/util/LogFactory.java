package com.jiud.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class LogFactory {

    /**
     * 日志记录器缓存
     *
     * @Fields logCache
     */
    private Map<String, Logger> logCache = new HashMap<String, Logger>();

    /**
     * 获取日志记录器
     * @param key
     * @return
     * @author Norton Lai
     * @created 2018-9-7 下午5:37:42
     */
    public Logger get(String key) {
        if (logCache.containsKey(key)) {
            return logCache.get(key);
        }
        synchronized (LogFactory.class) {
            if (!logCache.containsKey(key)) {
                Logger logger = LoggerFactory.getLogger(key);
                logCache.put(key, logger);
                return logger;
            }
            return logCache.get(key);
        }
    }

    /**
     * 构建实例
     * @return
     * @author Norton Lai
     * @created 2018-9-7 下午5:40:35
     */
    public static LogFactory newInstance(){
        return new LogFactory();
    }

}
