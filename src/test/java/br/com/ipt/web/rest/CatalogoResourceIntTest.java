package br.com.ipt.web.rest;

import br.com.ipt.CervejanetApp;
import br.com.ipt.domain.Catalogo;
import br.com.ipt.repository.CatalogoRepository;
import br.com.ipt.web.rest.dto.CatalogoDTO;
import br.com.ipt.web.rest.mapper.CatalogoMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CatalogoResource REST controller.
 *
 * @see CatalogoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CervejanetApp.class)
@WebAppConfiguration
@IntegrationTest
public class CatalogoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_DESCRICAO = "AAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBB";

    @Inject
    private CatalogoRepository catalogoRepository;

    @Inject
    private CatalogoMapper catalogoMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCatalogoMockMvc;

    private Catalogo catalogo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CatalogoResource catalogoResource = new CatalogoResource();
        ReflectionTestUtils.setField(catalogoResource, "catalogoRepository", catalogoRepository);
        ReflectionTestUtils.setField(catalogoResource, "catalogoMapper", catalogoMapper);
        this.restCatalogoMockMvc = MockMvcBuilders.standaloneSetup(catalogoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        catalogo = new Catalogo();
        catalogo.setNome(DEFAULT_NOME);
        catalogo.setDescricao(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createCatalogo() throws Exception {
        int databaseSizeBeforeCreate = catalogoRepository.findAll().size();

        // Create the Catalogo
        CatalogoDTO catalogoDTO = catalogoMapper.catalogoToCatalogoDTO(catalogo);

        restCatalogoMockMvc.perform(post("/api/catalogos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalogoDTO)))
                .andExpect(status().isCreated());

        // Validate the Catalogo in the database
        List<Catalogo> catalogos = catalogoRepository.findAll();
        assertThat(catalogos).hasSize(databaseSizeBeforeCreate + 1);
        Catalogo testCatalogo = catalogos.get(catalogos.size() - 1);
        assertThat(testCatalogo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCatalogo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllCatalogos() throws Exception {
        // Initialize the database
        catalogoRepository.saveAndFlush(catalogo);

        // Get all the catalogos
        restCatalogoMockMvc.perform(get("/api/catalogos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(catalogo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getCatalogo() throws Exception {
        // Initialize the database
        catalogoRepository.saveAndFlush(catalogo);

        // Get the catalogo
        restCatalogoMockMvc.perform(get("/api/catalogos/{id}", catalogo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(catalogo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCatalogo() throws Exception {
        // Get the catalogo
        restCatalogoMockMvc.perform(get("/api/catalogos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatalogo() throws Exception {
        // Initialize the database
        catalogoRepository.saveAndFlush(catalogo);
        int databaseSizeBeforeUpdate = catalogoRepository.findAll().size();

        // Update the catalogo
        Catalogo updatedCatalogo = new Catalogo();
        updatedCatalogo.setId(catalogo.getId());
        updatedCatalogo.setNome(UPDATED_NOME);
        updatedCatalogo.setDescricao(UPDATED_DESCRICAO);
        CatalogoDTO catalogoDTO = catalogoMapper.catalogoToCatalogoDTO(updatedCatalogo);

        restCatalogoMockMvc.perform(put("/api/catalogos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catalogoDTO)))
                .andExpect(status().isOk());

        // Validate the Catalogo in the database
        List<Catalogo> catalogos = catalogoRepository.findAll();
        assertThat(catalogos).hasSize(databaseSizeBeforeUpdate);
        Catalogo testCatalogo = catalogos.get(catalogos.size() - 1);
        assertThat(testCatalogo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCatalogo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void deleteCatalogo() throws Exception {
        // Initialize the database
        catalogoRepository.saveAndFlush(catalogo);
        int databaseSizeBeforeDelete = catalogoRepository.findAll().size();

        // Get the catalogo
        restCatalogoMockMvc.perform(delete("/api/catalogos/{id}", catalogo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Catalogo> catalogos = catalogoRepository.findAll();
        assertThat(catalogos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
