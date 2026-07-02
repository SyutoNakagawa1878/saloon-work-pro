package jp.co.nakagawa.salonworkpro.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.nakagawa.salonworkpro.controller.view.ReservationView;
import jp.co.nakagawa.salonworkpro.entity.ReservationEntity;
import jp.co.nakagawa.salonworkpro.repository.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository repository;

	public List<ReservationEntity> getReservationList(
			LocalDate date) {

		return repository
				.findByReservationDate(
						date);

	}

	public List<ReservationView> createTimeTable(LocalDate targetDate) {

		List<ReservationEntity> reservationList = repository
				.findByReservationDate(
						targetDate);

		List<ReservationView> reservationTable = new ArrayList<>();

		// 09:00開始
		LocalTime current = LocalTime.of(9, 0);

		// 22:00終了
		while (!current.isAfter(
				LocalTime.of(22, 0))) {

			ReservationView row = new ReservationView();

			// 時間
			row.setTime(current);

			// 初期状態は空き
			row.setAvailable(true);

			reservationTable.add(row);

			// 30分加算
			current = current.plusMinutes(30);
		}

		for (ReservationEntity reservation : reservationList) {

			for (ReservationView row : reservationTable) {

				LocalTime slot = row.getTime();

				if (!slot.isBefore(
						reservation.getStartTime())
						&&
						slot.isBefore(
								reservation.getEndTime())) {

					row.setAvailable(false);

					row.setReservationId(
							reservation.getReservationId());

					// 開始時間
					if (slot.equals(
							reservation.getStartTime())) {

						row.setCustomerName(
								reservation.getCustomerName());

						row.setMenuName(
								reservation.getMenuName());

						row.setContinuation(false);

					}
					// 継続時間
					else {

						row.setContinuation(true);

					}
				}
			}
		}

		return reservationTable;
	}

}