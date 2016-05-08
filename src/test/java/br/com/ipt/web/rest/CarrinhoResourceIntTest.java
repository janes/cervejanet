package br.com.ipt.web.rest;

import br.com.ipt.CervejanetApp;
import br.com.ipt.domain.Carrinho;
import br.com.ipt.repository.CarrinhoRepository;

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
 * Test class for the CarrinhoResource REST controller.
 *
 * @see CarrinhoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CervejanetApp.class)
@WebAppConfiguration
@IntegrationTest
public class CarrinhoResourceIntTest {


    private static final Long DEFAULT_CLIENTE_ID = 1L;
    private static final Long UPDATED_CLIENTE_ID = 2L;

    @Inject
    private CarrinhoRepository carrinhoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCarrinhoMockMvc;

    private Carrinho carrinho;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarrinhoResource carrinhoResource = new CarrinhoResource();
        ReflectionTestUtils.setField(carrinhoResource, "carrinhoRepository", carrinhoRepository);
        this.restCarrinhoMockMvc = MockMvcBuilders.standaloneSetup(carrinhoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        carrinho = new Carrinho();
        carrinho.setClienteId(DEFAULT_CLIENTE_ID);
    }

    @Test
    @Transactional
    public void createCarrinho() throws Exception {
        int databaseSizeBeforeCreate = carrinhoRepository.findAll().size();

        // Create the Carrinho

        restCarrinhoMockMvc.perform(post("/api/carrinhos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carrinho)))
                .andExpect(status().isCreated());

        // Validate the Carrinho in the database
        List<Carrinho> carrinhos = carrinhoRepository.findAll();
        assertThat(carrinhos).hasSize(databaseSizeBeforeCreate + 1);
        Carrinho testCarrinho = carrinhos.get(carrinhos.size() - 1);
        assertThat(testCarrinho.getClienteId()).isEqualTo(DEFAULT_CLIENTE_ID);
    }

    @Test
    @Transactional
    public void getAllCarrinhos() throws Exception {
        // Initialize the database
        carrinhoRepository.saveAndFlush(carrinho);

        // Get all the carrinhos
        restCarrinhoMockMvc.perform(get("/api/carrinhos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(carrinho.getId().intValue())))
                .andExpect(jsonPath("$.[*].clienteId").value(hasItem(DEFAULT_CLIENTE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getCarrinho() throws Exception {
        // Initialize the database
        carrinhoRepository.saveAndFlush(carrinho);

        // Get the carrinho
        restCarrinhoMockMvc.perform(get("/api/carrinhos/{id}", carrinho.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carrinho.getId().intValue()))
            .andExpect(jsonPath("$.clienteId").value(DEFAULT_CLIENTE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCarrinho() throws Exception {
        // Get the carrinho
        restCarrinhoMockMvc.perform(get("/api/carrinhos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarrinho() throws Exception {
        // Initialize the database
        carrinhoRepository.saveAndFlush(carrinho);
        int databaseSizeBeforeUpdate = carrinhoRepository.findAll().size();

        // Update the carrinho
        Carrinho updatedCarrinho = new Carrinho();
        updatedCarrinho.setId(carrinho.getId());
        updatedCarrinho.setClienteId(UPDATED_CLIENTE_ID);

        restCarrinhoMockMvc.perform(put("/api/carrinhos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCarrinho)))
                .andExpect(status().isOk());

        // Validate the Carrinho in the database
        List<Carrinho> carrinhos = carrinhoRepository.findAll();
        assertThat(carrinhos).hasSize(databaseSizeBeforeUpdate);
        Carrinho testCarrinho = carrinhos.get(carrinhos.size() - 1);
        assertThat(testCarrinho.getClienteId()).isEqualTo(UPDATED_CLIENTE_ID);
    }

    @Test
    @Transactional
    public void deleteCarrinho() throws Exception {
        // Initialize the database
        carrinhoRepository.saveAndFlush(carrinho);
        int databaseSizeBeforeDelete = carrinhoRepository.findAll().size();

        // Get the carrinho
        restCarrinhoMockMvc.perform(delete("/api/carrinhos/{id}", carrinho.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Carrinho> carrinhos = carrinhoRepository.findAll();
        assertThat(carrinhos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
