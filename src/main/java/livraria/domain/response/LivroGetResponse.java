package livraria.domain.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class LivroGetResponse {
    private Integer id;
    private String titulo;
    private String autor;
    private String isbn;
}
