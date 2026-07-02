/**
 * ==========================================
 * SCR-003 予約管理
 * reservation.js
 * ==========================================
 */

'use strict';

/**
 * 初期表示
 */
document.addEventListener('DOMContentLoaded', () => {

    console.log('SCR003 初期表示');

    initialize();

});

/**
 * 初期化
 */
function initialize() {

    loadReservations();

}

///**
// * 日付表示
// */
//function displayDate() {
//
//    const week = [
//        '日',
//        '月',
//        '火',
//        '水',
//        '木',
//        '金',
//        '土'
//    ];
//
//    const text =
//        currentDate.getFullYear()
//        + '年'
//        + (currentDate.getMonth() + 1)
//        + '月'
//        + currentDate.getDate()
//        + '日'
//        + '('
//        + week[currentDate.getDay()]
//        + ')';
//
//    const target =
//        document.getElementById(
//            'currentDate'
//        );
//
//    if (target) {
//
//        target.textContent =
//            text;
//
//    }
//
//}

/**
 * 日付変更
 */
function changeDate(addDay) {

    const target =
        document.getElementById(
            'targetDate');

    console.log(target.value);

    const currentDate =
        new Date(
            target.value + 'T00:00:00');

    currentDate.setDate(
        currentDate.getDate()
        + addDay);

    const yyyy =
        currentDate.getFullYear();

    const mm =
        String(
            currentDate.getMonth() + 1)
            .padStart(2, '0');

    const dd =
        String(
            currentDate.getDate())
            .padStart(2, '0');

    location.href =
        '/scr003?targetDate='
        + yyyy
        + '-'
        + mm
        + '-'
        + dd;
}

/**
 * 予約取得
 */
function loadReservations() {

    console.log(
        '予約取得'
    );

}

/**
 * ==========================================
 * 新規予約モーダル
 * ==========================================
 */

/**
 * 新規予約モーダル表示
 */
function openCreateModal(
    targetTime = ''
) {

    console.log(
        '新規予約',
        targetTime
    );

    document
        .getElementById(
            'createTime'
        )
        .value =
        targetTime;

    document
        .getElementById(
            'createModal'
        )
        .classList
        .add(
            'show'
        );

}

/**
 * 新規予約モーダル閉じる
 */
function closeCreateModal() {

    document
        .getElementById(
            'createModal'
        )
        .classList
        .remove(
            'show'
        );

}

/**
 * 登録
 */
function saveReservation() {

    const customer =
        document
            .getElementById(
                'customerId'
            )
            .value;

    if (!customer) {

        alert(
            '顧客を選択してください。'
        );

        return;

    }

    console.log(
        '予約登録'
    );

    // TODO
    // POST
    // /scr003/create

    closeCreateModal();

}

/**
 * ==========================================
 * 編集モーダル
 * ==========================================
 */

/**
 * 編集モーダル表示
 */
function openEditModal(
    reservationId
) {

    console.log(
        '予約編集',
        reservationId
    );

    document
        .getElementById(
            'reservationId'
        )
        .value =
        reservationId;

    // 仮データ
    document
        .getElementById(
            'editCustomer'
        )
        .value =
        '中川柊人';

    document
        .getElementById(
            'editMenu'
        )
        .value =
        '眉毛WAX';

    document
        .getElementById(
            'editMemo'
        )
        .value =
        '';

    document
        .getElementById(
            'editModal'
        )
        .classList
        .add(
            'show'
        );

}

/**
 * 編集モーダル閉じる
 */
function closeEditModal() {

    document
        .getElementById(
            'editModal'
        )
        .classList
        .remove(
            'show'
        );

}

/**
 * 更新
 */
function updateReservation() {

    const id =
        document
            .getElementById(
                'reservationId'
            )
            .value;

    console.log(
        '更新',
        id
    );

    // TODO
    // POST
    // /scr003/update

    closeEditModal();

}

/**
 * 削除
 */
function deleteReservation() {

    const result =
        confirm(
            '削除しますか？'
        );

    if (!result) {

        return;

    }

    const id =
        document
            .getElementById(
                'reservationId'
            )
            .value;

    console.log(
        '削除',
        id
    );

    // TODO
    // POST
    // /scr003/delete

    closeEditModal();

}

/**
 * ==========================================
 * モーダル外クリック
 * ==========================================
 */
window.onclick =
    function(event) {

        const create =
            document
                .getElementById(
                    'createModal'
                );

        const edit =
            document
                .getElementById(
                    'editModal'
                );

        if (
            event.target
            === create
        ) {

            closeCreateModal();

        }

        if (
            event.target
            === edit
        ) {

            closeEditModal();

        }

    };