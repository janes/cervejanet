package br.com.ipt.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ipt.domain.Produto;
import br.com.ipt.service.ProdutoService;
import br.com.ipt.web.rest.util.HeaderUtil;
import br.com.ipt.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Produto.
 */
@RestController
@RequestMapping("/api")
public class HomeResource {

    private final Logger log = LoggerFactory.getLogger(HomeResource.class);
        
    @Inject
    private ProdutoService produtoService;
    
    /**
     * GET  /produtos : get all the produtos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of produtos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/home-produtos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Produto>> getAllProdutos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Home Produtos");
        Page<Produto> page = produtoService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/home-produtos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
