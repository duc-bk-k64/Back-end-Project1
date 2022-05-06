package javaGuides.duc.Entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 60)
	private String name;
	@ManyToMany(mappedBy = "roles")
	private Set<User> user;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<User> getUser() {
		return user;
	}
}
