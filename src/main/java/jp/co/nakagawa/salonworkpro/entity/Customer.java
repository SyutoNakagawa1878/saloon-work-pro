package jp.co.nakagawa.salonworkpro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 顧客マスタ（m_customer）テーブルの1件分のデータを表すクラス。
 *
 * Entity（エンティティ）は、データベースのテーブルとJavaのクラスを
 * 対応付けるためのものです。
 */
@Entity
@Table(name = "m_customer") // このクラスを「m_customer」テーブルに対応付ける
@Getter // Lombokが各項目のgetter（値を取得するメソッド）を自動生成する
@Setter // Lombokが各項目のsetter（値を設定するメソッド）を自動生成する
public class Customer {

	/**
	 * 顧客ID
	 *
	 * @Id は、この項目がテーブル内の各データを一意に識別する
	 * 主キーであることを示します。
	 */
	@Id
	@Column(name = "customer_id") // テーブルの「customer_id」列に対応する
	private String customerId;

	/**
	 * 顧客名(漢字)
	 * テーブルの「customer_name」列に対応する。
	 */
	@Column(name = "customer_name")
	private String customerName;
	
	/**
	 * 顧客名(カナ)
	 * テーブルの「customer_name」列に対応する。
	 */
	@Column(name = "customer_name_kana")
	private String customerNameKana;

	/**
	 * 削除フラグ。
	 *
	 * 物理的にデータを削除せず、削除済みであることを管理するための項目です。
	 * 例：「0」= 有効、「1」= 削除済み
	 */
	@Column(name = "delete_flag")
	private String deleteFlag;
}