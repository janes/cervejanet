package br.com.ipt.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ipt.domain.Carrinho;
import br.com.ipt.repository.CarrinhoRepository;
import br.com.ipt.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Carrinho.
 */
@RestController
@RequestMapping("/api")
public class CarrinhoResource {

    private final Logger log = LoggerFactory.getLogger(CarrinhoResource.class);
        
    @Inject
    private CarrinhoRepository carrinhoRepository;
    
    /**
     * POST  /carrinhos : Create a new carrinho.
     *
     * @param carrinho the carrinho to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carrinho, or with status 400 (Bad Request) if the carrinho has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/carrinhos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carrinho> createCarrinho(@RequestBody Carrinho carrinho) throws URISyntaxException {
        log.debug("REST request to save Carrinho : {}", carrinho);
        if (carrinho.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("carrinho", "idexists", "A new carrinho cannot already have an ID")).body(null);
        }
        Carrinho result = carrinhoRepository.save(carrinho);
        return ResponseEntity.created(new URI("/api/carrinhos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("carrinho", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carrinhos : Updates an existing carrinho.
     *
     * @param carrinho the carrinho to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carrinho,
     * or with status 400 (Bad Request) if the carrinho is not valid,
     * or with status 500 (Internal Server Error) if the carrinho couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/carrinhos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carrinho> updateCarrinho(@RequestBody Carrinho carrinho) throws URISyntaxException {
        log.debug("REST request to update Carrinho : {}", carrinho);
        if (carrinho.getId() == null) {
            return createCarrinho(carrinho);
        }
        Carrinho result = carrinhoRepository.save(carrinho);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("carrinho", carrinho.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carrinhos : get all the carrinhos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carrinhos in body
     */
    /*@RequestMapping(value = "/carrinhos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Carrinho> getAllCarrinhos() {
        log.debug("REST request to get all Carrinhos");
        List<Carrinho> carrinhos = carrinhoRepository.findAll();
        return carrinhos;
    }*/

    /**
     * GET  /carrinhos/:id : get the "id" carrinho.
     *
     * @param id the id of the carrinho to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carrinho, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/carrinhos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Carrinho> getCarrinho(@PathVariable Long id) {
        log.debug("REST request to get Carrinho : {}", id);
        Carrinho carrinho = carrinhoRepository.findOne(id);
        return Optional.ofNullable(carrinho)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /carrinhos/:id : delete the "id" carrinho.
     *
     * @param id the id of the carrinho to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/carrinhos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCarrinho(@PathVariable Long id) {
        log.debug("REST request to delete Carrinho : {}", id);
        carrinhoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carrinho", id.toString())).build();
    }

}
