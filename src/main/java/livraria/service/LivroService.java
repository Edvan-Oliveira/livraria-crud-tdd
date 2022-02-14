package livraria.service;

import livraria.domain.entity.Livro;
import livraria.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro cadastrar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Livro atualizar(Livro livro) {
        return null;
    }

    public List<Livro> listarTodos() {
        return null;
    }

    public Livro buscarPorID(Integer id) {
        return null;
    }

    public void deletar(Integer id) {
    }
}
