package livraria.domain.mapper;

import livraria.domain.entity.Livro;
import livraria.domain.request.LivroPostRequest;
import livraria.domain.request.LivroPutRequest;
import livraria.domain.response.LivroGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {
    public static final LivroMapper INSTANCIA = Mappers.getMapper(LivroMapper.class);
    public abstract Livro converterParaLivro(LivroPostRequest livroPostRequest);
    public abstract LivroGetResponse converterParaLivroGetResponse(Livro livro);
    public abstract Livro converterParaLivro(LivroPutRequest livroPutRequest);
    public abstract List<LivroGetResponse> converterParaListaDeLivroGetResponse(List<Livro> listaDeLivros);
}
