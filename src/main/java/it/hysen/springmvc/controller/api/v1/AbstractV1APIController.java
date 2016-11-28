package it.hysen.springmvc.controller.api.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.hysen.springmvc.controller.api.AbstractAPIController;

@Controller
@RequestMapping("/api/v1")
public abstract class AbstractV1APIController extends AbstractAPIController {
	
}
