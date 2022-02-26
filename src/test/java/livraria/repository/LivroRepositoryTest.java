package livraria.repository;

import livraria.domain.entity.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class LivroRepositoryTest {

    private static final String OUTRO_TITULO = "Patui Livro";
    private static final String OUTRO_AUTOR = "Iutro";
    private static final String OUTRO_ISBN = "654";

    private static final Integer ID =  17;
    private static final String TITULO = "Um Livro";
    private static final String AUTOR = "Ghuto";
    private static final String ISBN = "357";

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deve retornar verdadeiro se o ISBN já está cadastrado no banco de dados.")
    void verificarSeISBNExiste() {
        Livro livroDonoDoISBN = obterLivroSemID();
        entityManager.persist(livroDonoDoISBN);
        boolean resposta = livroRepository.existsByIsbn(ISBN);
        assertTrue(resposta);
    }

    @Test
    @DisplayName("Deve retornar falso se o ISBN não existe na base de dados.")
    void verificarSeISBNNaoExiste() {
        boolean resposta = livroRepository.existsByIsbn(ISBN);
        assertFalse(resposta);
    }

    @Test
    @DisplayName("Deve cadastrar um livro com sucesso.")
    void cadastrar() {
        Livro livroParaSerSalvo = obterLivroSemID();
        Livro livroSalvo = livroRepository.save(livroParaSerSalvo);

        assertThat(livroSalvo).isNotNull();
        assertThat(livroSalvo.getId()).isNotNull();
        assertThat(livroSalvo.getTitulo()).isEqualTo(TITULO);
        assertThat(livroSalvo.getAutor()).isEqualTo(AUTOR);
        assertThat(livroSalvo.getIsbn()).isEqualTo(ISBN);
    }

    @Test
    @DisplayName("Deve atualizar um livro com sucesso.")
    void atualizar() {
        Livro livroParaSerAtualizado = obterLivroSemID();
        entityManager.persist(livroParaSerAtualizado);
        String novoTitulo = TITULO.concat("!!!");
        livroParaSerAtualizado.setTitulo(novoTitulo);

        livroRepository.save(livroParaSerAtualizado);
        Livro livroAtualizado = entityManager.find(Livro.class, livroParaSerAtualizado.getId());

        assertThat(livroAtualizado).isNotNull();
        assertThat(livroAtualizado.getId()).isEqualTo(livroParaSerAtualizado.getId());
        assertThat(livroAtualizado.getTitulo()).isEqualTo(novoTitulo);
        assertThat(livroAtualizado.getAutor()).isEqualTo(AUTOR);
        assertThat(livroAtualizado.getIsbn()).isEqualTo(ISBN);
    }

    @Test
    @DisplayName("Deve listar todos os livros com sucesso.")
    void listarTodos() {
        Livro primeiroLivro = obterLivroSemID();
        Livro segundoLivro = obterOutroLivroSemID();

        entityManager.persist(primeiroLivro);
        entityManager.persist(segundoLivro);

        List<Livro> lista = livroRepository.findAll();

        assertThat(lista).isNotNull().isNotEmpty().hasSize(2);

        assertThat(lista.get(0).getId()).isEqualTo(primeiroLivro.getId());
        assertThat(lista.get(0).getTitulo()).isEqualTo(TITULO);
        assertThat(lista.get(0).getAutor()).isEqualTo(AUTOR);
        assertThat(lista.get(0).getIsbn()).isEqualTo(ISBN);

        assertThat(lista.get(1).getId()).isEqualTo(segundoLivro.getId());
        assertThat(lista.get(1).getTitulo()).isEqualTo(OUTRO_TITULO);
        assertThat(lista.get(1).getAutor()).isEqualTo(OUTRO_AUTOR);
        assertThat(lista.get(1).getIsbn()).isEqualTo(OUTRO_ISBN);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia.")
    void listarTodosComListaVazia() {
        List<Livro> lista = livroRepository.findAll();
        assertThat(lista).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Deve buscar um livro por ID com sucesso.")
    void buscarPorID() {
        Livro livroParaSerSalvo = obterLivroSemID();
        entityManager.persist(livroParaSerSalvo);

        Optional<Livro> livroSalvo = livroRepository.findById(livroParaSerSalvo.getId());

        assertThat(livroSalvo).isPresent();
        assertThat(livroSalvo.get().getId()).isEqualTo(livroParaSerSalvo.getId());
        assertThat(livroSalvo.get().getTitulo()).isEqualTo(TITULO);
        assertThat(livroSalvo.get().getAutor()).isEqualTo(AUTOR);
        assertThat(livroSalvo.get().getIsbn()).isEqualTo(ISBN);
    }

    @Test
    @DisplayName("Deve retornar vazio ao tentar buscar por ID de um livro que não existe.")
    void buscaPorIDLivroInexistente() {
        Optional<Livro> resultado = livroRepository.findById(ID);
        assertThat(resultado.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Deve deletar um livro com sucesso.")
    void deletar() {
        Livro livroParaSerSalvo = obterLivroSemID();
        entityManager.persist(livroParaSerSalvo);
        livroRepository.deleteById(livroParaSerSalvo.getId());
        Livro livroDeletado = entityManager.find(Livro.class, livroParaSerSalvo.getId());
        assertThat(livroDeletado).isNull();
    }

    private Livro obterLivroSemID() {
        return Livro.builder().titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }

    private Livro obterOutroLivroSemID() {
        return Livro.builder().titulo(OUTRO_TITULO).autor(OUTRO_AUTOR).isbn(OUTRO_ISBN).build();
    }
}