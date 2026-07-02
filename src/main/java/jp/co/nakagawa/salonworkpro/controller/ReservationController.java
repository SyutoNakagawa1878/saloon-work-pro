package jp.co.nakagawa.salonworkpro.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.nakagawa.salonworkpro.controller.view.ReservationView;
import jp.co.nakagawa.salonworkpro.service.ReservationService;

/**
 * ============================================
 * SCR-003 予約管理
 * ============================================
 */
@Controller
@RequestMapping("/scr003")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@GetMapping
	public String init(
			@RequestParam(required = false) LocalDate targetDate,
			Model model) {

		System.out.println(
				"受信="
						+ targetDate);

		if (targetDate == null) {

			targetDate = LocalDate.now();

		}

		System.out.println(
				"使用="
						+ targetDate);

		List<ReservationView> reservationTable = reservationService
				.createTimeTable(
						targetDate);

		model.addAttribute(
				"reservationTable",
				reservationTable);

		model.addAttribute(
				"displayDate",
				targetDate.getYear()
						+ "年"
						+ targetDate.getMonthValue()
						+ "月"
						+ targetDate.getDayOfMonth()
						+ "日");

		model.addAttribute(
				"targetDate",
				targetDate);

		return "scr003/reservation";
	}

}