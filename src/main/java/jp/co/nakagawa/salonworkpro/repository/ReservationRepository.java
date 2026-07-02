package jp.co.nakagawa.salonworkpro.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.nakagawa.salonworkpro.entity.ReservationEntity;

public interface ReservationRepository
		extends JpaRepository<ReservationEntity, Long> {

	List<ReservationEntity> findByReservationDate(
			LocalDate reservationDate);

}