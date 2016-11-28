package it.hysen.springmvc.controller;

import org.apache.log4j.Logger;

public abstract class AbstractController {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	public Logger getLogger() {
		return this.logger;
	}

}
