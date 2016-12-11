package it.hysen.springmvc.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import it.hysen.springmvc.controller.AbstractController;

//@ControllerAdvice
public class ErrorHandler extends AbstractController {
	
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
	@ExceptionHandler(Exception.class)
	public String handleException(Exception exception) {
		this.getLogger().error(exception);
		return "error/handler";
	}
	
}
