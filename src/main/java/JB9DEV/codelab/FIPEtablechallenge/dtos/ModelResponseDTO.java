package JB9DEV.codelab.FIPEtablechallenge.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record ModelResponseDTO(
        @JsonAlias("anos")
        List<DefaultResponseDTO> years,
        @JsonAlias("modelos")
        List<DefaultResponseDTO> models
) {
}
