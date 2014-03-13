package tests;

import models.Employee;

import java.util.Objects;

/**
 * Created by Truls on 11.03.14.
 */
public class RequestTest {

	private String type;
	private String request;
	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getType() {
		return type;

	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public RequestTest(String type, String request, Object object) {
		this.type = type;
		this.request = request;
		this.object = object;

	}

	//getter and setter methods

	@Override
	public String toString() {
		return "Type";
	}
}