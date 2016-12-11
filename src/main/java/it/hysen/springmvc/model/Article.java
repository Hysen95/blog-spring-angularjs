package it.hysen.springmvc.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the articles database table.
 *
 */
@Entity
@Table(name = "articles")
@NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a")
public class Article extends AbstractEntity<Integer> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "article_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Lob
	private String body;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "deleted_at")
	private Timestamp deletedAt;
	
	private String subtitle;
	
	private String tags;
	
	private String title;
	
	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
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
		Article other = (Article) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}
	
	public Timestamp getDeletedAt() {
		return this.deletedAt;
	}
	
	@Override
	public Integer getId() {
		return this.id;
	}
	
	public String getSubtitle() {
		return this.subtitle;
	}
	
	public String getTags() {
		return this.tags;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public User getUser() {
		return this.user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id ^ this.id >>> 32);
		return result;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}