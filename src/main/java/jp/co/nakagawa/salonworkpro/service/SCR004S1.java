package jp.co.nakagawa.salonworkpro.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.nakagawa.salonworkpro.entity.Customer;
import jp.co.nakagawa.salonworkpro.repository.SCR004R1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D1;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D2;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D3;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D7;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR004D8;

@Service
public class SCR004S1 {

	private final SCR004R1 repository;

	public SCR004S1(SCR004R1 repository) {
		this.repository = repository;
	}

	/**
	 * 顧客一覧検索
	 */
	public List<SCR004D1> searchCustomer(
			String customerName,
			String genderCodes,
			Integer ageFrom,
			Integer ageTo) {
		return repository.searchCustomer(
				customerName == null ? "" : customerName,
				genderCodes == null ? "" : genderCodes,
				ageFrom,
				ageTo);
	}

	/**
	 * 性別コード一覧取得
	 */
	public List<SCR004D2> findGenderList() {
		return repository.findGenderList();
	}

	/**
	 * 職業コード一覧取得
	 */
	public List<SCR004D2> findJobList() {
		return repository.findJobList();
	}

	/**
	 * 顧客詳細情報取得
	 */
	public SCR004D7 findCustomerDetail(String customerId) {
		SCR004D3 profile = repository.findCustomerDetail(customerId)
				.orElseThrow(() -> new IllegalArgumentException("顧客が見つかりません。"));

		return new SCR004D7(
				profile,
				repository.findReservationHistory(customerId),
				repository.findFeatures(customerId),
				repository.findHobbies(customerId),
				repository.findTalkHistories(customerId));
	}

	/**
	 * 新規顧客登録
	 */
	@Transactional
	public String createCustomer(SCR004D8 request) {
		int nextNumber = repository.findMaxCustomerNumber() + 1;
		String customerId = String.format("CUS%04d", nextNumber);
		saveCustomer(customerId, request, true);
		return customerId;
	}

	/**
	 * 顧客情報更新
	 */
	@Transactional
	public void updateCustomer(String customerId, SCR004D8 request) {
		saveCustomer(customerId, request, false);
	}

	/**
	 * 顧客情報登録・更新
	 */
	private void saveCustomer(String customerId, SCR004D8 request, boolean isNew) {

		Customer customer = isNew
				? new Customer()
				: repository.findById(customerId)
						.orElseThrow(() -> new IllegalArgumentException("顧客が見つかりません。"));

		customer.setCustomerId(customerId);
		customer.setCustomerName(request.customerName());
		customer.setCustomerNameKana(request.customerNameKana());
		customer.setDeleteFlag("0");

		repository.save(customer);

		repository.saveCustomerDetail(
				customerId,
				request.gender(),
				request.birthday(),
				request.job(),
				request.location());

		repository.deleteFeatures(customerId);
		int featureNo = 1;
		if (request.features() != null) {
			for (SCR004D8.Feature feature : request.features()) {
				if (feature.part() != null && !feature.part().isBlank()
						&& feature.feature() != null && !feature.feature().isBlank()) {
					repository.saveFeature(customerId, featureNo++, feature.part(), feature.feature());
				}
			}
		}

		repository.deleteHobbies(customerId);

		int hobbyNo = 1;

		if (request.hobbies() != null) {
			for (SCR004D8.Hobby hobby : request.hobbies()) {
				if (hobby.hobby() != null && !hobby.hobby().isBlank()) {
					repository.saveHobby(
							customerId,
							hobbyNo++,
							hobby.hobby(),
							hobby.hobbyDetail());
				}
			}
		}

		repository.deleteTalkHistories(customerId);

		if (request.talkHistories() != null) {
			for (SCR004D8.TalkHistory talk : request.talkHistories()) {
				if (talk.visitDate() != null
						&& talk.talkContent() != null
						&& !talk.talkContent().isBlank()) {

					repository.saveTalkHistory(
							customerId,
							talk.visitDate(),
							talk.talkContent());
				}
			}
		}
	}
}
