/**
 * ============================================
 * Salon Work Pro
 * ログイン画面 JavaScript
 * ============================================
 */

'use strict';

/**
 * 画面初期表示
 */
document.addEventListener('DOMContentLoaded', () => {

    // ログインIDへフォーカス
    document.getElementById('loginId').focus();

    initializeEvents();

});

/**
 * イベント登録
 */
function initializeEvents() {

    const form = document.querySelector("form");
    const loginButton = document.querySelector(".login-button");

    // ログインボタン押下
    loginButton.addEventListener("click", (event) => {

        if (!validate()) {

            event.preventDefault();
            return;

        }

        login();

    });

    // Enterキー対応
    form.addEventListener("keydown", (event) => {

        if (event.key === "Enter") {

            if (!validate()) {

                event.preventDefault();
                return;

            }

            login();

        }

    });

}

/**
 * 入力チェック
 */
function validate() {

    const loginId = document.getElementById("loginId").value.trim();
    const password = document.getElementById("password").value.trim();

    if (loginId === "") {

        alert("ログインIDを入力してください。");

        document.getElementById("loginId").focus();

        return false;

    }

    if (password === "") {

        alert("パスワードを入力してください。");

        document.getElementById("password").focus();

        return false;

    }

    return true;

}

function login() {

    const button =
        document.querySelector(".login-button");

    button.disabled = true;

    button.textContent = "ログイン中...";

    document
        .getElementById("loginForm")
        .submit();

}