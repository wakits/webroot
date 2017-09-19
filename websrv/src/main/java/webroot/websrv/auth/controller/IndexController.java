package webroot.websrv.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /**
     * @return index page
     */
    @GetMapping({"/"})
    public String index() {
    		return "forward:index.html";
    }
    
    @RequestMapping("/init")
    public String init() {
    		System.out.println("go to index.html");
        return "forward:index.html";
    }

}
