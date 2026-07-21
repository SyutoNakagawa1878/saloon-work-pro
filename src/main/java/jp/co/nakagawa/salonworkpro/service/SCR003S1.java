package jp.co.nakagawa.salonworkpro.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nakagawa.salonworkpro.controller.view.SCR003V1;
import jp.co.nakagawa.salonworkpro.repository.SCR003R1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR003D1;

/**
 * 予約管理に関する業務処理を担当するService。
 *
 * Repositoryから予約データを取得し、画面の時間割として表示しやすい
 * ReservationViewの一覧へ変換します。
 */
@Service
public class SCR003S1 {

	/** 予約データをデータベースから取得するRepository。 */
	@Autowired
	private SCR003R1 repository;

	/**
	 * 指定日の予約情報から、30分ごとの予約時間割を作成する。
	 *
	 * @param targetDate 時間割を表示したい日付
	 * @return 09:00～22:00までの30分単位の時間割
	 */
	public List<SCR003V1> createTimeTable(LocalDate targetDate) {

		// 指定日の予約情報をデータベースから取得する。
		List<SCR003D1> reservationList = repository.findReservation(targetDate);

		// 画面に渡す時間割データを格納するリスト。
		List<SCR003V1> reservationTable = new ArrayList<>();

		// 営業開始時刻を09:00とする。
		LocalTime current = LocalTime.of(9, 0);

		// 09:00から22:00まで、30分ごとの時間枠を作成する。
		while (!current.isAfter(LocalTime.of(22, 0))) {

			// 1つの時間枠（例：09:00、09:30）を作成する。
			SCR003V1 row = new SCR003V1();

			// この行が表す時刻を設定する。
			row.setTime(current);

			// 予約情報を設定する前は「空き」として扱う。
			row.setAvailable(true);

			// 作成した時間枠を時間割へ追加する。
			reservationTable.add(row);

			// 次の時間枠へ進む。
			current = current.plusMinutes(30);
		}

		// 取得した各予約を時間割へ反映する。
		for (SCR003D1 reservation : reservationList) {

			// 時間割のすべての時間枠を確認する。
			for (SCR003V1 row : reservationTable) {

				LocalTime slot = row.getTime();

				/*
				 * 時間枠が「予約開始時刻以上」かつ「予約終了時刻より前」の場合、
				 * その時間枠は予約済みと判断する。
				 *
				 * 例：10:00～11:00の予約の場合、
				 * 10:00と10:30を予約済みにする。
				 * 11:00は終了時刻のため予約済みにはしない。
				 */
				if (!slot.isBefore(reservation.getStartTime())
						&& slot.isBefore(reservation.getEndTime())) {

					// この時間枠は空いていない。
					row.setAvailable(false);

					// どの予約に属する時間枠かを設定する。
					row.setReservationId(reservation.getReservationId());

					/*
					 * 予約開始時刻の行か、それ以降の継続時間の行かを判定する。
					 */
					if (slot.equals(reservation.getStartTime())) {

						// 予約開始行には顧客名・メニュー名などを設定する。
						row.setCustomerName(reservation.getCustomerName());
						row.setMenuName(reservation.getMenuName());
						row.setDurationMinute(reservation.getDurationMinute());

						// falseは「予約の開始行」であることを表す。
						row.setContinuation(false);

					} else {

						// 継続行にも予約内容を設定する。
						row.setCustomerName(reservation.getCustomerName());
						row.setMenuName(reservation.getMenuName());
						row.setDurationMinute(reservation.getDurationMinute());

						// trueは「予約開始後の継続行」であることを表す。
						row.setContinuation(true);
					}
				}
			}
		}

		// 作成した時間割をControllerへ返す。
		return reservationTable;
	}
}