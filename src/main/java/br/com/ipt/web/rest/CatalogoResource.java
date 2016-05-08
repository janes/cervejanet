package br.com.ipt.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ipt.domain.Catalogo;
import br.com.ipt.repository.CatalogoRepository;
import br.com.ipt.web.rest.util.HeaderUtil;
import br.com.ipt.web.rest.dto.CatalogoDTO;
import br.com.ipt.web.rest.mapper.CatalogoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Catalogo.
 */
@RestController
@RequestMapping("/api")
public class CatalogoResource {

    private final Logger log = LoggerFactory.getLogger(CatalogoResource.class);
        
    @Inject
    private CatalogoRepository catalogoRepository;
    
    @Inject
    private CatalogoMapper catalogoMapper;
    
    /**
     * POST  /catalogos : Create a new catalogo.
     *
     * @param catalogoDTO the catalogoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new catalogoDTO, or with status 400 (Bad Request) if the catalogo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/catalogos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CatalogoDTO> createCatalogo(@RequestBody CatalogoDTO catalogoDTO) throws URISyntaxException {
        log.debug("REST request to save Catalogo : {}", catalogoDTO);
        if (catalogoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("catalogo", "idexists", "A new catalogo cannot already have an ID")).body(null);
        }
        Catalogo catalogo = catalogoMapper.catalogoDTOToCatalogo(catalogoDTO);
        catalogo = catalogoRepository.save(catalogo);
        CatalogoDTO result = catalogoMapper.catalogoToCatalogoDTO(catalogo);
        return ResponseEntity.created(new URI("/api/catalogos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("catalogo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /catalogos : Updates an existing catalogo.
     *
     * @param catalogoDTO the catalogoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated catalogoDTO,
     * or with status 400 (Bad Request) if the catalogoDTO is not valid,
     * or with status 500 (Internal Server Error) if the catalogoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/catalogos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CatalogoDTO> updateCatalogo(@RequestBody CatalogoDTO catalogoDTO) throws URISyntaxException {
        log.debug("REST request to update Catalogo : {}", catalogoDTO);
        if (catalogoDTO.getId() == null) {
            return createCatalogo(catalogoDTO);
        }
        Catalogo catalogo = catalogoMapper.catalogoDTOToCatalogo(catalogoDTO);
        catalogo = catalogoRepository.save(catalogo);
        CatalogoDTO result = catalogoMapper.catalogoToCatalogoDTO(catalogo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("catalogo", catalogoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /catalogos : get all the catalogos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of catalogos in body
     */
    @RequestMapping(value = "/catalogos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<CatalogoDTO> getAllCatalogos() {
        log.debug("REST request to get all Catalogos");
        List<Catalogo> catalogos = catalogoRepository.findAll();
        return catalogoMapper.catalogosToCatalogoDTOs(catalogos);
    }

    /**
     * GET  /catalogos/:id : get the "id" catalogo.
     *
     * @param id the id of the catalogoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the catalogoDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/catalogos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CatalogoDTO> getCatalogo(@PathVariable Long id) {
        log.debug("REST request to get Catalogo : {}", id);
        Catalogo catalogo = catalogoRepository.findOne(id);
        CatalogoDTO catalogoDTO = catalogoMapper.catalogoToCatalogoDTO(catalogo);
        return Optional.ofNullable(catalogoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /catalogos/:id : delete the "id" catalogo.
     *
     * @param id the id of the catalogoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/catalogos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCatalogo(@PathVariable Long id) {
        log.debug("REST request to delete Catalogo : {}", id);
        catalogoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("catalogo", id.toString())).build();
    }

}
