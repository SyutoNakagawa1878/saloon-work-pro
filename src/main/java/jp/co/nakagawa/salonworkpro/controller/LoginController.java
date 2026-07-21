package jp.co.nakagawa.salonworkpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ログイン画面に関するアクセスを処理するコントローラー。
 *
 * @Controller を付けることで、Springはこのクラスを
 * ブラウザからのリクエストを受け取る担当として認識します。
 */
@Controller
public class LoginController {

	/**
	 * ログイン画面を表示する処理。
	 *
	 * ブラウザから「/scr001」へGETアクセスされたときに実行されます。
	 * 例：URLを直接開いたとき、リンクをクリックしたとき
	 *
	 * @GetMapping は、GETリクエストとこのメソッドを結び付ける設定です。
	 */
	@GetMapping("/scr001")
	public String init() {

		// templates/scr001/login.html を画面として表示する。
		return "scr001/login";
	}

	/**
	 * ログインボタン押下後の処理。
	 *
	 * フォームから「/login」へPOST送信されたときに実行されます。
	 * @PostMapping は、POSTリクエストとこのメソッドを結び付けます。
	 */
	@PostMapping("/login")
	public String login() {

		// 「redirect:」は画面を直接表示するのではなく、
		// ブラウザに別のURLへ移動するよう指示します。
		// この場合は、ログイン後に /scr002 へ移動します。
		return "redirect:/scr002";
	}
}