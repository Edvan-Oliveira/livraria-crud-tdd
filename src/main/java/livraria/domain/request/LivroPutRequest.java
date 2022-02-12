package livraria.domain.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LivroPutRequest {
    @EqualsAndHashCode.Include
    private Integer id;
    private String titulo;
    private String autor;
    private String isbn;
}
