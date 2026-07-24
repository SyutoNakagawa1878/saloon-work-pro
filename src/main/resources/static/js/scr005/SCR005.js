'use strict';

let currentMenu = null;

async function loadMenus() {
    const response = await fetch('/scr005/menu');
    if (!response.ok) return;
    const menus = await response.json();
    const list = document.getElementById('menuList');
    list.innerHTML = '';
    document.getElementById('menuMessage').textContent = menus.length ? `${menus.length}件のメニューを表示しています。` : '登録されているメニューはありません。';
    menus.forEach(menu => {
        const item = document.createElement('button');
        item.type = 'button'; item.className = 'customer-item';
        item.innerHTML = `<span>${escapeHtml(menu.menuName)}（${menu.durationMinute ?? '-'}分 / ${formatPrice(menu.price)}）</span><span class="customer-arrow">›</span>`;
        item.addEventListener('click', () => openDetailModal(menu.menuId));
        list.appendChild(item);
    });
}

async function openDetailModal(menuId) {
    const response = await fetch(`/scr005/menu/${encodeURIComponent(menuId)}`);
    if (!response.ok) return;
    currentMenu = await response.json();
    document.getElementById('menuDetail').innerHTML = `<div class="info-block-title">基本情報</div><strong>メニュー名：</strong>${escapeHtml(currentMenu.menuName)}<br><strong>施術目安時間：</strong>${currentMenu.durationMinute ?? '-'}分<br><strong>料金：</strong>${formatPrice(currentMenu.price)}`;
    openModal('detailModal');
}

function openMenuForm(menu = null) {
    closeDetailModal();
    document.getElementById('menuFormTitle').textContent = menu ? 'メニューの編集' : '新規メニュー登録';
    document.getElementById('formMenuName').value = menu?.menuName ?? '';
    document.getElementById('formDuration').value = menu?.durationMinute ?? '';
    document.getElementById('formPrice').value = menu?.price ?? '';
    document.getElementById('menuFormModal').dataset.menuId = menu?.menuId ?? '';
    clearMenuError(); openModal('menuFormModal');
}

async function saveMenu() {
    const modal = document.getElementById('menuFormModal');
    const menuId = modal.dataset.menuId;
    const payload = { menuName: document.getElementById('formMenuName').value.trim(), durationMinute: numberOrNull('formDuration'), price: numberOrNull('formPrice') };
    clearMenuError();
    try {
        const response = await fetch(menuId ? `/scr005/menu/${encodeURIComponent(menuId)}` : '/scr005/menu', { method: menuId ? 'PUT' : 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
        if (!response.ok) throw new Error((await response.text()) || 'メニューの保存に失敗しました。');
        closeMenuForm(); await loadMenus();
    } catch (error) { showMenuError(error.message); }
}

function numberOrNull(id) { const value = document.getElementById(id).value; return value === '' ? null : Number(value); }
function formatPrice(value) { return value == null ? '未設定' : `${Number(value).toLocaleString('ja-JP')}円`; }
function closeDetailModal() { closeModal('detailModal'); }
function closeMenuForm() { closeModal('menuFormModal'); }
function showMenuError(message) { const area = document.getElementById('menuError'); area.textContent = message; area.style.display = 'block'; }
function clearMenuError() { const area = document.getElementById('menuError'); area.textContent = ''; area.style.display = 'none'; }
function openModal(id) { document.getElementById(id).classList.add('show'); document.body.classList.add('modal-open'); }
function closeModal(id) { document.getElementById(id).classList.remove('show'); if (!document.querySelector('.modal.show')) document.body.classList.remove('modal-open'); }
function escapeHtml(value) { const node = document.createElement('div'); node.textContent = value ?? ''; return node.innerHTML; }
window.addEventListener('click', event => { if (event.target.classList.contains('modal')) closeModal(event.target.id); });
document.addEventListener('DOMContentLoaded', loadMenus);
