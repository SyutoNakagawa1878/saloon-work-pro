package jp.co.nakagawa.salonworkpro.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.nakagawa.salonworkpro.entity.MenuMaster;
import jp.co.nakagawa.salonworkpro.repository.SCR005R1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR005D1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR005D2;

@Service
public class SCR005S1 {
	private final SCR005R1 repository;
	public SCR005S1(SCR005R1 repository) { this.repository = repository; }
	public List<SCR005D1> findMenuList() { return repository.findMenuList(); }
	public SCR005D1 findMenuDetail(String menuId) { return repository.findMenuDetail(menuId).orElseThrow(() -> new IllegalArgumentException("メニューが見つかりません。")); }
	@Transactional
	public String createMenu(SCR005D2 request) {
		String menuId = String.format("MEN%04d", repository.findMaxMenuNumber() + 1);
		saveMenu(menuId, request, true);
		return menuId;
	}
	@Transactional
	public void updateMenu(String menuId, SCR005D2 request) { saveMenu(menuId, request, false); }
	private void saveMenu(String menuId, SCR005D2 request, boolean isNew) {
		MenuMaster menu = isNew ? new MenuMaster() : repository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("メニューが見つかりません。"));
		menu.setMenuId(menuId); menu.setMenuName(request.menuName()); menu.setDurationMinute(request.durationMinute()); menu.setPrice(request.price()); menu.setDeleteFlag("0");
		repository.save(menu);
	}
}
