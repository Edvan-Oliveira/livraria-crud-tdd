package livraria.domain.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LivroPutRequest {
    @NotNull(message = "id é obrigatório")
    @EqualsAndHashCode.Include
    private Integer id;
    @NotEmpty(message = "titulo é obrigatório")
    private String titulo;
    @NotEmpty(message = "autor é obrigatório")
    private String autor;
    @NotEmpty(message = "isbn é obrigatório")
    private String isbn;
}
