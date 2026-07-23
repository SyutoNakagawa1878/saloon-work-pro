package jp.co.nakagawa.salonworkpro.repository.dto;

import java.time.LocalDate;
import java.util.List;

/** 顧客登録・更新リクエスト。 */
public record SCR004D8(
		String customerId,
		String customerName,
		LocalDate birthday,
		String gender,
		String job,
		String feature,
		List<Hobby> hobbies,
		List<TalkHistory> talkHistories) {

	public record Hobby(String hobby, String hobbyDetail) {
	}

	public record TalkHistory(LocalDate visitDate, String talkContent) {
	}
}
