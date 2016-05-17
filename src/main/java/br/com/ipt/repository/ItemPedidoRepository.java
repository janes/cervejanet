package br.com.ipt.repository;

import br.com.ipt.domain.ItemPedido;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ItemPedido entity.
 */
@SuppressWarnings("unused")
public interface ItemPedidoRepository extends JpaRepository<ItemPedido,Long> {

}
