package br.com.ipt.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ipt.domain.ItemPedido;
import br.com.ipt.repository.ItemPedidoRepository;
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
 * REST controller for managing ItemPedido.
 */
@RestController
@RequestMapping("/api")
public class ItemPedidoResource {

    private final Logger log = LoggerFactory.getLogger(ItemPedidoResource.class);
        
    @Inject
    private ItemPedidoRepository itemPedidoRepository;
    
    /**
     * POST  /item-pedidos : Create a new itemPedido.
     *
     * @param itemPedido the itemPedido to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemPedido, or with status 400 (Bad Request) if the itemPedido has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/item-pedidos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ItemPedido> createItemPedido(@RequestBody ItemPedido itemPedido) throws URISyntaxException {
        log.debug("REST request to save ItemPedido : {}", itemPedido);
        if (itemPedido.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("itemPedido", "idexists", "A new itemPedido cannot already have an ID")).body(null);
        }
        ItemPedido result = itemPedidoRepository.save(itemPedido);
        return ResponseEntity.created(new URI("/api/item-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("itemPedido", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-pedidos : Updates an existing itemPedido.
     *
     * @param itemPedido the itemPedido to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemPedido,
     * or with status 400 (Bad Request) if the itemPedido is not valid,
     * or with status 500 (Internal Server Error) if the itemPedido couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/item-pedidos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ItemPedido> updateItemPedido(@RequestBody ItemPedido itemPedido) throws URISyntaxException {
        log.debug("REST request to update ItemPedido : {}", itemPedido);
        if (itemPedido.getId() == null) {
            return createItemPedido(itemPedido);
        }
        ItemPedido result = itemPedidoRepository.save(itemPedido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("itemPedido", itemPedido.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-pedidos : get all the itemPedidos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of itemPedidos in body
     */
    @RequestMapping(value = "/item-pedidos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ItemPedido> getAllItemPedidos() {
        log.debug("REST request to get all ItemPedidos");
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();
        return itemPedidos;
    }

    /**
     * GET  /item-pedidos/:id : get the "id" itemPedido.
     *
     * @param id the id of the itemPedido to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemPedido, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/item-pedidos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ItemPedido> getItemPedido(@PathVariable Long id) {
        log.debug("REST request to get ItemPedido : {}", id);
        ItemPedido itemPedido = itemPedidoRepository.findOne(id);
        return Optional.ofNullable(itemPedido)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /item-pedidos/:id : delete the "id" itemPedido.
     *
     * @param id the id of the itemPedido to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/item-pedidos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
        log.debug("REST request to delete ItemPedido : {}", id);
        itemPedidoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("itemPedido", id.toString())).build();
    }

}
