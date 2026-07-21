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
            'startTime'
        )
        .value =
        targetTime;

    document
        .getElementById(
            'reservationModal')
        .classList
        .add(
            'show'
        );

}

/**
 * 新規予約モーダル閉じる
 */

function closeReservationModal() {

    document
        .getElementById(
            'reservationModal')
        .classList
        .remove(
            'show');
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

    closeReservationModal();

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
    element) {

    const reservationId =
        element.dataset.id;

    const customer =
        element.dataset.customer;

    const menu =
        element.dataset.menu;

    const time =
        element.dataset.time;

    document
        .getElementById(
            'reservationId')
        .value =
        reservationId;

    document
        .getElementById(
            'editCustomer')
        .value =
        customer;

    document
        .getElementById(
            'editMenu')
        .value =
        menu;

    document
        .getElementById(
            'editStartTime')
        .value =
        time;

    document
        .getElementById(
            'editModal')
        .classList
        .add(
            'show');
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

            closeReservationModal();

        }

        if (
            event.target
            === edit
        ) {

            closeEditModal();

        }

    };

document
    .addEventListener(
        "DOMContentLoaded",
        () => {

            console.log(
                "登録完了");

            document
                .getElementById(
                    "customerSearch")
                .addEventListener(
                    "input",
                    searchCustomer);
        });

async function searchCustomer() {

    const keyword =
        document
            .getElementById(
                "customerSearch")
            .value;

    const suggest =
        document
            .getElementById(
                "customerSuggest");

    if (!keyword) {

        suggest.style.display =
            "none";

        return;
    }

    const response =
        await fetch(
            "/scr003/customer/search?keyword="
            + encodeURIComponent(
                keyword));

    const customers =
        await response.json();

    suggest.innerHTML =
        "";

    customers.forEach(
        customer => {

            const div =
                document
                    .createElement(
                        "div");

            div.className =
                "suggest-item";

            div.textContent =
                customer.customerName;

            div.onclick =
                function() {

                    document
                        .getElementById(
                            "customerSearch")
                        .value =
                        customer.customerName;

                    document
                        .getElementById(
                            "customerId")
                        .value =
                        customer.customerId;

                    suggest.style.display =
                        "none";
                };

            suggest
                .appendChild(
                    div);
        });

    suggest.style.display =
        customers.length > 0
            ? "block"
            : "none";
}

