package jp.co.nakagawa.salonworkpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SCR-004 顧客管理画面を担当するコントローラー。
 */
@Controller
@RequestMapping("/scr004") // このクラスのURLは「/scr003」から始まる
public class SCR004C1 {

    @GetMapping
    public String init() {

        return "scr004/SCR004C1";

    }
}
