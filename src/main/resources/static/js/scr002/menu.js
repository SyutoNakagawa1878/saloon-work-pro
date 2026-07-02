/**
 * ============================================
 * SCR-002 メインメニュー
 * menu.js
 * ============================================
 */

'use strict';

/**
 * 画面初期表示
 */
document.addEventListener('DOMContentLoaded', () => {

    console.log('SCR002 メインメニュー 初期表示');

    initialize();

});

/**
 * 初期化処理
 */
function initialize() {

    // ダッシュボード表示
    loadDashboard();

    // イベント登録
    initializeEvents();

}

/**
 * イベント登録
 */
function initializeEvents() {

    // 将来拡張用
    console.log('イベント登録完了');

}

/**
 * ダッシュボード取得
 */
function loadDashboard() {

    console.log('ダッシュボード取得開始');

    // TODO:
    // SCR002MainMenuController
    // ↓
    // SCR002MainMenuService
    // ↓
    // ReservationRepository
    // TodoRepository

    const dashboard = [
        {
            type: '予約',
            time: '11:00',
            text: '中川柊人様（眉毛WAX）'
        },
        {
            type: 'ToDo',
            time: '12:30',
            text: 'タオルの補充・洗濯'
        }
    ];

    renderDashboard(dashboard);

}

/**
 * ダッシュボード描画
 */
function renderDashboard(list) {

    const container =
        document.getElementById('dash-contents');

    if (!container) {
        return;
    }

    container.innerHTML = '';

    list.forEach(item => {

        const div =
            document.createElement('div');

        div.className = 'dash-item';

        div.innerHTML =
            '<strong>'
            + getIcon(item.type)
            + ' '
            + item.type
            + ' '
            + item.time
            + '</strong>'
            + '：'
            + item.text;

        container.appendChild(div);

    });

}

/**
 * アイコン取得
 */
function getIcon(type) {

    switch (type) {

        case '予約':
            return '📅';

        case 'ToDo':
            return '✅';

        default:
            return '📌';
    }

}

/**
 * ログアウト
 */
function logout() {

    const result =
        confirm('ログアウトしますか？');

    if (result) {

        location.href = '/scr001';

    }

}

/**
 * API呼び出し（将来実装）
 */
async function callApi(url, method = 'GET') {

    try {

        const response =
            await fetch(url, {
                method: method
            });

        return await response.json();

    } catch (error) {

        console.error(error);

        return null;

    }

}