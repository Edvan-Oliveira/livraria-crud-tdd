package livraria.service;

import livraria.domain.entity.Livro;
import livraria.repository.LivroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    private static final Integer ID = 8;
    private static final String TITULO = "Perfeito Livro";
    private static final String AUTOR = "Beltrano";
    private static final String ISBN = "452";

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Test
    @DisplayName("Deve cadastrar um livro com sucesso.")
    void cadastrar() {
        Livro livroParaSerSalvo = obterLivroSemID();
        Livro livroSalvo = obterLivroComID();

        given(livroRepository.save(livroParaSerSalvo)).willReturn(livroSalvo);
        Livro resposta = livroService.cadastrar(livroParaSerSalvo);

        assertThat(resposta).isNotNull();
        assertThat(resposta.getId()).isEqualTo(ID);
        assertThat(resposta.getTitulo()).isEqualTo(TITULO);
        assertThat(resposta.getAutor()).isEqualTo(AUTOR);
        assertThat(resposta.getIsbn()).isEqualTo(ISBN);
    }

    private Livro obterLivroSemID() {
        return Livro.builder().titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private Livro obterLivroComID() {
        return Livro.builder().id(ID).titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }
}