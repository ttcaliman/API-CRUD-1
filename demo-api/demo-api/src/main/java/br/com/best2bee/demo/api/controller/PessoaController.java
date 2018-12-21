package br.com.best2bee.demo.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.best2bee.demo.api.model.Pessoa;
import br.com.best2bee.demo.api.service.PessoaService;

@Controller
public class PessoaController {

	@Autowired
	private PessoaService service;
	
	@GetMapping("/")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/pessoa");
		mv.addObject("pessoas", service.listarPessoas());
		
		return mv;
	}
	
	@GetMapping("/ativos")
	public ModelAndView findActives() {
		ModelAndView mv = new ModelAndView("/pessoa");
		mv.addObject("pessoas", service.listarTodosAtivos());
		
		return mv;
	}
	
	@GetMapping("/add")
	public ModelAndView add(Pessoa pessoa) {
		ModelAndView mv = new ModelAndView("/pessoaAdd");
		mv.addObject("pessoa", pessoa);
		
		return mv;
	}
	
	@GetMapping("/edit/{codigo}")
	public ModelAndView edit(@PathVariable("codigo") Long codigo) {
		
		return add(service.buscarPessoaPeloCodigo(codigo));
	}
	
	@GetMapping("/atualizar/{codigo}")
	public ModelAndView edit1(@PathVariable("codigo") Long codigo, Pessoa pessoa) {
		
		return add(service.atualizar(codigo, pessoa));
	}
	
	@GetMapping("/ativo/{codigo}/{ativo}")
	public ModelAndView ativar(@PathVariable("codigo") Long codigo, @PathVariable("ativo") Boolean ativo) {
		
		service.ativarDesativar(codigo, ativo);
		
		return findAll();
	}
	
	
	@GetMapping("/delete/{codigoPessoa}")
	public ModelAndView delete(@PathVariable("codigoPessoa") Long codigoPessoa) {
		
		service.excluir(codigoPessoa);
		
		return findAll();
	}
	
	@PostMapping("/save")
	public ModelAndView save(@Valid Pessoa pessoa, BindingResult result) {
		
		if(result.hasErrors()) {
			return add(pessoa);
		}
		
		service.salvar(pessoa);
		
		return findAll();
	}
	
}
