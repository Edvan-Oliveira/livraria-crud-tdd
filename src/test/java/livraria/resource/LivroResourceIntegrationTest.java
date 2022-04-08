package livraria.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import livraria.domain.entity.Livro;
import livraria.domain.request.LivroPostRequest;
import livraria.domain.request.LivroPutRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class LivroResourceIntegrationTest {

    private static final Integer ID = 1;
    private static final String TITULO = "Livro Demais";
    private static final String AUTOR = "DH";
    private static final String ISBN = "985";

    private static final String OUTRO_TITULO = "Livro Ok";
    private static final String OUTRO_AUTOR = "Pou Ho";
    private static final String OUTRO_ISBN = "112";

    private static final String URL_API = "/livros";
    private static final String URL_API_ID = URL_API.concat("/").concat(ID.toString());

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void limparBancoDeDados() {
        entityManager.getEntityManager()
                .createNativeQuery("TRUNCATE livro RESTART IDENTITY")
                .executeUpdate();
    }

    @Test
    @DisplayName("Deve cadastrar um livro com sucesso.")
    void cadastrar() throws Exception {
        LivroPostRequest livroPostRequest = obterLivroPostRequest();
        String livroPostRequestJSON = converterParaJSON(livroPostRequest);

        MockHttpServletRequestBuilder requisicao = post(URL_API)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(livroPostRequestJSON);

        mvc.perform(requisicao)
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve atualizar um livro com sucesso.")
    void atualizar() throws Exception {
        Livro livroParaSerSalvo = obterLivroSemID();
        entityManager.persist(livroParaSerSalvo);

        LivroPutRequest livroPutRequest = obterLivroPutRequest();
        livroPutRequest.setAutor(AUTOR.concat(" Mendes"));
        livroPutRequest.setTitulo(TITULO.concat("!!!"));

        String livroPutRequestJSON = converterParaJSON(livroPutRequest);

        MockHttpServletRequestBuilder requisicao = put(URL_API_ID)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(livroPutRequestJSON);

        mvc.perform(requisicao)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve listar todos os livros com sucesso.")
    void listarTodos() throws Exception {
        Livro primeiroLivro = obterLivroSemID();
        Livro segundoLivro = obterOutroLivroSemID();
        entityManager.persist(primeiroLivro);
        entityManager.persist(segundoLivro);

        MockHttpServletRequestBuilder requisicao = get(URL_API)
                .accept(APPLICATION_JSON);

        mvc.perform(requisicao)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve buscar livro por ID com sucesso.")
    void buscarPorID() throws Exception {
        Livro livroParaSerSalvo = obterLivroSemID();
        entityManager.persist(livroParaSerSalvo);

        MockHttpServletRequestBuilder requisicao = get(URL_API_ID)
                .accept(APPLICATION_JSON);

        mvc.perform(requisicao)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar livro com sucesso.")
    void deletar() throws Exception {
        Livro livroParaSerSalvo = obterLivroSemID();
        entityManager.persist(livroParaSerSalvo);

        MockHttpServletRequestBuilder requisicao = delete(URL_API_ID);

        mvc.perform(requisicao)
                .andExpect(status().isNoContent());
    }

    private Livro obterLivroSemID() {
        return Livro.builder().titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private Livro obterOutroLivroSemID() {
        return Livro.builder().titulo(OUTRO_TITULO).autor(OUTRO_AUTOR).isbn(OUTRO_ISBN).build();
    }

    private LivroPostRequest obterLivroPostRequest() {
        return LivroPostRequest.builder().titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private String converterParaJSON(Object objeto) throws Exception {
        return new ObjectMapper().writeValueAsString(objeto);
    }

    private LivroPutRequest obterLivroPutRequest() {
        return LivroPutRequest.builder().id(ID).titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }
}
