package webroot.webcli.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@RequestMapping("/api/hi")
	public String hi() {
		return "Hello World from Restful API";
	}
}