package jp.co.nakagawa.salonworkpro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.nakagawa.salonworkpro.entity.MenuMaster;
import jp.co.nakagawa.salonworkpro.repository.dto.SCR005D1;

@Repository
public interface SCR005R1 extends JpaRepository<MenuMaster, String> {
	@Query(value = """
			SELECT menu_id AS menuId, menu_name AS menuName, duration_minute AS durationMinute, price AS price
			FROM m_menu WHERE delete_flag = '0' ORDER BY menu_name, menu_id
			""", nativeQuery = true)
	List<SCR005D1> findMenuList();

	@Query(value = """
			SELECT menu_id AS menuId, menu_name AS menuName, duration_minute AS durationMinute, price AS price
			FROM m_menu WHERE menu_id = :menuId AND delete_flag = '0'
			""", nativeQuery = true)
	Optional<SCR005D1> findMenuDetail(@Param("menuId") String menuId);

	@Query(value = "SELECT COALESCE(MAX(CAST(SUBSTRING(menu_id, 4) AS UNSIGNED)), 0) FROM m_menu WHERE menu_id LIKE 'MEN%'", nativeQuery = true)
	Integer findMaxMenuNumber();
}
