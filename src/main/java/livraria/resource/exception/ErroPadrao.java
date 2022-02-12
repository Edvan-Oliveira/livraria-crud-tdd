package livraria.resource.exception;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class ErroPadrao {
    private String caminho;
    private int status;
    private String mensagem;
    private LocalDateTime momento;
}
