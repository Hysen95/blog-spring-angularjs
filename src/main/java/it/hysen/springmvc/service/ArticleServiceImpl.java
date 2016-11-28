package it.hysen.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.hysen.springmvc.dao.ArticleDAO;
import it.hysen.springmvc.dao.GenericDAO;
import it.hysen.springmvc.model.Article;

@Service("articleService")
public class ArticleServiceImpl extends GenericServiceImpl<Article, Long> implements ArticleService {

	@Autowired
	private ArticleDAO dao;

	public ArticleServiceImpl(@Qualifier("articleDAOImpl") GenericDAO<Article, Long> genericDAO) {
		super(genericDAO);
		this.dao = (ArticleDAO) genericDAO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isArticleExist(Article entity) {
		return this.dao.find(entity.getId()) != null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void softDelete(Article entity) {
		this.dao.softDelete(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void softDeleteAll() {
		this.dao.softDeleteAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void softReactive(Article entity) {
		this.dao.softReactive(entity);
	}

}
