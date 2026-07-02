package jp.co.nakagawa.salonworkpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	@GetMapping("/scr001")
	public String init() {
		return "scr001/login";
	}

	@PostMapping("/login")
	public String login() {
		return "redirect:/scr002";

	}

}