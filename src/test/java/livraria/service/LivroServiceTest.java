package livraria.service;

import livraria.domain.entity.Livro;
import livraria.repository.LivroRepository;
import livraria.service.exception.ISBNDuplicadoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    private static final String ISBN_DUPLICADO = "ISBN já cadastrado";

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
        given(livroRepository.existsByIsbn(ISBN)).willReturn(false);

        Livro resposta = livroService.cadastrar(livroParaSerSalvo);

        assertThat(resposta).isNotNull();
        assertThat(resposta.getId()).isEqualTo(ID);
        assertThat(resposta.getTitulo()).isEqualTo(TITULO);
        assertThat(resposta.getAutor()).isEqualTo(AUTOR);
        assertThat(resposta.getIsbn()).isEqualTo(ISBN);
    }

    @Test
    @DisplayName("Deve lançar a exceção ISBNDuplicadoException ao tentar cadastrar um livro com o ISBN já cadastrado.")
    void cadastrarLivroComISBNExistente() {
        Livro livroParaSerSalvo = obterLivroSemID();
        given(livroRepository.existsByIsbn(ISBN)).willReturn(true);

        assertThatThrownBy(() -> livroService.cadastrar(livroParaSerSalvo))
                .isInstanceOf(ISBNDuplicadoException.class)
                .hasMessage(ISBN_DUPLICADO);
    }

    @Test
    @DisplayName("Deve atualizar um livro com sucesso.")
    void atualizar() {
        Livro livroParaSerAtualizado = obterLivroComID();
        Livro livroAtualizado = obterLivroComID();

        given(livroRepository.save(livroParaSerAtualizado)).willReturn(livroAtualizado);

        Livro resposta = livroService.atualizar(livroParaSerAtualizado);

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