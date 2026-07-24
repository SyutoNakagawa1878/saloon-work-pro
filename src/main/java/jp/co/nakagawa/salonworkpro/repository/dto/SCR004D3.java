package jp.co.nakagawa.salonworkpro.repository.dto;

import java.time.LocalDate;

/** 顧客基本情報の詳細表示用Projection。 */
public interface SCR004D3 {
	String getCustomerId();
	String getCustomerName();
	String getCustomerNameKana();
	String getGender();
	String getGenderCode();
	LocalDate getBirthday();
	Integer getAge();
	String getJob();
	String getJobCode();
	String getLocation();
}
