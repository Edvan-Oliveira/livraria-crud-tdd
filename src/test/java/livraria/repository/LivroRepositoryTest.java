package livraria.repository;

import livraria.domain.entity.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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

    private Livro obterLivroSemID() {
        return Livro.builder().titulo(TITULO).autor(AUTOR).isbn(ISBN).build();
    }
}