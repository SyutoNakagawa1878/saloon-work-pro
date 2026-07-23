package jp.co.nakagawa.salonworkpro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.nakagawa.salonworkpro.entity.Customer;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D2;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D3;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D4;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D5;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D6;

@Repository
public interface SCR004R1 extends JpaRepository<Customer, String> {

	@Query(value = """
			SELECT code_1 AS code1, code_name_1 AS codeName
			FROM m_code
			WHERE code_type = 'GENDER' AND delete_flag = '0'
			ORDER BY code_1
			""", nativeQuery = true)
	List<SCR004D2> findGenderList();

	@Query(value = """
			SELECT code_1 AS code1, code_name_1 AS codeName
			FROM m_code
			WHERE code_type = 'JOB' AND delete_flag = '0'
			ORDER BY code_1
			""", nativeQuery = true)
	List<SCR004D2> findJobList();

	@Query(value = """
			SELECT COALESCE(MAX(CAST(SUBSTRING(customer_id, 4) AS UNSIGNED)), 0)
			FROM m_customer
			WHERE customer_id LIKE 'CUS%'
			""", nativeQuery = true)
	Integer findMaxCustomerNumber();

	@Query(value = """
			SELECT c.customer_id AS customerId, c.customer_name AS customerName,
				COALESCE(gender.code_name_1, '未設定') AS gender,
				TIMESTAMPDIFF(YEAR, d.birthday, CURDATE()) AS age,
				COALESCE(job.code_name_1, '未設定') AS job
			FROM m_customer c
			INNER JOIN m_customer_detail d ON c.customer_id = d.customer_id
			LEFT JOIN m_code gender ON gender.code_type = 'GENDER'
				AND gender.code_1 = d.gender AND gender.delete_flag = '0'
			LEFT JOIN m_code job ON job.code_type = 'JOB'
				AND job.code_1 = d.job AND job.delete_flag = '0'
			WHERE c.delete_flag = '0' AND d.delete_flag = '0'
				AND (:customerName = '' OR c.customer_name LIKE CONCAT('%', :customerName, '%')
					OR c.customer_name_kana LIKE CONCAT('%', :customerName, '%'))
				AND (:genderCodes = '' OR FIND_IN_SET(d.gender, :genderCodes) > 0)
				AND (:ageFrom IS NULL OR TIMESTAMPDIFF(YEAR, d.birthday, CURDATE()) >= :ageFrom)
				AND (:ageTo IS NULL OR TIMESTAMPDIFF(YEAR, d.birthday, CURDATE()) <= :ageTo)
			ORDER BY c.customer_name_kana, c.customer_id
			""", nativeQuery = true)
	List<SCR004D1> searchCustomer(
			@Param("customerName") String customerName,
			@Param("genderCodes") String genderCodes,
			@Param("ageFrom") Integer ageFrom,
			@Param("ageTo") Integer ageTo);

	@Query(value = """
			SELECT c.customer_id AS customerId, c.customer_name AS customerName,
				COALESCE(gender.code_name_1, '未設定') AS gender, d.birthday AS birthday,
				TIMESTAMPDIFF(YEAR, d.birthday, CURDATE()) AS age,
				COALESCE(job.code_name_1, '未設定') AS job, d.feature AS feature
			FROM m_customer c
			INNER JOIN m_customer_detail d ON c.customer_id = d.customer_id
			LEFT JOIN m_code gender ON gender.code_type = 'GENDER'
				AND gender.code_1 = d.gender AND gender.delete_flag = '0'
			LEFT JOIN m_code job ON job.code_type = 'JOB'
				AND job.code_1 = d.job AND job.delete_flag = '0'
			WHERE c.customer_id = :customerId AND c.delete_flag = '0' AND d.delete_flag = '0'
			""", nativeQuery = true)
	Optional<SCR004D3> findCustomerDetail(@Param("customerId") String customerId);

	@Query(value = """
			SELECT r.reservation_date AS reservationDate, r.start_time AS startTime,
				r.end_time AS endTime, m.menu_name AS menuName, r.memo AS memo
			FROM t_reservation r INNER JOIN m_menu m ON r.menu_id = m.menu_id
			WHERE r.customer_id = :customerId AND r.delete_flag = '0'
			ORDER BY r.reservation_date DESC, r.start_time DESC
			""", nativeQuery = true)
	List<SCR004D4> findReservationHistory(@Param("customerId") String customerId);

	@Query(value = """
			SELECT hobby AS hobby, hobby_detail AS hobbyDetail
			FROM m_customer_hobby
			WHERE customer_id = :customerId AND delete_flag = '0'
			ORDER BY hobby_no
			""", nativeQuery = true)
	List<SCR004D5> findHobbies(@Param("customerId") String customerId);

	@Query(value = """
			SELECT visit_date AS visitDate, talk_content AS talkContent
			FROM t_customer_talk_history
			WHERE customer_id = :customerId AND delete_flag = '0'
			ORDER BY visit_date DESC, talk_id DESC
			""", nativeQuery = true)
	List<SCR004D6> findTalkHistories(@Param("customerId") String customerId);

	@Modifying
	@Query(value = """
			INSERT INTO m_customer_detail (customer_id, gender, birthday, job, feature)
			VALUES (:customerId, :gender, :birthday, :job, :feature)
			ON DUPLICATE KEY UPDATE gender = VALUES(gender), birthday = VALUES(birthday),
				job = VALUES(job), feature = VALUES(feature), delete_flag = '0'
			""", nativeQuery = true)
	void saveCustomerDetail(@Param("customerId") String customerId,
			@Param("gender") String gender, @Param("birthday") java.time.LocalDate birthday,
			@Param("job") String job, @Param("feature") String feature);

	@Modifying
	@Query(value = "DELETE FROM m_customer_hobby WHERE customer_id = :customerId", nativeQuery = true)
	void deleteHobbies(@Param("customerId") String customerId);

	@Modifying
	@Query(value = "INSERT INTO m_customer_hobby (customer_id, hobby_no, hobby, hobby_detail) VALUES (:customerId, :hobbyNo, :hobby, :hobbyDetail)", nativeQuery = true)
	void saveHobby(@Param("customerId") String customerId, @Param("hobbyNo") int hobbyNo,
			@Param("hobby") String hobby, @Param("hobbyDetail") String hobbyDetail);

	@Modifying
	@Query(value = "DELETE FROM t_customer_talk_history WHERE customer_id = :customerId", nativeQuery = true)
	void deleteTalkHistories(@Param("customerId") String customerId);

	@Modifying
	@Query(value = "INSERT INTO t_customer_talk_history (customer_id, visit_date, talk_content) VALUES (:customerId, :visitDate, :talkContent)", nativeQuery = true)
	void saveTalkHistory(@Param("customerId") String customerId,
			@Param("visitDate") java.time.LocalDate visitDate,
			@Param("talkContent") String talkContent);
}
