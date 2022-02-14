package livraria.resource.exception;

import livraria.service.exception.ConteudoNaoEncontradoException;
import livraria.service.exception.ISBNDuplicadoException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ManipuladorException {

    @ExceptionHandler({
            ISBNDuplicadoException.class,
            ConteudoNaoEncontradoException.class
    })
    public ResponseEntity<ErroPadrao> isbnDuplicadoException(RuntimeException excecao, HttpServletRequest requisicao) {
        ErroPadrao erro = ErroPadrao.builder()
                .caminho(requisicao.getRequestURI())
                .status(BAD_REQUEST.value())
                .mensagem(excecao.getMessage())
                .momento(LocalDateTime.now())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> methodArgumentNotValidException(MethodArgumentNotValidException excecao,
                                                                      HttpServletRequest requisicao) {
        ErroPadrao erro = ErroPadrao.builder()
                .caminho(requisicao.getRequestURI())
                .status(BAD_REQUEST.value())
                .mensagem(obterMensagemDeValidacao(excecao))
                .momento(LocalDateTime.now())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(erro);
    }

    private String obterMensagemDeValidacao(MethodArgumentNotValidException excecao) {
        List<String> erros = excecao.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        StringBuilder mensagem = new StringBuilder();
        for (String erro: erros) mensagem.append(" => ").append(erro);

        return mensagem.toString();
    }
}
