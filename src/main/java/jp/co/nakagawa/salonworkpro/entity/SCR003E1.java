package jp.co.nakagawa.salonworkpro.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 予約テーブル（t_reservation）の1件分のデータを表すクラス。
 *
 * Entityは、Javaのクラスとデータベースのテーブルを対応付けるためのクラスです。
 */
@Entity
@Table(name = "t_reservation") // このクラスを「t_reservation」テーブルに対応付ける
public class SCR003E1 {

	/**
	 * 予約ID。予約を一意に識別する主キー。
	 *
	 * @GeneratedValue により、新しい予約を登録するときは
	 * データベース側でIDを自動採番します。
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reservationId;

	/** 予約日 */
	private LocalDate reservationDate;

	/** 予約開始時刻 */
	private LocalTime startTime;

	/** 予約終了時刻 */
	private LocalTime endTime;

	/** 予約した顧客のID */
	@Column(name = "customer_id")
	private String customerId;

	/** 予約したメニューのID */
	@Column(name = "menu_id")
	private String menuId;

	/** 施術時間（分） */
	@Column(name = "duration_minute")
	private int durationMinute;

	/** スタッフが記録するメモ・連絡事項 */
	private String memo;

	/** データを登録した日時 */
	private LocalDateTime createdAt;

	/** データを最後に更新した日時 */
	private LocalDateTime updatedAt;

	/** 予約IDを取得する */
	public Long getReservationId() {
		return reservationId;
	}

	/** 予約IDを設定する。通常はデータベースが自動設定する */
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	/** 予約日を取得する。 */
	public LocalDate getReservationDate() {
		return reservationDate;
	}

	/** 予約日を設定する。 */
	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	/** 開始時刻を取得する。 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/** 開始時刻を設定する。 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/** 終了時刻を取得する。 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/** 終了時刻を設定する。 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	/** 顧客IDを取得する。 */
	public String getCustomerId() {
		return customerId;
	}

	/** 顧客IDを設定する。 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/** メニューIDを取得する。 */
	public String getMenuId() {
		return menuId;
	}

	/** メニューIDを設定する。 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/** 施術時間（分）を取得する。 */
	public int getDurationMinute() {
		return durationMinute;
	}

	/** 施術時間（分）を設定する。 */
	public void setDurationMinute(int durationMinute) {
		this.durationMinute = durationMinute;
	}

	/** メモを取得する。 */
	public String getMemo() {
		return memo;
	}

	/** メモを設定する。 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/** 登録日時を取得する。 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/** 登録日時を設定する。 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/** 更新日時を取得する。 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	/** 更新日時を設定する。 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}