package br.com.ipt.web.rest;

import br.com.ipt.CervejanetApp;
import br.com.ipt.domain.ItemPedido;
import br.com.ipt.repository.ItemPedidoRepository;

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
 * Test class for the ItemPedidoResource REST controller.
 *
 * @see ItemPedidoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CervejanetApp.class)
@WebAppConfiguration
@IntegrationTest
public class ItemPedidoResourceIntTest {


    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final Double DEFAULT_PRECO = 1D;
    private static final Double UPDATED_PRECO = 2D;

    @Inject
    private ItemPedidoRepository itemPedidoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restItemPedidoMockMvc;

    private ItemPedido itemPedido;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ItemPedidoResource itemPedidoResource = new ItemPedidoResource();
        ReflectionTestUtils.setField(itemPedidoResource, "itemPedidoRepository", itemPedidoRepository);
        this.restItemPedidoMockMvc = MockMvcBuilders.standaloneSetup(itemPedidoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        itemPedido = new ItemPedido();
        itemPedido.setQuantidade(DEFAULT_QUANTIDADE);
        itemPedido.setPreco(DEFAULT_PRECO);
    }

    @Test
    @Transactional
    public void createItemPedido() throws Exception {
        int databaseSizeBeforeCreate = itemPedidoRepository.findAll().size();

        // Create the ItemPedido

        restItemPedidoMockMvc.perform(post("/api/item-pedidos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(itemPedido)))
                .andExpect(status().isCreated());

        // Validate the ItemPedido in the database
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();
        assertThat(itemPedidos).hasSize(databaseSizeBeforeCreate + 1);
        ItemPedido testItemPedido = itemPedidos.get(itemPedidos.size() - 1);
        assertThat(testItemPedido.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testItemPedido.getPreco()).isEqualTo(DEFAULT_PRECO);
    }

    @Test
    @Transactional
    public void getAllItemPedidos() throws Exception {
        // Initialize the database
        itemPedidoRepository.saveAndFlush(itemPedido);

        // Get all the itemPedidos
        restItemPedidoMockMvc.perform(get("/api/item-pedidos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(itemPedido.getId().intValue())))
                .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
                .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.doubleValue())));
    }

    @Test
    @Transactional
    public void getItemPedido() throws Exception {
        // Initialize the database
        itemPedidoRepository.saveAndFlush(itemPedido);

        // Get the itemPedido
        restItemPedidoMockMvc.perform(get("/api/item-pedidos/{id}", itemPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(itemPedido.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemPedido() throws Exception {
        // Get the itemPedido
        restItemPedidoMockMvc.perform(get("/api/item-pedidos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPedido() throws Exception {
        // Initialize the database
        itemPedidoRepository.saveAndFlush(itemPedido);
        int databaseSizeBeforeUpdate = itemPedidoRepository.findAll().size();

        // Update the itemPedido
        ItemPedido updatedItemPedido = new ItemPedido();
        updatedItemPedido.setId(itemPedido.getId());
        updatedItemPedido.setQuantidade(UPDATED_QUANTIDADE);
        updatedItemPedido.setPreco(UPDATED_PRECO);

        restItemPedidoMockMvc.perform(put("/api/item-pedidos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedItemPedido)))
                .andExpect(status().isOk());

        // Validate the ItemPedido in the database
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();
        assertThat(itemPedidos).hasSize(databaseSizeBeforeUpdate);
        ItemPedido testItemPedido = itemPedidos.get(itemPedidos.size() - 1);
        assertThat(testItemPedido.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testItemPedido.getPreco()).isEqualTo(UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void deleteItemPedido() throws Exception {
        // Initialize the database
        itemPedidoRepository.saveAndFlush(itemPedido);
        int databaseSizeBeforeDelete = itemPedidoRepository.findAll().size();

        // Get the itemPedido
        restItemPedidoMockMvc.perform(delete("/api/item-pedidos/{id}", itemPedido.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();
        assertThat(itemPedidos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
