package jp.co.nakagawa.salonworkpro.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.nakagawa.salonworkpro.repository.dto.SCR005D1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR005D2;
import jp.co.nakagawa.salonworkpro.service.SCR005S1;

@Controller
@RequestMapping("/scr005")
public class SCR005C1 {
	private final SCR005S1 service;
	public SCR005C1(SCR005S1 service) { this.service = service; }
	@GetMapping public String init() { return "scr005/SCR005C1"; }
	@GetMapping("/menu") @ResponseBody public List<SCR005D1> findMenus() { return service.findMenuList(); }
	@GetMapping("/menu/{menuId}") @ResponseBody public SCR005D1 findMenu(@PathVariable String menuId) { return service.findMenuDetail(menuId); }
	@PostMapping("/menu") @ResponseBody public String createMenu(@RequestBody SCR005D2 request) { validate(request); return service.createMenu(request); }
	@PutMapping("/menu/{menuId}") @ResponseBody public void updateMenu(@PathVariable String menuId, @RequestBody SCR005D2 request) { validate(request); service.updateMenu(menuId, request); }
	private void validate(SCR005D2 request) {
		if (request.menuName() == null || request.menuName().isBlank() || request.durationMinute() == null || request.durationMinute() <= 0 || request.price() == null || request.price() < 0) {
			throw new IllegalArgumentException("メニュー名、施術目安時間（1分以上）、料金（0円以上）は必須です。");
		}
	}
}
