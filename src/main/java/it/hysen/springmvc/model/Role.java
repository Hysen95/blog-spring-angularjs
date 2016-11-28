package it.hysen.springmvc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the roles database table.
 *
 */
@Entity
@Table(name = "roles")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role extends AbstractEntity<Long> {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "is_default")
	private byte isDefault;

	private String name;

	@Column(name = "parent_id")
	private Long parentId;

	@Id
	@Column(name = "role_id")
	private Long id;

	// bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
	private List<User> users;

	public Role() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Role other = (Role) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public byte getIsDefault() {
		return this.isDefault;
	}

	public String getName() {
		return this.name;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public List<User> getUsers() {
		return this.users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.id ^ this.id >>> 32);
		return result;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setIsDefault(byte isDefault) {
		this.isDefault = isDefault;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}

}