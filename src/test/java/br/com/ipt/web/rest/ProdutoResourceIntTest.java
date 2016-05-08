package br.com.ipt.web.rest;

import br.com.ipt.CervejanetApp;
import br.com.ipt.domain.Produto;
import br.com.ipt.repository.ProdutoRepository;
import br.com.ipt.service.ProdutoService;

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
 * Test class for the ProdutoResource REST controller.
 *
 * @see ProdutoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CervejanetApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProdutoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_DESCRICAO = "AAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBB";

    private static final Double DEFAULT_PRECO = 1D;
    private static final Double UPDATED_PRECO = 2D;

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;
    private static final String DEFAULT_CATEGORIA = "AAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBB";
    private static final String DEFAULT_IMAGEM = "AAAAA";
    private static final String UPDATED_IMAGEM = "BBBBB";

    @Inject
    private ProdutoRepository produtoRepository;

    @Inject
    private ProdutoService produtoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProdutoMockMvc;

    private Produto produto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProdutoResource produtoResource = new ProdutoResource();
        ReflectionTestUtils.setField(produtoResource, "produtoService", produtoService);
        this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        produto = new Produto();
        produto.setNome(DEFAULT_NOME);
        produto.setDescricao(DEFAULT_DESCRICAO);
        produto.setPreco(DEFAULT_PRECO);
        produto.setQuantidade(DEFAULT_QUANTIDADE);
        produto.setCategoria(DEFAULT_CATEGORIA);
        produto.setImagem(DEFAULT_IMAGEM);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto

        restProdutoMockMvc.perform(post("/api/produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(produto)))
                .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtos.get(produtos.size() - 1);
        assertThat(testProduto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualTo(DEFAULT_PRECO);
        assertThat(testProduto.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testProduto.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testProduto.getImagem()).isEqualTo(DEFAULT_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtos
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
                .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.doubleValue())))
                .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
                .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
                .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM.toString())));
    }

    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.doubleValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoService.save(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = new Produto();
        updatedProduto.setId(produto.getId());
        updatedProduto.setNome(UPDATED_NOME);
        updatedProduto.setDescricao(UPDATED_DESCRICAO);
        updatedProduto.setPreco(UPDATED_PRECO);
        updatedProduto.setQuantidade(UPDATED_QUANTIDADE);
        updatedProduto.setCategoria(UPDATED_CATEGORIA);
        updatedProduto.setImagem(UPDATED_IMAGEM);

        restProdutoMockMvc.perform(put("/api/produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProduto)))
                .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtos.get(produtos.size() - 1);
        assertThat(testProduto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProduto.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProduto.getPreco()).isEqualTo(UPDATED_PRECO);
        assertThat(testProduto.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testProduto.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testProduto.getImagem()).isEqualTo(UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoService.save(produto);

        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Get the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
