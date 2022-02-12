package livraria.service.exception;

public class ISBNDuplicadoException extends RuntimeException {

    public ISBNDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
