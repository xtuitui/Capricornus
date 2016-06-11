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
@Table(name = "tcs_group")
@NamedQueries(value = {
		@NamedQuery(name = "Group.queryAllGroup", query = "from Group"),
		@NamedQuery(name = "Group.queryGroupByName", query = "from Group g where g.name = :name")
})
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "tcs_user_group", 
			joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")}, 
			inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
	)
	@OrderBy(value = "id asc")
	private List<User> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", description="
				+ description + "]";
	}

}