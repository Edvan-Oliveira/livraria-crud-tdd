package livraria.resource.exception;

import livraria.service.exception.ConteudoNaoEncontradoException;
import livraria.service.exception.ISBNDuplicadoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ManipuladorException {

    @ExceptionHandler(ISBNDuplicadoException.class)
    public ResponseEntity<ErroPadrao> isbnDuplicadoException(ISBNDuplicadoException excecao,
                                                             HttpServletRequest requisicao) {
        ErroPadrao erro = ErroPadrao.builder()
                .caminho(requisicao.getRequestURI())
                .status(BAD_REQUEST.value())
                .mensagem(excecao.getMessage())
                .momento(LocalDateTime.now())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ConteudoNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> conteudoNaoEncontradoException(ConteudoNaoEncontradoException exception,
                                                                     HttpServletRequest requisicao) {
        ErroPadrao erro = ErroPadrao.builder()
                .caminho(requisicao.getRequestURI())
                .status(BAD_REQUEST.value())
                .mensagem(exception.getMessage())
                .momento(LocalDateTime.now())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(erro);
    }
}
