'use strict';

let currentCustomerDetail = null;

async function searchCustomer() {
    const params = new URLSearchParams();
    const customerName = document.getElementById('customerName').value.trim();
    const genderCodes = [...document.querySelectorAll('input[name="gender"]:checked')].map((item) => item.value).join(',');
    const ageFrom = document.getElementById('ageFrom').value;
    const ageTo = document.getElementById('ageTo').value;
    if (customerName) params.set('customerName', customerName);
    if (genderCodes) params.set('genderCodes', genderCodes);
    if (ageFrom) params.set('ageFrom', ageFrom);
    if (ageTo) params.set('ageTo', ageTo);

    const response = await fetch(`/scr004/customer/search?${params}`);
    if (!response.ok) return alert('顧客検索に失敗しました。');
    renderCustomers(await response.json());
}

function renderCustomers(customers) {
    const list = document.getElementById('customerList');
    const message = document.getElementById('searchMessage');
    list.innerHTML = '';
    if (!customers.length) {
        message.textContent = '該当する顧客はいません。';
        return;
    }
    message.textContent = `${customers.length}件の顧客が見つかりました。`;
    customers.forEach((customer) => {
        const item = document.createElement('button');
        item.type = 'button';
        item.className = 'customer-item';
        item.innerHTML = `<span>${escapeHtml(customer.customerName)} 様（${customer.age ?? '-'}歳 / ${escapeHtml(customer.gender)} / ${escapeHtml(customer.job)}）</span><span class="customer-arrow">›</span>`;
        item.addEventListener('click', () => openDetailModal(customer.customerId));
        list.appendChild(item);
    });
}

async function openDetailModal(customerId) {
    const response = await fetch(`/scr004/customer/${encodeURIComponent(customerId)}`);
    if (!response.ok) return alert('顧客詳細を取得できませんでした。');
    currentCustomerDetail = await response.json();
    const profile = currentCustomerDetail.profile;
    document.getElementById('detailProfile').innerHTML = `<div class="info-block-title">基本情報</div><strong>顧客名：</strong>${escapeHtml(profile.customerName)} 様<br><strong>年齢：</strong>${profile.age ?? '-'}歳（${profile.birthday ?? '未登録'}）<br><strong>性別：</strong>${escapeHtml(profile.gender)}<br><strong>職業：</strong>${escapeHtml(profile.job)}<br><strong>特徴：</strong>${escapeHtml(profile.feature || 'なし')}`;
    document.getElementById('detailReservationHistory').innerHTML = historyHtml(currentCustomerDetail.reservations, (item) => `<div class="history-date">${item.reservationDate} ${item.startTime}〜${item.endTime}</div><strong>${escapeHtml(item.menuName)}</strong>${item.memo ? `<br>${escapeHtml(item.memo)}` : ''}`, '利用履歴はありません。');
    document.getElementById('detailHobbies').innerHTML = currentCustomerDetail.hobbies.length ? currentCustomerDetail.hobbies.map((item) => `<span class="hobby-badge">${escapeHtml(item.hobby)}${item.hobbyDetail ? `：${escapeHtml(item.hobbyDetail)}` : ''}</span>`).join('') : '<p class="empty-detail">趣味・嗜好は未登録です。</p>';
    document.getElementById('detailTalkHistories').innerHTML = historyHtml(currentCustomerDetail.talkHistories, (item) => `<div class="history-date">${item.visitDate}</div>${escapeHtml(item.talkContent)}`, '会話履歴はありません。');
    openModal('detailModal');
}

function historyHtml(items, content, emptyMessage) {
    return items.length ? items.map((item) => `<div class="history-item">${content(item)}</div>`).join('') : `<p class="empty-detail">${emptyMessage}</p>`;
}

function closeDetailModal() { closeModal('detailModal'); }

function openCustomerForm(detail = null) {
    closeDetailModal();
    const profile = detail?.profile;
    document.getElementById('customerFormTitle').textContent = profile ? '顧客編集' : '新規顧客追加';
    document.getElementById('formCustomerName').value = profile?.customerName ?? '';
    document.getElementById('formBirthday').value = profile?.birthday ?? '';
    document.getElementById('formGender').value = profile?.genderCode ?? document.getElementById('formGender').value;
    document.getElementById('formJob').value = profile?.jobCode ?? document.getElementById('formJob').value;
    document.getElementById('formFeature').value = profile?.feature ?? '';
    document.getElementById('hobbyRows').innerHTML = '';
    document.getElementById('talkRows').innerHTML = '';
    (detail?.hobbies ?? []).forEach((item) => addHobbyRow(item));
    (detail?.talkHistories ?? []).forEach((item) => addTalkRow(item));
    if (!(detail?.hobbies?.length)) addHobbyRow();
    if (!(detail?.talkHistories?.length)) addTalkRow();
    document.getElementById('customerFormModal').dataset.customerId = profile?.customerId ?? '';
    openModal('customerFormModal');
}

function addHobbyRow(hobby = {}) {
    const row = document.createElement('div');
    row.className = 'dynamic-row';
    row.innerHTML = `<input class="hobby-name" placeholder="趣味" value="${escapeHtml(hobby.hobby ?? '')}"><input class="hobby-detail" placeholder="詳細" value="${escapeHtml(hobby.hobbyDetail ?? '')}"><button type="button" class="btn-mini-del">×</button>`;
    row.querySelector('button').addEventListener('click', () => row.remove());
    document.getElementById('hobbyRows').appendChild(row);
}

function addTalkRow(talk = {}) {
    const row = document.createElement('div');
    row.className = 'dynamic-row talk-row';
    row.innerHTML = `<input class="talk-date" type="date" value="${talk.visitDate ?? ''}"><input class="talk-content" placeholder="会話内容" value="${escapeHtml(talk.talkContent ?? '')}"><button type="button" class="btn-mini-del">×</button>`;
    row.querySelector('button').addEventListener('click', () => row.remove());
    document.getElementById('talkRows').appendChild(row);
}

async function saveCustomer() {
    const modal = document.getElementById('customerFormModal');
    const customerId = modal.dataset.customerId;
    const payload = {
        customerName: document.getElementById('formCustomerName').value.trim(),
        birthday: document.getElementById('formBirthday').value || null,
        gender: document.getElementById('formGender').value,
        job: document.getElementById('formJob').value,
        feature: document.getElementById('formFeature').value.trim(),
        hobbies: [...document.querySelectorAll('#hobbyRows .dynamic-row')].map((row) => ({ hobby: row.querySelector('.hobby-name').value.trim(), hobbyDetail: row.querySelector('.hobby-detail').value.trim() })),
        talkHistories: [...document.querySelectorAll('#talkRows .dynamic-row')].map((row) => ({ visitDate: row.querySelector('.talk-date').value || null, talkContent: row.querySelector('.talk-content').value.trim() }))
    };
    if (!payload.customerName || !payload.birthday || !payload.gender || !payload.job) return alert('顧客名・生年月日・性別・職業を入力してください。');
    const response = await fetch(customerId ? `/scr004/customer/${encodeURIComponent(customerId)}` : '/scr004/customer', { method: customerId ? 'PUT' : 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
    if (!response.ok) return alert('顧客情報の保存に失敗しました。');
    closeCustomerForm();
    await searchCustomer();
}

function closeCustomerForm() { closeModal('customerFormModal'); }
function openModal(id) { document.getElementById(id).classList.add('show'); document.body.classList.add('modal-open'); }
function closeModal(id) { document.getElementById(id).classList.remove('show'); if (!document.querySelector('.modal.show')) document.body.classList.remove('modal-open'); }
function escapeHtml(value) { const node = document.createElement('div'); node.textContent = value ?? ''; return node.innerHTML; }

window.addEventListener('click', (event) => { if (event.target.classList.contains('modal')) closeModal(event.target.id); });
document.addEventListener('DOMContentLoaded', () => document.getElementById('customerName').addEventListener('keydown', (event) => { if (event.key === 'Enter') searchCustomer(); }));
