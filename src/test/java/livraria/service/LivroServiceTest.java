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

import java.util.List;
import java.util.Optional;

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

    private static final Integer OUTRO_ID = 14;
    private static final String OUTRO_TITULO = "Livro Novo";
    private static final String OUTRO_AUTOR = "Xiluan";
    private static final String OUTRO_ISBN = "984";

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

        given(livroRepository.findByIsbn(ISBN)).willReturn(Optional.empty());
        given(livroRepository.save(livroParaSerAtualizado)).willReturn(livroAtualizado);

        Livro resposta = livroService.atualizar(livroParaSerAtualizado);

        assertThat(resposta).isNotNull();
        assertThat(resposta.getId()).isEqualTo(ID);
        assertThat(resposta.getTitulo()).isEqualTo(TITULO);
        assertThat(resposta.getAutor()).isEqualTo(AUTOR);
        assertThat(resposta.getIsbn()).isEqualTo(ISBN);
    }

    @Test
    @DisplayName("Deve lançar a exceção ISBNDuplicadoException ao tentar atualizar um livro com o ISBN de outro livro.")
    void atualizarLivroComISBNExistente() {
        Livro livroParaSerAtualizado = obterLivroComID();
        Livro livroDonoDoISBN = obterOutroLivroComID();

        given(livroRepository.findByIsbn(ISBN)).willReturn(Optional.of(livroDonoDoISBN));

        assertThatThrownBy(() -> livroService.atualizar(livroParaSerAtualizado))
                .isInstanceOf(ISBNDuplicadoException.class)
                .hasMessage(ISBN_DUPLICADO);
    }

    @Test
    @DisplayName("Deve listar todos os livros com sucesso.")
    void listarTodos() {
        Livro primeiroLivro = obterLivroComID();
        Livro segundoLivro = obterOutroLivroComID();
        List<Livro> lista = List.of(primeiroLivro, segundoLivro);

        given(livroRepository.findAll()).willReturn(lista);

        List<Livro> resposta = livroService.listarTodos();

        assertThat(resposta).isNotNull().isNotEmpty().hasSize(2);

        assertThat(resposta.get(0).getId()).isEqualTo(ID);
        assertThat(resposta.get(0).getTitulo()).isEqualTo(TITULO);
        assertThat(resposta.get(0).getAutor()).isEqualTo(AUTOR);
        assertThat(resposta.get(0).getIsbn()).isEqualTo(ISBN);

        assertThat(resposta.get(1).getId()).isEqualTo(OUTRO_ID);
        assertThat(resposta.get(1).getTitulo()).isEqualTo(OUTRO_TITULO);
        assertThat(resposta.get(1).getAutor()).isEqualTo(OUTRO_AUTOR);
        assertThat(resposta.get(1).getIsbn()).isEqualTo(OUTRO_ISBN);
    }

    @Test
    @DisplayName("Deve buscar livro por ID com sucesso.")
    void buscarPorID() {
        Livro livro = obterLivroComID();
        given(livroRepository.findById(ID)).willReturn(Optional.of(livro));

        Livro resposta = livroService.buscarPorID(ID);

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

    private Livro obterOutroLivroComID() {
        return Livro.builder().id(OUTRO_ID).titulo(OUTRO_TITULO).autor(OUTRO_AUTOR).isbn(OUTRO_ISBN).build();
    }
}