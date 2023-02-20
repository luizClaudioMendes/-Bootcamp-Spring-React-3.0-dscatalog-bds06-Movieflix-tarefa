package com.devsuperior.movieflix.services.exceptions;

/**
 * usada para retornar o erro 401 para o cliente
 * 
 * erro 401 - usuario com o missing token ou token invalido
 * @author admin
 *
 */
public class UnauthorizedException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedException(String msg) {
		super(msg);
	}

}
