package jp.co.nakagawa.salonworkpro.repository.dto;

import java.time.LocalTime;

/**
 * 予約情報をまとめて受け渡すためのDTOクラス。
 *
 * DTO（Data Transfer Object）は、複数のテーブルから取得したデータを
 * 画面やサービスへ渡しやすい形にまとめるためのクラスです。
 */
public class SCR003D1 {

	/** 予約を一意に識別するID。 */
	private Long reservationId;

	/** 予約の開始時刻。 */
	private LocalTime startTime;

	/** 予約の終了時刻。 */
	private LocalTime endTime;

	/** 予約をした顧客の名前。 */
	private String customerName;

	/** 予約した施術メニューの名前。 */
	private String menuName;

	/** 施術にかかる時間（分）。 */
	private int durationMinute;

	/** 予約に関するメモ・連絡事項。 */
	private String memo;

	/**
	 * 予約DTOを作成するためのコンストラクタ。
	 *
	 * Repositoryでデータベース検索した結果を、このDTOにまとめる際に使用します。
	 */
	public SCR003D1(
			Long reservationId,
			LocalTime startTime,
			LocalTime endTime,
			String customerName,
			String menuName,
			int durationMinute,
			String memo) {

		this.setReservationId(reservationId);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setCustomerName(customerName);
		this.setMenuName(menuName);
		this.setDurationMinute(durationMinute);
		this.setMemo(memo);
	}

	/** 予約IDを取得する。 */
	public Long getReservationId() {
		return reservationId;
	}

	/** 予約IDを設定する。 */
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
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

	/** 顧客名を取得する。 */
	public String getCustomerName() {
		return customerName;
	}

	/** 顧客名を設定する。 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/** メニュー名を取得する。 */
	public String getMenuName() {
		return menuName;
	}

	/** メニュー名を設定する。 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/** メモを取得する。 */
	public String getMemo() {
		return memo;
	}

	/** メモを設定する。 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/** 施術時間（分）を取得する。 */
	public int getDurationMinute() {
		return durationMinute;
	}

	/** 施術時間（分）を設定する。 */
	public void setDurationMinute(int durationMinute) {
		this.durationMinute = durationMinute;
	}
}