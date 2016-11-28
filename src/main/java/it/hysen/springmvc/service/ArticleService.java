package it.hysen.springmvc.service;

import it.hysen.springmvc.model.Article;

public interface ArticleService extends GenericService<Article, Long>, SoftOperationsService<Article, Long> {

	boolean isArticleExist(Article entity);

}
