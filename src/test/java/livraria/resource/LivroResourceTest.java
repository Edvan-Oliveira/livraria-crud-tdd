package livraria.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import livraria.domain.entity.Livro;
import livraria.domain.mapper.LivroMapper;
import livraria.domain.request.LivroPostRequest;
import livraria.domain.request.LivroPutRequest;
import livraria.domain.response.LivroGetResponse;
import livraria.service.LivroService;
import livraria.service.exception.ISBNDuplicadoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LivroResource.class)
public class LivroResourceTest {

    private static final String ISBN_DUPLICADO = "ISBN já cadastrado";

    private static final Integer ID = 5;
    private static final String TITULO = "Livro Bom";
    private static final String AUTOR = "Fulano";
    private static final String ISBN = "123";

    private static final Integer OUTRO_ID = 8;
    private static final String OUTRO_TITULO = "Livro Ótimo";
    private static final String OUTRO_AUTOR = "Ciclano";
    private static final String OUTRO_ISBN = "654";

    private static final String URL_API = "/livros";
    private static final String URL_API_ID = URL_API.concat("/").concat(ID.toString());

    @MockBean
    private LivroService livroService;

    @MockBean
    private LivroMapper livroMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Deve cadastrar um livro com sucesso.")
    void cadastrar() throws Exception {
        Livro livroParSerSalvo = obterLivroSemID();
        Livro livroSalvo = obterLivroComID();
        LivroPostRequest livroPostRequest = obterLivroPostRequest();
        LivroGetResponse livroGetResponse = obterLivroGetResponse();

        given(livroMapper.converterParaLivro(livroPostRequest)).willReturn(livroParSerSalvo);
        given(livroService.cadastrar(livroParSerSalvo)).willReturn(livroSalvo);
        given(livroMapper.converterParaLivroGetResponse(livroSalvo)).willReturn(livroGetResponse);

        String livroPostRequestJSON = converterParaJSON(livroPostRequest);

        MockHttpServletRequestBuilder requisicao = post(URL_API)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(livroPostRequestJSON);

        mvc.perform(requisicao)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(ID))
                .andExpect(jsonPath("titulo").value(TITULO))
                .andExpect(jsonPath("autor").value(AUTOR))
                .andExpect(jsonPath("isbn").value(ISBN));
    }

    @Test
    @DisplayName("Deve lançar a exceção ISBNDuplicadoException ao tentar cadastrar um livro com ISBN já cadastrado.")
    void cadastrarLivroComISBNExistente() throws Exception {
        Livro livroParaSerSalvo = obterLivroSemID();
        LivroPostRequest livroPostRequest = obterLivroPostRequest();

        given(livroMapper.converterParaLivro(livroPostRequest)).willReturn(livroParaSerSalvo);
        given(livroService.cadastrar(livroParaSerSalvo)).willThrow(new ISBNDuplicadoException(ISBN_DUPLICADO));

        String livroPostRequestJSON = converterParaJSON(livroPostRequest);

        MockHttpServletRequestBuilder requisicao = post(URL_API)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(livroPostRequestJSON);

        mvc.perform(requisicao)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("caminho").value(URL_API))
                .andExpect(jsonPath("status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("mensagem").value(ISBN_DUPLICADO))
                .andExpect(jsonPath("momento").isNotEmpty());
    }

    @Test
    @DisplayName("Deve atualizar um livro com sucesso.")
    void atualizar() throws Exception {
        Livro livroParaSerAtualizado = obterLivroComID();
        Livro livroAtualizado = obterLivroComID();
        LivroPutRequest livroPutRequest = obterLivroPutRequest();
        LivroGetResponse livroGetResponse = obterLivroGetResponse();

        given(livroMapper.converterParaLivro(livroPutRequest)).willReturn(livroParaSerAtualizado);
        given(livroService.atualizar(livroParaSerAtualizado)).willReturn(livroAtualizado);
        given(livroMapper.converterParaLivroGetResponse(livroAtualizado)).willReturn(livroGetResponse);

        String livroPutRequestJSON = converterParaJSON(livroPutRequest);

        MockHttpServletRequestBuilder requisicao = put(URL_API_ID)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(livroPutRequestJSON);

        mvc.perform(requisicao)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(ID))
                .andExpect(jsonPath("titulo").value(TITULO))
                .andExpect(jsonPath("autor").value(AUTOR))
                .andExpect(jsonPath("isbn").value(ISBN));
    }

    @Test
    @DisplayName("Deve lançar a exceção ISBNDuplicadoException ao tentar atualizar um livro com ISBN já cadastrado.")
    void atualizarLivroComISBNExistente() throws Exception {
        Livro livroParaSerAtualizado = obterLivroComID();
        LivroPutRequest livroPutRequest = obterLivroPutRequest();

        given(livroMapper.converterParaLivro(livroPutRequest)).willReturn(livroParaSerAtualizado);
        given(livroService.atualizar(livroParaSerAtualizado)).willThrow(new ISBNDuplicadoException(ISBN_DUPLICADO));

        String livroPutRequestJSON = converterParaJSON(livroPutRequest);

        MockHttpServletRequestBuilder requisicao = put(URL_API_ID)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(livroPutRequestJSON);

        mvc.perform(requisicao)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("caminho").value(URL_API_ID))
                .andExpect(jsonPath("status").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("mensagem").value(ISBN_DUPLICADO))
                .andExpect(jsonPath("momento").isNotEmpty());
    }

    @Test
    @DisplayName("Deve listar todos os livros no cenário de uma lista populada.")
    void listarTodos() throws Exception {
        Livro primeiroLivro = obterLivroComID();
        Livro segundoLivro = obterOutroLivroComID();
        List<Livro> listaDeLivros = List.of(primeiroLivro, segundoLivro);

        LivroGetResponse primeiroLivroGetResponse = obterLivroGetResponse();
        LivroGetResponse segundoLivroGetResponse = obterOutroLivroGetResponse();
        List<LivroGetResponse> listaDeLivroGetResponse = List.of(primeiroLivroGetResponse, segundoLivroGetResponse);

        given(livroService.listarTodos()).willReturn(listaDeLivros);
        given(livroMapper.converterParaListaDeLivroGetResponse(listaDeLivros)).willReturn(listaDeLivroGetResponse);

        MockHttpServletRequestBuilder requisicao = get(URL_API)
                .accept(APPLICATION_JSON);

        mvc.perform(requisicao)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("[0].id").value(ID))
                .andExpect(jsonPath("[0].titulo").value(TITULO))
                .andExpect(jsonPath("[0].autor").value(AUTOR))
                .andExpect(jsonPath("[0].isbn").value(ISBN))
                .andExpect(jsonPath("[1].id").value(OUTRO_ID))
                .andExpect(jsonPath("[1].titulo").value(OUTRO_TITULO))
                .andExpect(jsonPath("[1].autor").value(OUTRO_AUTOR))
                .andExpect(jsonPath("[1].isbn").value(OUTRO_ISBN));
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia.")
    void listarTodosComListaVazia() throws Exception {
        List<Livro> listaDeLivrosVazia = List.of();
        List<LivroGetResponse> listaDeLivroGetResponseVazia = List.of();

        given(livroService.listarTodos()).willReturn(listaDeLivrosVazia);
        given(livroMapper.converterParaListaDeLivroGetResponse(listaDeLivrosVazia))
                .willReturn(listaDeLivroGetResponseVazia);

        MockHttpServletRequestBuilder requisicao = get(URL_API)
                .accept(APPLICATION_JSON);

        mvc.perform(requisicao)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Deve buscar livro por ID com sucesso.")
    void bsucarPorID() throws Exception {
        Livro livroQueSeraRetornado = obterLivroComID();
        LivroGetResponse livroGetResponse = obterLivroGetResponse();

        given(livroService.buscarPorID(ID)).willReturn(livroQueSeraRetornado);
        given(livroMapper.converterParaLivroGetResponse(livroQueSeraRetornado)).willReturn(livroGetResponse);

        MockHttpServletRequestBuilder requisicao = get(URL_API_ID)
                .accept(APPLICATION_JSON);

        mvc.perform(requisicao)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(ID))
                .andExpect(jsonPath("titulo").value(TITULO))
                .andExpect(jsonPath("autor").value(AUTOR))
                .andExpect(jsonPath("isbn").value(ISBN));
    }

    private Livro obterLivroSemID() {
        return Livro.builder().titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private Livro obterLivroComID() {
        return Livro.builder().id(ID).titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private LivroPostRequest obterLivroPostRequest() {
        return LivroPostRequest.builder().titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private LivroGetResponse obterLivroGetResponse() {
        return LivroGetResponse.builder().id(ID).titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private LivroPutRequest obterLivroPutRequest() {
        return LivroPutRequest.builder().id(ID).titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private String converterParaJSON(Object objeto) throws Exception {
        return new ObjectMapper().writeValueAsString(objeto);
    }

    private Livro obterOutroLivroComID() {
        return Livro.builder().id(OUTRO_ID).titulo(OUTRO_TITULO).autor(OUTRO_AUTOR).isbn(OUTRO_ISBN).build();
    }

    private LivroGetResponse obterOutroLivroGetResponse() {
        return LivroGetResponse.builder().id(OUTRO_ID).titulo(OUTRO_TITULO).autor(OUTRO_AUTOR).isbn(OUTRO_ISBN).build();
    }
}
