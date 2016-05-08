package br.com.ipt.repository;

import br.com.ipt.domain.Carrinho;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Carrinho entity.
 */
@SuppressWarnings("unused")
public interface CarrinhoRepository extends JpaRepository<Carrinho,Long> {

}
