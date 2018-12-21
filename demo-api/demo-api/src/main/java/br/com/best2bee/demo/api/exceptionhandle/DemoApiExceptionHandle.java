
package br.com.best2bee.demo.api.exceptionhandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DemoApiExceptionHandle extends ResponseEntityExceptionHandler{
	
	
	private static final String RECURSO_NAO_ENCONTRADO_KEY = "recurso.nao-encontrado";
	private static final String MENSAGEM_INVALIDA_KEY = "mensagem.invalida";
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemDoUsuario = messageSource.getMessage(MENSAGEM_INVALIDA_KEY, null, LocaleContextHolder.getLocale());

		String mensagemDesenvolvedor = ex.getCause()!=null?ex.getCause().toString():ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemDoUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,	HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		
		return handleExceptionInternal(ex, erros, headers, status, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String mensagemDoUsuario = messageSource.getMessage(RECURSO_NAO_ENCONTRADO_KEY, null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemDoUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);		
	}

	private List<Erro> criarListaDeErros(BindingResult bindingResult){
		List<Erro> erros = new ArrayList<Erro>();
		
		for (FieldError erro :bindingResult.getFieldErrors()) {
			
			String mensagemDoUsuario = messageSource.getMessage(erro, LocaleContextHolder.getLocale());

			String mensagemDesenvolvedor = erro.toString();
			
			erros.add(new Erro(mensagemDoUsuario,mensagemDesenvolvedor));
		}
		return erros;
	}
	
	
	public  class Erro {
		
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
		
	}	
}
