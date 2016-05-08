package br.com.ipt.service;

import br.com.ipt.domain.Produto;
import br.com.ipt.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Produto.
 */
@Service
@Transactional
public class ProdutoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoService.class);
    
    @Inject
    private ProdutoRepository produtoRepository;
    
    /**
     * Save a produto.
     * 
     * @param produto the entity to save
     * @return the persisted entity
     */
    public Produto save(Produto produto) {
        log.debug("Request to save Produto : {}", produto);
        Produto result = produtoRepository.save(produto);
        return result;
    }

    /**
     *  Get all the produtos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Produto> findAll(Pageable pageable) {
        log.debug("Request to get all Produtos");
        Page<Produto> result = produtoRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one produto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Produto findOne(Long id) {
        log.debug("Request to get Produto : {}", id);
        Produto produto = produtoRepository.findOne(id);
        return produto;
    }

    /**
     *  Delete the  produto by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Produto : {}", id);
        produtoRepository.delete(id);
    }
}
