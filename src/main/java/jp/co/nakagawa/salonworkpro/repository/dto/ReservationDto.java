package jp.co.nakagawa.salonworkpro.repository.dto;

import java.time.LocalTime;

public class ReservationDto {

    private Long reservationId;

    private LocalTime startTime;

    private LocalTime endTime;

    private String customerName;

    private String menuName;
    
    private int durationMinute;

    private String memo;

    public ReservationDto(
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

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getDurationMinute() {
		return durationMinute;
	}

	public void setDurationMinute(int durationMinute) {
		this.durationMinute = durationMinute;
	}

}