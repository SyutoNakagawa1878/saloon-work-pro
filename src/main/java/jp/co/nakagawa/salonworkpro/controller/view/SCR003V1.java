package jp.co.nakagawa.salonworkpro.controller.view;

import java.time.LocalTime;

public class SCR003V1 {

	/**
	 * 時間
	 */
	private LocalTime time;

	/**
	 * 空き枠か
	 */
	private boolean available;

	/**
	 * 予約ID
	 */
	private Long reservationId;

	/**
	 * 顧客名
	 */
	private String customerName;

	/**
	 * メニュー名
	 */
	private String menuName;
	
	/**
	 * 施術時間
	 */
	private int durationMinute;
	
	/**
	 * 継続枠か
	 */
	private boolean continuation;

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public boolean isContinuation() {
		return continuation;
	}

	public void setContinuation(boolean continuation) {
		this.continuation = continuation;
	}

	public int getDurationMinute() {
		return durationMinute;
	}

	public void setDurationMinute(int durationMinute) {
		this.durationMinute = durationMinute;
	}
}