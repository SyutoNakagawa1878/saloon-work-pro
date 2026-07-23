package jp.co.nakagawa.salonworkpro.repository.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/** 過去の利用履歴表示用Projection。 */
public interface SCR004D4 {
	LocalDate getReservationDate();
	LocalTime getStartTime();
	LocalTime getEndTime();
	String getMenuName();
	String getMemo();
}
