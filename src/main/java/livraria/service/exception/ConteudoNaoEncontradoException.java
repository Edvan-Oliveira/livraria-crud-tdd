package livraria.service.exception;

public class ConteudoNaoEncontradoException extends RuntimeException {

    public ConteudoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
