package com.sergiosanchez.movies;

public class Response {
	String service;
	String response;
	
	public Response(String service, String response) {
		super();
		this.service = service;
		this.response = response;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
}
