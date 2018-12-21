package br.com.best2bee.demo.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.best2bee.demo.api.model.Pessoa;
import br.com.best2bee.demo.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		
		return repository.save(pessoaSalva);
	}



	public void ativarDesativar(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		if(ativo == true) {
			ativo = false;
		}else {
			ativo = true;
		}
		pessoaSalva.setAtivo(ativo);
		
		repository.save(pessoaSalva);
	}
	
	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = repository.findOne(codigo);
		
		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}



	public List<Pessoa> listarPessoas() {
		return repository.findAll();
	}
	
	
	public Pessoa listarTodosAtivos(){
	List<Pessoa> var = repository.findAll();
	
	for(int i = 0; i<=var.size(); i++) {
		Long l2 = Long.valueOf(i);
		Pessoa pes = repository.findOne(l2);
		
		while(pes != null) {
			return pes; //pega o atributo que vc quer aqui
			}
		}
	return null;
	}



	public Pessoa salvar(Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	public void excluir(Long codigoPessoa) {
		if (!repository.exists(codigoPessoa)) {
			throw new EmptyResultDataAccessException(1);
		}
		repository.delete(codigoPessoa);
	}
}




