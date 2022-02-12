package livraria.domain.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter @Setter @Builder
public class LivroPostRequest {
    private String titulo;
    private String autor;
    @EqualsAndHashCode.Include
    private String isbn;
}
