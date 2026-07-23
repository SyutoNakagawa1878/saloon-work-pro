package jp.co.nakagawa.salonworkpro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nakagawa.salonworkpro.entity.Customer;
import jp.co.nakagawa.salonworkpro.repository.SCR003R2;

/**
 * 顧客に関する業務処理を担当するService。
 *
 * Serviceは、ControllerとRepositoryの間に位置します。
 * 現在は検索処理をRepositoryへ依頼するだけですが、
 * 将来的には入力チェックや業務ルールの判定などもここに記述します。
 */
@Service
public class CustomerService {

	/**
	 * 顧客テーブルを操作するRepository。
	 *
	 * @Autowiredにより、SpringがCustomerRepositoryの実装を
	 * 自動的にこの変数へ設定します。
	 */
	@Autowired
	private SCR003R2 repository;

	/**
	 * キーワードをもとに顧客を検索する。
	 *
	 * @param keyword 顧客名を部分一致検索するための文字列
	 * @return 条件に一致した顧客の一覧
	 */
	public List<Customer> search(
			String keyword) {

		// 実際のデータベース検索はRepositoryに依頼する。
		// Repository側では、削除されていない顧客のみを検索している。
		return repository.search(keyword);
	}
}