package webroot.websrv.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	/*
	 *Is out of the package auth but the mapping is inside /auth, so can be accessible without
	 *authentication 
	 */
	@RequestMapping("/api/hi")
	public String hi() {
		return "Hello World from Restful API";
	}
}