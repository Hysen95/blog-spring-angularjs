package it.hysen.springmvc.dao;

import it.hysen.springmvc.model.Article;

public interface ArticleDAO extends GenericDAO<Article, Integer>, SoftOperationsDAO<Article, Integer> {

}
