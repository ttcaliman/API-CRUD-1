package br.com.best2bee.demo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.best2bee.demo.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
