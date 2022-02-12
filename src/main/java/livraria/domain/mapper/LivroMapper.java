package livraria.domain.mapper;

import livraria.domain.entity.Livro;
import livraria.domain.request.LivroPostRequest;
import livraria.domain.response.LivroGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {
    public static final LivroMapper INSTANCIA = Mappers.getMapper(LivroMapper.class);
    public abstract Livro converterParaLivro(LivroPostRequest livroPostRequest);
    public abstract LivroGetResponse converterParaLivroGetResponse(Livro livro);
}
