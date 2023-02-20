package com.devsuperior.movieflix.services.exceptions;

/**
 * usada para retornar o erro 403 para o cliente
 * 
 * erro 403 - usuario logado mas o perfil dele nao permite acessar o recurso
 * @author admin
 *
 */
public class ForbiddenException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ForbiddenException(String msg) {
		super(msg);
	}

}
