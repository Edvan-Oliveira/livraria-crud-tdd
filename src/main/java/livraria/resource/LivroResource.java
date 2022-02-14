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

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroResource {

    private static final String URL_ID = "/{id}";

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<LivroGetResponse> cadastrar(@RequestBody @Valid LivroPostRequest livroPostRequest) {
        Livro livro = livroService.cadastrar(livroMapper.converterParaLivro(livroPostRequest));
        return ResponseEntity.status(CREATED).body(livroMapper.converterParaLivroGetResponse(livro));
    }

    @PutMapping(URL_ID)
    public ResponseEntity<LivroGetResponse> atualizar(@PathVariable Integer id,
                                                      @Valid @RequestBody LivroPutRequest livroPutRequest) {
        livroPutRequest.setId(id);
        Livro livro = livroService.atualizar(livroMapper.converterParaLivro(livroPutRequest));
        return ResponseEntity.status(OK).body(livroMapper.converterParaLivroGetResponse(livro));
    }

    @GetMapping
    public ResponseEntity<List<LivroGetResponse>> listarTodos() {
        return ResponseEntity.status(OK)
                .body(livroMapper.converterParaListaDeLivroGetResponse(livroService.listarTodos()));
    }

    @GetMapping(URL_ID)
    public ResponseEntity<LivroGetResponse> buscarPorID(@PathVariable Integer id) {
        return ResponseEntity.status(OK).body(livroMapper.converterParaLivroGetResponse(livroService.buscarPorID(id)));
    }

    @DeleteMapping(URL_ID)
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        livroService.deletar(id);
        return ResponseEntity.status(NO_CONTENT).body(null);
    }
}
