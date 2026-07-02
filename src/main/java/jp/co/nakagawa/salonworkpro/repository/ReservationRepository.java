package jp.co.nakagawa.salonworkpro.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.nakagawa.salonworkpro.entity.ReservationEntity;
import jp.co.nakagawa.salonworkpro.repository.dto.ReservationDto;

@Repository
public interface ReservationRepository
		extends JpaRepository<ReservationEntity, Long> {

	@Query(
		    value = """
		        SELECT
		            r.reservation_id,
		            r.start_time,
		            r.end_time,
		            c.customer_name,
		            m.menu_name,
		            m.duration_minute,
		            r.memo
		        FROM
		            t_reservation r
		        INNER JOIN
		            m_customer c
		        ON
		            r.customer_id = c.customer_id
		        INNER JOIN
		            m_menu m
		        ON
		            r.menu_id = m.menu_id
		        WHERE
		            r.reservation_date = :reservationDate
		        """,
		    nativeQuery = true)
		List<ReservationDto> findReservation(
		        @Param("reservationDate")
		        LocalDate reservationDate);
}