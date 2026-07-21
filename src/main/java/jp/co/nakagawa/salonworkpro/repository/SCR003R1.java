package jp.co.nakagawa.salonworkpro.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.nakagawa.salonworkpro.entity.SCR003E1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR003D1;

/**
 * 予約情報をデータベースから取得・登録・更新するRepository。
 */
@Repository
public interface SCR003R1
		// ReservationEntityを操作し、主キーの型はLongであることを指定する。
		extends JpaRepository<SCR003E1, Long> {

	/**
	 * 指定日の予約一覧を取得する。
	 *
	 * 予約テーブルに加えて、顧客マスタ・メニューマスタも結合し、
	 * 画面表示に必要な「顧客名」「メニュー名」「施術時間」も取得する。
	 *
	 * @param reservationDate 検索したい予約日
	 * @return 指定日の予約情報一覧
	 *
	 * nativeQuery = true は、JPQLではなく通常のSQLを実行する設定です。
	 */
	@Query(value = """
			SELECT
			    r.reservation_id,  -- 予約ID
			    r.start_time,      -- 開始時刻
			    r.end_time,        -- 終了時刻
			    c.customer_name,   -- 顧客名
			    m.menu_name,       -- メニュー名
			    m.duration_minute, -- 施術時間（分）
			    r.memo             -- メモ
			FROM
			    t_reservation r    -- 予約テーブル。rは短縮名（別名）。
			INNER JOIN
			    m_customer c       -- 顧客マスタ。cは短縮名。
			ON
			    r.customer_id = c.customer_id
			INNER JOIN
			    m_menu m           -- メニューマスタ。mは短縮名。
			ON
			    r.menu_id = m.menu_id
			WHERE
			    r.reservation_date = :reservationDate
			    -- :reservationDate は、メソッド引数の値に置き換わる。
			""", nativeQuery = true)
	List<SCR003D1> findReservation(
			// @Paramで、SQL内の :reservationDate と引数を対応付ける。
			@Param("reservationDate") LocalDate reservationDate);
}