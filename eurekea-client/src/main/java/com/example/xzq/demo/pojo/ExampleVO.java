package com.example.xzq.demo.pojo;

import java.util.List;

public class ExampleVO {
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "ExampleVO [ids=" + ids + "]";
	}
	
	
}
