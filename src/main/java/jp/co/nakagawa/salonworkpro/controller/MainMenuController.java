package jp.co.nakagawa.salonworkpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * メインメニュー画面を表示するためのコントローラー。
 *
 * @Controller により、Springはこのクラスを
 * ブラウザからのアクセスを処理するクラスとして認識します。
 */
@Controller
@RequestMapping("/scr002")
public class MainMenuController {

	/**
	 * メインメニュー画面を表示する処理。
	 *
	 * クラスに @RequestMapping("/scr002") が設定されているため、
	 * ブラウザから「/scr002」へGETアクセスされたときに実行されます。
	 *
	 * @GetMapping は、GETリクエスト（画面を開くアクセス）を
	 * このメソッドで受け取るための設定です。
	 */
	@GetMapping
	public String init() {

		// templates/scr002/menu.html を画面として表示する。
		return "scr002/menu";
	}
}