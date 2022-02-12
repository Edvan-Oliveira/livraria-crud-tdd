package livraria.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import livraria.domain.entity.Livro;
import livraria.domain.mapper.LivroMapper;
import livraria.domain.request.LivroPostRequest;
import livraria.domain.request.LivroPutRequest;
import livraria.domain.response.LivroGetResponse;
import livraria.service.LivroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LivroResource.class)
public class LivroResourceTest {

    private static final String URL_API = "/livros";
    private static final Integer ID = 5;
    private static final String TITULO = "Livro Bom";
    private static final String AUTOR = "Fulano";
    private static final String ISBN = "123";

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

        MockHttpServletRequestBuilder requisicao = put(URL_API.concat("/").concat(ID.toString()))
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
}
