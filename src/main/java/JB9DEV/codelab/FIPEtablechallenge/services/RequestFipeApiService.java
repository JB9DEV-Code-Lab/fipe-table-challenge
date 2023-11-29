package JB9DEV.codelab.FIPEtablechallenge.services;

import JB9DEV.codelab.FIPEtablechallenge.dtos.DefaultResponseDTO;
import JB9DEV.codelab.FIPEtablechallenge.dtos.ModelResponseDTO;
import JB9DEV.codelab.FIPEtablechallenge.dtos.VehicleDetailsDTO;
import JB9DEV.codelab.FIPEtablechallenge.enums.FipeApiCategories;
import JB9DEV.codelab.FIPEtablechallenge.enums.VehicleTypes;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingBrandCodeException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleModelException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleTypeException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleYearException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

public class RequestFipeApiService {
    // region final fields
    private final String BASE_URL = "https://parallelum.com.br/fipe/api/v1";
    private final JsonSerializerService JSON_SERIALIZER = new JsonSerializerService();
    private final RequestAPIService API_SERVICE = new RequestAPIService(BASE_URL);
    private final Map<Enum<? extends Enum<?>>, String> pathMapping = Map.ofEntries(
            Map.entry(VehicleTypes.BIKE, "/motos"),
            Map.entry(FipeApiCategories.BRANDS, "/marcas"),
            Map.entry(FipeApiCategories.MODELS, "/modelos"),
            Map.entry(VehicleTypes.CAR, "/carros"),
            Map.entry(VehicleTypes.TRUCK, "/caminhoes"),
            Map.entry(FipeApiCategories.YEARS, "/anos")
    );
    // endregion final fields

    // region fields
    private String brandCode;
    private VehicleTypes vehicleType;
    private String modelCode;

    private String yearCode;
    // endregion fields
    // region getters
    public String getVehicleType() {
        return vehicleType.toString();
    }
    // endregion getters

    // region setters
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public void setModelCode(String model) {
        this.modelCode = model;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public void setVehicleType(String vehicleType) {
        Map<String, VehicleTypes> vehicleTypeMapping = Map.of(
                "bike", VehicleTypes.BIKE,
                "car", VehicleTypes.CAR,
                "truck", VehicleTypes.TRUCK
        );

        this.vehicleType = vehicleTypeMapping.get(vehicleType);
    }
    // endregion setters

    // region public methods
    public List<DefaultResponseDTO> fetchBrands() throws MissingVehicleTypeException {
        String brands = API_SERVICE.get(getBrandsPath());

        return JSON_SERIALIZER.deserialize(brands, new TypeReference<>(){});
    }

    public ModelResponseDTO fetchModels() throws MissingVehicleTypeException, MissingBrandCodeException {
        String models = API_SERVICE.get(getModelsPath());
        return JSON_SERIALIZER.deserialize(models, ModelResponseDTO.class);
    }

    public List<DefaultResponseDTO> fetchYears()
            throws MissingVehicleTypeException, MissingBrandCodeException, MissingVehicleModelException {
        String vehicleDetails = API_SERVICE.get(getYearsPath());

        return JSON_SERIALIZER.deserialize(vehicleDetails, new TypeReference<>(){});
    }

    public VehicleDetailsDTO fetchVehicleDetails()
            throws MissingVehicleTypeException, MissingBrandCodeException, MissingVehicleModelException, MissingVehicleYearException {

        String vehicleDetails = API_SERVICE.get(getVehicleDetailsPath());
        return JSON_SERIALIZER.deserialize(vehicleDetails, VehicleDetailsDTO.class);
    }
    // endregion public methods

    // region private methods
    private String getBrandsPath() throws MissingVehicleTypeException {
        if (vehicleType == null) {
            throw new MissingVehicleTypeException();
        }
        return getPath(vehicleType) + getPath(FipeApiCategories.BRANDS);
    }

    private String getModelsPath() throws MissingVehicleTypeException, MissingBrandCodeException {
        if (brandCode == null) {
            throw new MissingBrandCodeException();
        }

        return getBrandsPath() + "/" + brandCode + getPath(FipeApiCategories.MODELS);
    }

    private String getYearsPath()
            throws MissingVehicleTypeException, MissingBrandCodeException, MissingVehicleModelException {

        if (modelCode == null) {
            throw new MissingVehicleModelException();
        }
        return getModelsPath() + "/" + modelCode + getPath(FipeApiCategories.YEARS);
    }

    private String getVehicleDetailsPath()
            throws MissingVehicleTypeException, MissingBrandCodeException, MissingVehicleModelException, MissingVehicleYearException {

        if (yearCode == null) {
            throw new MissingVehicleYearException();
        }
        return getYearsPath() + "/" + yearCode;
    }

    private String getPath(Enum<? extends Enum<?>> key) {
        String path = pathMapping.get(key);

        if (key == null) {
            return "";
        }

        return path;
    }
    // endregion private methods
}
