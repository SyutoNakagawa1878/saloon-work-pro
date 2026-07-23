package jp.co.nakagawa.salonworkpro.repository.dto;

import java.time.LocalDate;

/** 会話履歴表示用Projection。 */
public interface SCR004D6 {
	LocalDate getVisitDate();
	String getTalkContent();
}
