package com.car.app.carscraporder.bo;

import java.util.ArrayList;
import java.util.List;

public class DictionarySystemBO {
	
	private String type;
	
	private List<DictionaryBO> children = new ArrayList<DictionaryBO>();

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<DictionaryBO> getChildren() {
		return children;
	}

	public void setChildren(List<DictionaryBO> children) {
		this.children = children;
	}

	
	

}
