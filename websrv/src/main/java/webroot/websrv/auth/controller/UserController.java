package webroot.websrv.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import webroot.websrv.auth.entity.User;
import webroot.websrv.auth.service.UserService;


@RestController
@RequestMapping("/api/auth/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;

	
	@RequestMapping(value ="/list")
//    @PreAuthorize("hasAuthority('USER')")
	@ResponseBody
	public List<User>  getAllUserByAnyName(@RequestParam("name") String name){
		List<User> users = userService.findByAnyName(name);
		System.out.println("users: " + users.size());
		return users;
	}
	@RequestMapping(value ="/")
	@ResponseBody
	public User getUserByToken(@RequestParam("token") String token) {
		User user = userService.findUserByToken(token);
		return user;
	}

}
