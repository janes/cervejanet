package br.com.ipt.repository;

import br.com.ipt.domain.Catalogo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Catalogo entity.
 */
@SuppressWarnings("unused")
public interface CatalogoRepository extends JpaRepository<Catalogo,Long> {

}
