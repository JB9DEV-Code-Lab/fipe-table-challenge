package JB9DEV.codelab.FIPEtablechallenge.dtos;


import com.fasterxml.jackson.annotation.JsonAlias;

public record DefaultResponseDTO(
        @JsonAlias("codigo")
        String code,
        @JsonAlias("nome")
        String name
) {
}
