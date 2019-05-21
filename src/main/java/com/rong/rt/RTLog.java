package com.rong.rt;

import org.apache.logging.log4j.Logger;

public class RTLog {
	
	public static Logger logger;

    public static void init(Logger modLogger) {
        logger = modLogger;
    }

}
