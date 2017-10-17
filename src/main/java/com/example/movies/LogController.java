package com.example.movies;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LogController {

	static Logger logger = Logger.getLogger("MyLog");
	static FileHandler fh;

	public static void writeLog(String sentence) {
		/*
		try {
			fh = new FileHandler("./log/log.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			Date date = new Date();
			String logInit = date.toString() + " --- " + "Call /" + RestController.SERVICE + " --- " + RestController.NAME;
			logger.info(logInit + " --- " + sentence);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

}
