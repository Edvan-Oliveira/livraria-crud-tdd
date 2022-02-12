package livraria.resource;

import livraria.domain.entity.Livro;
import livraria.domain.mapper.LivroMapper;
import livraria.domain.request.LivroPostRequest;
import livraria.domain.response.LivroGetResponse;
import livraria.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroResource {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<LivroGetResponse> cadastrar(@RequestBody LivroPostRequest livroPostRequest) {
        Livro livro = livroService.cadastrar(livroMapper.converterParaLivro(livroPostRequest));
        return ResponseEntity.status(CREATED).body(livroMapper.converterParaLivroGetResponse(livro));
    }
}
