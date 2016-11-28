package it.hysen.springmvc.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserAdminController extends AdminController {
	
	@RequestMapping(value = "/user/index", method = RequestMethod.GET)
	public String getHome() {
		return "partials/admin/user/index";
	}

}
