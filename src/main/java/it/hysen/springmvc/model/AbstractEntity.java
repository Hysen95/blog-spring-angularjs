package it.hysen.springmvc.model;

import java.io.Serializable;

public abstract class AbstractEntity<K extends Serializable> implements Serializable, EntityMarker {
	
	private static final long serialVersionUID = 1L;

	public abstract K getId();

	public abstract void setId(K id);
	
}
