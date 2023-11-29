package JB9DEV.codelab.FIPEtablechallenge.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

public record VehicleDetailsDTO(
        @JsonAlias("CodigoFipe")
        String fipeCode,
        @JsonAlias("Marca")
        String brand,
        @JsonAlias("Combustivel")
        String fuel,
        @JsonAlias("SiglaCombustivel")
        String fuelAcronym,
        @JsonAlias("Modelo")
        String model,
        @JsonAlias("AnoModelo")
        int modelYear,
        @JsonAlias("MesReferencia")
        String monthRef,
        @JsonAlias("Valor")
        String price,
        @JsonAlias("TipoVeiculo")
        int vehicleTypeCode
        ) {
}
