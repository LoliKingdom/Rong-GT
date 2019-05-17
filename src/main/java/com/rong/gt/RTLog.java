package com.rong.gt;

import org.apache.logging.log4j.Logger;

public class RTLog {
	
	public static Logger logger;

    public static void init(Logger modLogger) {
        logger = modLogger;
    }

}
