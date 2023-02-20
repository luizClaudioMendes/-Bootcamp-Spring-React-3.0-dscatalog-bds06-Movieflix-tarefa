package com.devsuperior.movieflix.controllers.exceptions;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OauthCustomError implements Serializable{

	private static final long serialVersionUID = 1L;

	private String error;
	
	@JsonProperty("error_description") // para colocar no json o campo com este nome
	private String errorDescription;

	
	public OauthCustomError () {}


	public OauthCustomError(String error, String errorDescription) {
		super();
		this.error = error;
		this.errorDescription = errorDescription;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public String getErrorDescription() {
		return errorDescription;
	}


	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
}
