'use strict';

let currentCustomerDetail = null;

async function searchCustomer() {
    const params = new URLSearchParams();
    const name = document.getElementById('customerName').value.trim();
    const genders = [...document.querySelectorAll('input[name="gender"]:checked')].map(item => item.value).join(',');
    const ageFrom = document.getElementById('ageFrom').value;
    const ageTo = document.getElementById('ageTo').value;
    if (name) params.set('customerName', name);
    if (genders) params.set('genderCodes', genders);
    if (ageFrom) params.set('ageFrom', ageFrom);
    if (ageTo) params.set('ageTo', ageTo);
    const response = await fetch(`/scr004/customer/search?${params}`);
    if (!response.ok) return;
    renderCustomers(await response.json());
}

function renderCustomers(customers) {
    const list = document.getElementById('customerList');
    const message = document.getElementById('searchMessage');
    list.innerHTML = '';
    message.textContent = customers.length ? `${customers.length}件の顧客が見つかりました。` : '該当する顧客はいません。';
    customers.forEach(customer => {
        const item = document.createElement('button');
        item.type = 'button'; item.className = 'customer-item';
        item.innerHTML = `<span>${escapeHtml(customer.customerName)} ${customer.age ?? '-'}歳 / ${escapeHtml(customer.gender)} / ${escapeHtml(customer.job)}</span><span class="customer-arrow">›</span>`;
        item.addEventListener('click', () => openDetailModal(customer.customerId));
        list.appendChild(item);
    });
}

async function openDetailModal(customerId) {
    const response = await fetch(`/scr004/customer/${encodeURIComponent(customerId)}`);
    if (!response.ok) return;
    currentCustomerDetail = await response.json();
    const profile = currentCustomerDetail.profile;
    const features = currentCustomerDetail.features || [];
    document.getElementById('detailProfile').innerHTML = `<div class="info-block-title">基本情報</div><strong>顧客名：</strong>${escapeHtml(profile.customerName)}<br><strong>年齢：</strong>${profile.age ?? '-'}歳（${profile.birthday ?? '未登録'}）<br><strong>性別：</strong>${escapeHtml(profile.gender)}<br><strong>職業：</strong>${escapeHtml(profile.job)}<br><strong>お住まいの場所：</strong>${escapeHtml(profile.location || '未登録')}<br><strong>特徴：</strong>${features.length ? features.map(item => `${escapeHtml(item.part)}：${escapeHtml(item.feature)}`).join('<br>') : '未登録'}`;
    document.getElementById('detailReservationHistory').innerHTML = historyHtml(currentCustomerDetail.reservations, item => `<div class="history-date">${item.reservationDate} ${item.startTime}〜${item.endTime}</div><strong>${escapeHtml(item.menuName)}</strong>${item.memo ? `<br>${escapeHtml(item.memo)}` : ''}`, '利用履歴はありません。');
    document.getElementById('detailHobbies').innerHTML = currentCustomerDetail.hobbies.length ? currentCustomerDetail.hobbies.map(item => `<span class="hobby-badge">${escapeHtml(item.hobby)}${item.hobbyDetail ? `：${escapeHtml(item.hobbyDetail)}` : ''}</span>`).join('') : '<p class="empty-detail">趣味・嗜好は未登録です。</p>';
    document.getElementById('detailTalkHistories').innerHTML = historyHtml(currentCustomerDetail.talkHistories, item => `<div class="history-date">${item.visitDate}</div>${escapeHtml(item.talkContent)}`, '会話履歴はありません。');
    openModal('detailModal');
}

function historyHtml(items, content, emptyMessage) { return items.length ? items.map(item => `<div class="history-item">${content(item)}</div>`).join('') : `<p class="empty-detail">${emptyMessage}</p>`; }
function closeDetailModal() { closeModal('detailModal'); }

function openCustomerForm(detail = null) {
    closeDetailModal();
    const profile = detail?.profile;
    document.getElementById('customerFormTitle').textContent = profile ? '顧客情報の編集' : '新規顧客登録';
    document.getElementById('formCustomerName').value = profile?.customerName ?? '';
    document.getElementById('formCustomerNameKana').value = profile?.customerNameKana ?? '';
    document.getElementById('formBirthday').value = profile?.birthday ?? '';
    document.getElementById('formGender').value = profile?.genderCode ?? '';
    document.getElementById('formJob').value = profile?.jobCode ?? '';
    document.getElementById('formLocation').value = profile?.location ?? '';
    clearCustomerError();
    document.getElementById('featureRows').innerHTML = '';
    document.getElementById('hobbyRows').innerHTML = '';
    document.getElementById('talkRows').innerHTML = '';
    (detail?.features ?? []).forEach(addFeatureRow);
    (detail?.hobbies ?? []).forEach(addHobbyRow);
    (detail?.talkHistories ?? []).forEach(addTalkRow);
    if (!(detail?.features?.length)) addFeatureRow();
    if (!(detail?.hobbies?.length)) addHobbyRow();
    if (!(detail?.talkHistories?.length)) addTalkRow();
    document.getElementById('customerFormModal').dataset.customerId = profile?.customerId ?? '';
    openModal('customerFormModal');
}

function addFeatureRow(feature = {}) { addRow('featureRows', `<input class="feature-part" placeholder="部位" value="${escapeHtml(feature.part ?? '')}"><input class="feature-detail" placeholder="詳細" value="${escapeHtml(feature.feature ?? '')}">`); }
function addHobbyRow(hobby = {}) { addRow('hobbyRows', `<input class="hobby-name" placeholder="趣味" value="${escapeHtml(hobby.hobby ?? '')}"><input class="hobby-detail" placeholder="詳細" value="${escapeHtml(hobby.hobbyDetail ?? '')}">`); }
function addTalkRow(talk = {}) { addRow('talkRows', `<input class="talk-date" type="date" value="${talk.visitDate ?? ''}"><input class="talk-content" placeholder="会話内容" value="${escapeHtml(talk.talkContent ?? '')}">`, 'talk-row'); }
function addRow(containerId, fields, extraClass = '') { const row = document.createElement('div'); row.className = `dynamic-row ${extraClass}`; row.innerHTML = `${fields}<button type="button" class="btn-mini-del">×</button>`; row.querySelector('button').addEventListener('click', () => row.remove()); document.getElementById(containerId).appendChild(row); }

async function saveCustomer() {
    const modal = document.getElementById('customerFormModal');
    const customerId = modal.dataset.customerId;
    const payload = {
        customerName: document.getElementById('formCustomerName').value.trim(), customerNameKana: document.getElementById('formCustomerNameKana').value.trim(),
        birthday: document.getElementById('formBirthday').value || null, gender: document.getElementById('formGender').value, job: document.getElementById('formJob').value,
        location: document.getElementById('formLocation').value.trim(),
        features: [...document.querySelectorAll('#featureRows .dynamic-row')].map(row => ({ part: row.querySelector('.feature-part').value.trim(), feature: row.querySelector('.feature-detail').value.trim() })),
        hobbies: [...document.querySelectorAll('#hobbyRows .dynamic-row')].map(row => ({ hobby: row.querySelector('.hobby-name').value.trim(), hobbyDetail: row.querySelector('.hobby-detail').value.trim() })),
        talkHistories: [...document.querySelectorAll('#talkRows .dynamic-row')].map(row => ({ visitDate: row.querySelector('.talk-date').value || null, talkContent: row.querySelector('.talk-content').value.trim() }))
    };
    clearCustomerError();
    try {
        const response = await fetch(customerId ? `/scr004/customer/${encodeURIComponent(customerId)}` : '/scr004/customer', { method: customerId ? 'PUT' : 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
        if (!response.ok) throw new Error((await response.text()) || '顧客情報の保存に失敗しました。');
        closeCustomerForm(); await searchCustomer();
    } catch (error) { showCustomerError(error.message); }
}

function showCustomerError(message) { const area = document.getElementById('customerError'); area.textContent = message; area.style.display = 'block'; }
function clearCustomerError() { const area = document.getElementById('customerError'); area.textContent = ''; area.style.display = 'none'; }
function closeCustomerForm() { closeModal('customerFormModal'); }
function openModal(id) { document.getElementById(id).classList.add('show'); document.body.classList.add('modal-open'); }
function closeModal(id) { document.getElementById(id).classList.remove('show'); if (!document.querySelector('.modal.show')) document.body.classList.remove('modal-open'); }
function escapeHtml(value) { const node = document.createElement('div'); node.textContent = value ?? ''; return node.innerHTML; }
window.addEventListener('click', event => { if (event.target.classList.contains('modal')) closeModal(event.target.id); });
document.addEventListener('DOMContentLoaded', () => { document.getElementById('customerName').addEventListener('keydown', event => { if (event.key === 'Enter') searchCustomer(); }); searchCustomer(); });
