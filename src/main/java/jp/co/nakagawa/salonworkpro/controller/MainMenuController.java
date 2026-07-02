package jp.co.nakagawa.salonworkpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scr002")
public class MainMenuController {

	@GetMapping
	public String init() {

		return "scr002/menu";

	}

}