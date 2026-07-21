/*
==================================================
初期処理
==================================================
*/

document.addEventListener(
    "DOMContentLoaded",
    () => {

        const keyword =
            document.getElementById(
                "keyword");

        if (keyword) {

            keyword.addEventListener(
                "input",
                searchCustomer);

        }

    });


/*
==================================================
検索
==================================================
*/

function searchCustomer() {

    const keyword =
        document.getElementById(
            "keyword")
            .value;

    console.log(
        "検索："
        + keyword);

    // TODO
    // 顧客一覧のあいまい検索APIを呼び出す

}


/*
==================================================
新規登録モーダル
==================================================
*/

function openCreateModal() {

    document
        .getElementById(
            "createCustomerName")
        .value = "";

    document
        .getElementById(
            "createModal")
        .classList
        .add(
            "show");

}

function closeCreateModal() {

    document
        .getElementById(
            "createModal")
        .classList
        .remove(
            "show");

}


/*
==================================================
編集モーダル
==================================================
*/

function openEditModal(
        element) {

    const customerId =
        element.dataset.id;

    const customerName =
        element.cells[0]
            .innerText;

    document
        .getElementById(
            "customerId")
        .value =
        customerId;

    document
        .getElementById(
            "editCustomerName")
        .value =
        customerName;

    document
        .getElementById(
            "editModal")
        .classList
        .add(
            "show");

}

function closeEditModal() {

    document
        .getElementById(
            "editModal")
        .classList
        .remove(
            "show");

}


/*
==================================================
モーダル外クリック
==================================================
*/

window.onclick =
    function(
            event) {

        const createModal =
            document
                .getElementById(
                    "createModal");

        const editModal =
            document
                .getElementById(
                    "editModal");

        if (event.target ==
                createModal) {

            closeCreateModal();

        }

        if (event.target ==
                editModal) {

            closeEditModal();

        }

    };