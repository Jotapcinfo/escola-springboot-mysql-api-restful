package br.com.jotapcinfo.apiescola.entitie.exception;

public class ExceptionClass extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExceptionClass(String message) {
		super(message);
	}
}
