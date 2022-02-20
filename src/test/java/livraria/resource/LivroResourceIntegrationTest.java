package livraria.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import livraria.domain.request.LivroPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LivroResourceIntegrationTest {

    private static final String URL_API = "/livros";

    private static final String TITULO = "Livro Demais";
    private static final String AUTOR = "DH";
    private static final String ISBN = "985";

    @Autowired
    private MockMvc mvc;

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

    private LivroPostRequest obterLivroPostRequest() {
        return LivroPostRequest.builder().titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private String converterParaJSON(Object objeto) throws Exception {
        return new ObjectMapper().writeValueAsString(objeto);
    }
}
