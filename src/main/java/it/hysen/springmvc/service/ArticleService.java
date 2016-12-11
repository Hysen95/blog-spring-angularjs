package it.hysen.springmvc.service;

import it.hysen.springmvc.model.Article;

public interface ArticleService extends GenericService<Article, Integer>, SoftOperationsService<Article, Integer> {
	
	boolean isArticleExist(Article entity);
	
}
