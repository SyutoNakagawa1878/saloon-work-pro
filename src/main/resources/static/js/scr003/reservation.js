'use strict';

function changeDate(addDay) {
    const targetDate = document.getElementById('targetDate');
    const currentDate = new Date(targetDate.value + 'T00:00:00');
    currentDate.setDate(currentDate.getDate() + addDay);

    const yyyy = currentDate.getFullYear();
    const mm = String(currentDate.getMonth() + 1).padStart(2, '0');
    const dd = String(currentDate.getDate()).padStart(2, '0');
    location.href = `/scr003?targetDate=${yyyy}-${mm}-${dd}`;
}

function openCreateModal(targetTime) {
    document.getElementById('startTime').value = targetTime;
    document.getElementById('reservationModal').classList.add('show');
}

function closeReservationModal() {
    document.getElementById('reservationModal').classList.remove('show');
    document.getElementById('startTime').value = '';
    document.getElementById('customerSearch').value = '';
    document.getElementById('customerId').value = '';
    document.getElementById('menuId').value = '';
    document.getElementById('customerSuggest').innerHTML = '';
    document.getElementById('customerSuggest').style.display = 'none';
}

async function saveReservation() {
    const targetDate = document.getElementById('targetDate').value;
    const startTime = document.getElementById('startTime').value;
    const customerId = document.getElementById('customerId').value;
    const menuId = document.getElementById('menuId').value;





    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    };

    if (csrfToken && csrfHeader) {
        headers[csrfHeader] = csrfToken;
    }

    const body = new URLSearchParams({
        reservationDate: targetDate,
        startTime,
        customerId,
        menuId
    });

    try {
        const response = await fetch('/scr003/reservation', {
            method: 'POST',
            headers,
            body
        });

        if (!response.ok) {
            const message = await response.text();
            throw new Error(message);
        }

        location.reload();
    } catch (error) {
        const errorArea =
            document.getElementById("reservationError");
        errorArea.style.display = "none";
        errorArea.textContent = "";
        errorArea.textContent = error.message;
        errorArea.style.display = "block";
    }
}

// 予約済み枠のdata属性を読み取り、確認モーダルへ表示する。
function openEditModal(element) {
    document.getElementById('reservationId').value = element.dataset.id;
    document.getElementById('editCustomer').textContent = `${element.dataset.customer} 様`;
    document.getElementById('editMenu').textContent = element.dataset.menu;
    document.getElementById('editTimeRange').textContent =
        `${element.dataset.startTime} ～ ${element.dataset.endTime}`;
    document.getElementById('editModal').classList.add('show');
}

function closeEditModal() {
    document.getElementById('editModal').classList.remove('show');
}

async function deleteReservation() {
    const reservationId = document.getElementById('reservationId').value;
//    const confirmed = confirm('この予約を削除します。よろしいですか？');
//
//    if (!confirmed) {
//        return;
//    }

    try {
        const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
        const headers = csrfToken && csrfHeader ? { [csrfHeader]: csrfToken } : {};
        const response = await fetch(`/scr003/reservation/${reservationId}`, {
            method: 'DELETE',
            headers
        });

        if (!response.ok) {
            throw new Error('削除に失敗しました。');
        }

        location.reload();
    } catch (error) {
        alert(error.message);
    }
}

window.addEventListener('click', (event) => {
    if (event.target === document.getElementById('reservationModal')) {
        closeReservationModal();
    }
    if (event.target === document.getElementById('editModal')) {
        closeEditModal();
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const customerSearch = document.getElementById('customerSearch');
    if (customerSearch) {
        customerSearch.addEventListener('input', searchCustomer);
    }
});

async function searchCustomer() {
    const keyword = document.getElementById('customerSearch').value;
    const suggest = document.getElementById('customerSuggest');

    if (!keyword) {
        suggest.style.display = 'none';
        return;
    }

    const response = await fetch(
        `/scr003/customer/search?keyword=${encodeURIComponent(keyword)}`);
    const customers = await response.json();
    suggest.innerHTML = '';

    customers.forEach((customer) => {
        const item = document.createElement('div');
        item.className = 'suggest-item';
        item.textContent = customer.customerName;
        item.addEventListener('click', () => {
            document.getElementById('customerSearch').value = customer.customerName;
            document.getElementById('customerId').value = customer.customerId;
            suggest.style.display = 'none';
        });
        suggest.appendChild(item);
    });

    suggest.style.display = customers.length > 0 ? 'block' : 'none';
}
