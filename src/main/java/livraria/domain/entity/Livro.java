package livraria.domain.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class Livro {
    private Integer id;
    private String titulo;
    private String autor;
    private String isbn;
}
