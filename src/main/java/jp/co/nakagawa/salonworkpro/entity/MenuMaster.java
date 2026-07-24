package jp.co.nakagawa.salonworkpro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "m_menu")
@Getter
@Setter
public class MenuMaster {
	@Id
	@Column(name = "menu_id")
	private String menuId;

	@Column(name = "menu_name")
	private String menuName;

	@Column(name = "price")
	private Integer price;

	@Column(name = "duration_minute")
	private Integer durationMinute;

	@Column(name = "delete_flag")
	private String deleteFlag;
}
