package com.example.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role")
public class Role {
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Role)) {
			return false;
		}
		final Role role1 = (Role) o;
		return id == role1.id &&
			Objects.equals(role, role1.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, role);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="role_id")

	private int id;
	@Column(name="role")
	private String role;

	public Role(final String role) {
		this.role = role;
	}

	public Role() {

	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
