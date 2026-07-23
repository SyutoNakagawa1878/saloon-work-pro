package jp.co.nakagawa.salonworkpro.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D7;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D8;
import jp.co.nakagawa.salonworkpro.service.SCR004S1;

@Controller
@RequestMapping("/scr004")
public class SCR004C1 {

	private final SCR004S1 customerService;

	public SCR004C1(SCR004S1 customerService) {
		this.customerService = customerService;
	}

	@GetMapping
	public String init(Model model) {
		model.addAttribute("genderList", customerService.findGenderList());
		model.addAttribute("jobList", customerService.findJobList());
		return "scr004/SCR004C1";
	}

	@GetMapping("/customer/search")
	@ResponseBody
	public List<SCR004D1> searchCustomer(
			@RequestParam(required = false) String customerName,
			@RequestParam(required = false) String genderCodes,
			@RequestParam(required = false) Integer ageFrom,
			@RequestParam(required = false) Integer ageTo) {
		return customerService.searchCustomer(
				customerName, genderCodes, ageFrom, ageTo);
	}

	@GetMapping("/customer/{customerId}")
	@ResponseBody
	public SCR004D7 customerDetail(@PathVariable String customerId) {
		return customerService.findCustomerDetail(customerId);
	}

	@PostMapping("/customer")
	@ResponseBody
	public String createCustomer(@RequestBody SCR004D8 request) {
		return customerService.createCustomer(request);
	}

	@PutMapping("/customer/{customerId}")
	@ResponseBody
	public void updateCustomer(@PathVariable String customerId, @RequestBody SCR004D8 request) {
		customerService.updateCustomer(customerId, request);
	}
}
