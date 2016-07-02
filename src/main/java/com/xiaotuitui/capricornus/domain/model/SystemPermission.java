package com.xiaotuitui.capricornus.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "tcs_system_permission")
@NamedQueries(
		@NamedQuery(name = "SystemPermission.queryAllSystemPermission", query = "from SystemPermission order by id")
)
public class SystemPermission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "permission_key", nullable = false, unique = true, updatable = false)
	private String permissionKey;
	
	@Column(name = "name", nullable = false, unique = true, updatable = false)
	private String name;
	
	@Column(name = "description", updatable = false)
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "tcs_system_permission_group",
		joinColumns = {@JoinColumn(name = "system_permission_id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")}
	)
	@OrderBy(value = "id asc")
	private List<Group> groups;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public String toString() {
		return "SystemPermission [id=" + id + ", permissionKey="
				+ permissionKey + ", name=" + name + ", description="
				+ description + "]";
	}
	
}