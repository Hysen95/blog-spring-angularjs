package it.hysen.springmvc.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import it.hysen.springmvc.model.Article;

@Repository
public class ArticleDAOImpl extends AbstractGenericDAO<Article, Integer> implements ArticleDAO {
	
	@Override
	public void softDelete(Article entity) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		entity.setDeletedAt(Timestamp.valueOf(dateFormat.format(date)));
		this.update(entity);
	}
	
	@Override
	public void softDeleteAll() {
		List<Article> result = this.findAll();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Timestamp currentTimestamp = Timestamp.valueOf(dateFormat.format(date));
		for (Article item : result) {
			item.setDeletedAt(currentTimestamp);
			this.update(item);
		}
	}
	
	@Override
	public void softReactive(Article entity) {
		entity.setDeletedAt(null);
		this.update(entity);
	}
	
}
