package jp.co.nakagawa.salonworkpro.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.nakagawa.salonworkpro.controller.view.SCR003V1;
import jp.co.nakagawa.salonworkpro.entity.SCR003E2;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR003D2;
import jp.co.nakagawa.salonworkpro.service.CustomerService;
import jp.co.nakagawa.salonworkpro.service.SCR003S1;

/**
 * SCR-003 予約管理画面を担当するコントローラー。
 *
 * コントローラーは、ブラウザからのアクセスを受け取り、
 * 必要なデータを取得して画面へ渡す役割を持つクラスです。
 */
@Controller
@RequestMapping("/scr003") // このクラスのURLは「/scr003」から始まる
public class SCR003C1 {

	/**
	 * 予約に関する処理を担当するサービス。
	 *
	 * @Autowired により、Springが用意したReservationServiceを
	 * 自動でこの変数に設定します。
	 */
	@Autowired
	private SCR003S1 reservationService;

	/**
	 * 予約管理画面を表示する処理。
	 *
	 * @GetMapping は、ブラウザからのGETリクエストを受け取る設定です。
	 */
	@GetMapping
	public String init(
			// URLの「targetDate」パラメータを受け取る。
			// required = false のため、指定されていなくてもエラーにしない。
			@RequestParam(required = false) LocalDate targetDate,
			// 画面（HTML）に値を渡すための入れ物。
			Model model) {

		// 日付が指定されていない場合は、今日の日付を使用する。
		if (targetDate == null) {
			targetDate = LocalDate.now();
		}

		// 指定日の予約情報を、画面表示用の時間割形式で取得する。
		List<SCR003V1> reservationTable = reservationService.createTimeTable(targetDate);
		List<SCR003D2> menuList = reservationService.findMenuList();

		// 指定日の曜日を取得する。
		DayOfWeek dayOfWeek = targetDate.getDayOfWeek();

		// DayOfWeekの番号（月曜=1～日曜=7）に対応する日本語の曜日名。
		String[] weeks = {
				"月", "火", "水", "木", "金", "土", "日"
		};

		// HTML側で「reservationTable」という名前で予約一覧を利用できるようにする。
		model.addAttribute("reservationTable", reservationTable);
		model.addAttribute("menuList", menuList);

		// 画面に表示する日付文字列を作成する。
		model.addAttribute(
				"displayDate",
				targetDate.getYear()
						+ "年"
						+ targetDate.getMonthValue()
						+ "月"
						+ targetDate.getDayOfMonth()
						+ "日"
						+ "("
						+ weeks[dayOfWeek.getValue() - 1]
						+ ")");

		// HTML側で、現在表示中の日付を利用できるようにする。
		model.addAttribute("targetDate", targetDate);

		// 「templates/scr003/reservation.html」を表示する。
		return "scr003/SCR003C1";
	}

	/**
	 * 顧客に関する処理を担当するサービス。
	 */
	@Autowired
	private CustomerService customerService;

	/**
	 * 顧客検索API。
	 *
	 * 例：
	 * /scr003/customer/search?keyword=中川
	 *
	 * @ResponseBody により、画面HTMLではなく検索結果をJSON形式で返す。
	 * JavaScriptから非同期で呼び出す用途を想定しています。
	 */
	@GetMapping("/customer/search")
	@ResponseBody
	public List<SCR003E2> searchCustomer(
			// URLのkeywordパラメータを検索キーワードとして受け取る。
			String keyword) {

		// サービスに検索処理を依頼し、該当する顧客一覧を返す。
		return customerService.search(keyword);
	}

	@DeleteMapping("/reservation/{reservationId}")
	@ResponseBody
	public void deleteReservation(
			@PathVariable Long reservationId) {
		reservationService.deleteReservation(reservationId);
	}

	@PostMapping("/reservation")
	@ResponseBody
	public ResponseEntity<String> registerReservation(
	        @RequestParam LocalDate reservationDate,
	        @RequestParam LocalTime startTime,
	        @RequestParam String customerId,
	        @RequestParam String menuId) {

	    try {
	    	
	    	// 必須チェック
			// 施術開始時刻、顧客ID、メニューIDが未設定の場合にエラー
			if(startTime == null || customerId.isEmpty() || menuId.isEmpty()) {
				throw new IllegalArgumentException("開始時間・顧客・メニューを選択してください。");
			}

	        reservationService.registerReservation(
	                reservationDate,
	                startTime,
	                customerId,
	                menuId);

	        return ResponseEntity.ok("登録しました。");

	    } catch (IllegalArgumentException e) {

	        return ResponseEntity
	                .badRequest()
	                .body(e.getMessage());
	    }
	}	
}
