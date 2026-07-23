package jp.co.nakagawa.salonworkpro.repository.dto;

import java.util.List;

/** 顧客詳細モーダルへ返すデータ一式。 */
public record SCR004D7(
		SCR004D3 profile,
		List<SCR004D4> reservations,
		List<SCR004D5> hobbies,
		List<SCR004D6> talkHistories) {
}
