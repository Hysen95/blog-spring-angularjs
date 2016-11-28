package it.hysen.springmvc.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the users database table.
 *
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	// bi-directional many-to-one association to Article
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Article> articles;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "deleted_at")
	private Timestamp deletedAt;

	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private String password;

	// bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
	        @JoinColumn(name = "role_id") })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Role> roles;

	@Id
	@Column(name = "user_id")
	private Long id;

	private String username;

	public User() {
	}

	public Article addArticle(Article article) {
		this.getArticles().add(article);
		article.setUser(this);

		return article;
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
		User other = (User) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	public List<Article> getArticles() {
		return this.articles;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public Timestamp getDeletedAt() {
		return this.deletedAt;
	}

	public String getEmail() {
		return this.email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public String getUsername() {
		return this.username;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.id ^ this.id >>> 32);
		return result;
	}

	public Article removeArticle(Article article) {
		this.getArticles().remove(article);
		article.setUser(null);

		return article;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

}