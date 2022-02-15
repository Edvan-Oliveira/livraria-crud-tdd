package livraria.repository;

import livraria.domain.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
    boolean existsByIsbn(String isbn);
    Optional<Livro> findByIsbn(String isbn);
}
