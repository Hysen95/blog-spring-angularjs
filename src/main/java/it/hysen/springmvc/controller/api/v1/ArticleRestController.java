package it.hysen.springmvc.controller.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.hysen.springmvc.controller.api.SoftOperationsRestController;
import it.hysen.springmvc.model.Article;
import it.hysen.springmvc.service.ArticleService;
import it.hysen.springmvc.service.GenericService;

@RestController
public class ArticleRestController extends AbstractCRUDRestController<Article, Integer>
        implements SoftOperationsRestController<Article, Integer> {

	@Autowired
	private ArticleService service;

	public ArticleRestController(@Qualifier("articleService") GenericService<Article, Integer> genericService) {
		super(genericService);
		this.service = (ArticleService) genericService;
	}
	
	@Override
	@RequestMapping(value = "/article", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Article entity) {
		return super.create(entity);
	}
	
	@Override
	@RequestMapping(value = "/article/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Article> delete(@PathVariable("id") Integer key) {
		return super.delete(key);
	}
	
	@Override
	@RequestMapping(value = "/article", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAll() {
		return super.deleteAll();
	}
	
	@Override
	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
	public ResponseEntity<Article> find(@PathVariable("id") Integer key) {
		return super.find(key);
	}
	
	@Override
	@RequestMapping(value = "/article", method = RequestMethod.GET)
	public ResponseEntity<List<Article>> findAll() {
		return super.findAll();
	}
	
	@Override
	@RequestMapping(value = "/article/soft/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Article> softDelete(@PathVariable("id") Integer key) {
		Article entity = this.service.find(key);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		this.service.softDelete(entity);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@Override
	@RequestMapping(value = "/article/soft/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Article> softReactive(@PathVariable("id") Integer key) {
		Article entity = this.service.find(key);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		this.service.softReactive(entity);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@Override
	@RequestMapping(value = "/article/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Article> update(@PathVariable("id") Integer key, @RequestBody Article entity) {
		return super.update(key, entity);
	}
	
}