package livraria.resource;

import livraria.domain.entity.Livro;
import livraria.domain.mapper.LivroMapper;
import livraria.domain.request.LivroPostRequest;
import livraria.domain.request.LivroPutRequest;
import livraria.domain.response.LivroGetResponse;
import livraria.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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

    @PutMapping("/{id}")
    public ResponseEntity<LivroGetResponse> atualizar(@PathVariable Integer id,
                                                      @RequestBody LivroPutRequest livroPutRequest) {
        livroPutRequest.setId(id);
        Livro livro = livroService.atualizar(livroMapper.converterParaLivro(livroPutRequest));
        return ResponseEntity.status(OK).body(livroMapper.converterParaLivroGetResponse(livro));
    }
}
