package it.hysen.springmvc.dao;

import it.hysen.springmvc.model.Article;

public interface ArticleDAO extends GenericDAO<Article, Long>, SoftOperationsDAO<Article, Long> {
	
}
