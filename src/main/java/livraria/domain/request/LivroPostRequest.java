package livraria.domain.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter @Setter @Builder
public class LivroPostRequest {
    @NotEmpty(message = "titulo é obrigatório")
    private String titulo;
    @NotEmpty(message = "autor é obrigatório")
    private String autor;
    @NotEmpty(message = "isbn é obrigatório")
    @EqualsAndHashCode.Include
    private String isbn;
}
