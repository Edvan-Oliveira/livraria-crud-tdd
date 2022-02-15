package livraria.service;

import livraria.domain.entity.Livro;
import livraria.repository.LivroRepository;
import livraria.service.exception.ConteudoNaoEncontradoException;
import livraria.service.exception.ISBNDuplicadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LivroService {

    private static final String ISBN_DUPLICADO = "ISBN já cadastrado";
    private static final String LIVRO_NAO_ENCONTRADO = "Livro não encontrado";

    private final LivroRepository livroRepository;

    public Livro cadastrar(Livro livro) {
        if (livroRepository.existsByIsbn(livro.getIsbn()))
            throw new ISBNDuplicadoException(ISBN_DUPLICADO);
        return livroRepository.save(livro);
    }

    public Livro atualizar(Livro livro) {
        Optional<Livro> livroSalvo = livroRepository.findByIsbn(livro.getIsbn());
        if (livroSalvo.isPresent() && !livroSalvo.get().getId().equals(livro.getId()))
            throw new ISBNDuplicadoException(ISBN_DUPLICADO);
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorID(Integer id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ConteudoNaoEncontradoException(LIVRO_NAO_ENCONTRADO));
    }

    public void deletar(Integer id) {
        livroRepository.delete(buscarPorID(id));
    }
}
