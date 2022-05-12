package javaGuides.duc.Entity;

import java.time.Instant;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "food")
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private int calo;
	@Column
	private String element;
	@Column
	private String name;
	@Column
	private Instant time_create;
	@Column
	private Instant time_update;
	@Column 
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Instant getTime_create() {
		return time_create;
	}
	public void setTime_create(Instant time_create) {
		this.time_create = time_create;
	}
	public Instant getTime_update() {
		return time_update;
	}
	public void setTime_update(Instant time_update) {
		this.time_update = time_update;
	}
	@ManyToMany(mappedBy = "foods")
	private Set<Menu> menus;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getCalo() {
		return calo;
	}
	public void setCalo(int calo) {
		this.calo = calo;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
}
