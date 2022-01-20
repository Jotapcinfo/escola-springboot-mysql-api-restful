package br.com.jotapcinfo.apiescola.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.jotapcinfo.apiescola.entitie.exception.ExceptionClass;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(ExceptionClass.class)
	public ResponseEntity<Object> handleSerivice(ExceptionClass ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ExceptionModel problema = new ExceptionModel();

		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(LocalDateTime.now());

		return super.handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionModel problema = new ExceptionModel();
		List<ExceptionModel.Campo> campos = new ArrayList<ExceptionModel.Campo>();

		for (ObjectError error : ex.getBindingResult().getAllErrors()) {

			String nomeCampo = ((FieldError) error).getField();
			String mensagem = error.getDefaultMessage();

			campos.add(new ExceptionModel.Campo(nomeCampo, mensagem));
		}

		problema.setStatus(status.value());
		problema.setTitulo("Um ou mais campos estão invalidos." + "Faça o preenchimento correto e tente novamente");
		problema.setDataHora(LocalDateTime.now());

		problema.setCampos(campos);

		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
