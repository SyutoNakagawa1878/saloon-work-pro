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

@Entity
@Table(name = "t_reservation")
public class ReservationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reservationId;

	private LocalDate reservationDate;

	private LocalTime startTime;

	private LocalTime endTime;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "menu_id")
	private String menuId;
	
	@Column(name = "duration_minute")
	private int durationMinute;

	private String memo;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(
			Long reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public int getDurationMinute() {
		return durationMinute;
	}

	public void setDurationMinute(int durationMinute) {
		this.durationMinute = durationMinute;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}