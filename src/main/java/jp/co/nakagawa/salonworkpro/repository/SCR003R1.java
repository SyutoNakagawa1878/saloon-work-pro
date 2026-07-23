package jp.co.nakagawa.salonworkpro.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.nakagawa.salonworkpro.entity.Reservation;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR003D1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR003D2;

/**
 * 予約情報をデータベースから取得・登録・更新するRepository。
 */
@Repository
public interface SCR003R1
		// ReservationEntityを操作し、主キーの型はLongであることを指定する。
		extends JpaRepository<Reservation, Long> {

	// ================================
	// 予約情報検索
	// ================================
	@Query(value = """
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
			""", nativeQuery = true)
	List<SCR003D1> findReservation(
			// @Paramで、SQL内の :reservationDate と引数を対応付ける。
			@Param("reservationDate") LocalDate reservationDate);

	
	// ================================
	// メニュー情報一覧取得
	// ================================
	@Query(value = """
			SELECT
				menu_id AS menuId,
				menu_name AS menuName,
				duration_minute AS durationMinute
			FROM m_menu
			ORDER BY menu_id
			""", nativeQuery = true)
	List<SCR003D2> findMenuList();

	// ================================
	// 施術時間取得
	// ================================
	@Query(value = """
			SELECT duration_minute
			FROM m_menu
			WHERE menu_id = :menuId
			""", nativeQuery = true)
	Integer findDurationMinute(
			@Param("menuId") String menuId);
	
	// ================================
	// 予約の重複有無取得
	// ================================
	@Query(value = """
		    SELECT COUNT(*)
		    FROM t_reservation
		    WHERE reservation_date = :reservationDate
		      AND start_time < :endTime
		      AND end_time > :startTime
		    """, nativeQuery = true)
		Integer countOverlapReservation(
		        @Param("reservationDate") LocalDate reservationDate,
		        @Param("startTime") LocalTime startTime,
		        @Param("endTime") LocalTime endTime);
}
