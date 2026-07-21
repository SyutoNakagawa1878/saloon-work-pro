package jp.co.nakagawa.salonworkpro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.nakagawa.salonworkpro.entity.SCR003E2;

/**
 * 顧客マスタ（m_customer）を操作するRepository。
 *
 * Repositoryは、データベースへの検索・登録・更新・削除を担当します。
 * ControllerやServiceが直接SQLを書く代わりに、このRepositoryを利用します。
 */
@Repository
public interface SCR003R2
		// JpaRepository<Entityの型, 主キーの型> を継承する。
		// これにより、基本的な検索・登録・更新などの機能が自動で利用できる。
		extends JpaRepository<SCR003E2, String> {

	/**
	 * 顧客名に指定したキーワードが含まれる、有効な顧客を検索する。
	 *
	 * @param keyword 顧客名を部分一致で検索するための文字列
	 * @return 該当した顧客の一覧
	 *
	 * @QueryにはSQLに似たJPQLを記述します。
	 * JPQLではテーブル名・列名ではなく、Entity名・フィールド名を使用します。
	 */
	@Query("""
			SELECT c
			FROM SCR003E2 c
			WHERE c.deleteFlag = '0'
			AND c.customerName
			    LIKE CONCAT(
			            '%',
			            :keyword,
			            '%')
			""")
	List<SCR003E2> search(
			String keyword);
}