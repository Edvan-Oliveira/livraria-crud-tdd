package livraria.repository;

import livraria.domain.entity.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class LivroRepositoryTest {

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

    private Livro obterLivroSemID() {
        return Livro.builder().titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }
}