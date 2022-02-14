package livraria.resource.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter @Setter @NoArgsConstructor
public class ErroPadrao {
    private String caminho;
    private int status;
    private String mensagem;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime momento;
}
