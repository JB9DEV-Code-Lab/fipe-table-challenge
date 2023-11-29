package JB9DEV.codelab.FIPEtablechallenge.presenters;

import JB9DEV.codelab.FIPEtablechallenge.dtos.DefaultResponseDTO;
import JB9DEV.codelab.FIPEtablechallenge.dtos.ModelResponseDTO;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingBrandCodeException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleTypeException;
import JB9DEV.codelab.FIPEtablechallenge.interfaces.IPresenter;
import JB9DEV.codelab.FIPEtablechallenge.services.RequestFipeApiService;
import JB9DEV.codelab.FIPEtablechallenge.utils.Reader;

import java.util.List;
import java.util.Optional;

public class ModelPresenter implements IPresenter {
    private final Reader READER = new Reader();
    private final RequestFipeApiService FIPE_API_SERVICE;
    private ModelResponseDTO modelResponse;

    public ModelPresenter(RequestFipeApiService fipeApiService) {
        this.FIPE_API_SERVICE = fipeApiService;
    }

    @Override
    public String show() {
        fetchModels();

        return filterModel();
    }

    // region private methods
    private void fetchModels() {
        try {
            modelResponse = FIPE_API_SERVICE.fetchModels();
        } catch (MissingBrandCodeException | MissingVehicleTypeException exception) {
            System.out.println("Couldn't get models, details: " + exception.getMessage());
        }
    }

    private String filterModel() {
        String favoriteModel = filterByFavoriteModel();
        if (!favoriteModel.isEmpty()) {
            return favoriteModel;
        }

        String modelCode = choseModelFromList(modelResponse.models());
        Optional<DefaultResponseDTO> filteredModel = filterModelByCode(modelCode);
        if (filteredModel.isEmpty()) {
            System.out.println("This code isn't available in the models list, please choose one from the list below:");

            return filterModel();
        }

        return filteredModel.get().code();
    }

    private String filterByFavoriteModel() {
        String favoriteModel = READER.ask(
                "Is there any favorite model?",
                "if there is any please type it down, if not just press enter");

        if (favoriteModel.isEmpty()) {
            return favoriteModel;
        }

        List<DefaultResponseDTO> favoriteModelsFiltered = modelResponse.models().stream()
                .filter(model -> model.name().toLowerCase().contains(favoriteModel.toLowerCase()))
                .toList();

        if (favoriteModelsFiltered.isEmpty()) {
            char favoriteModelFirstLetter = favoriteModel.charAt(0);
            System.out.printf("Couldn't find %s, but here are all models starting by %s",
                    favoriteModel, Character.toUpperCase(favoriteModelFirstLetter));

            return filterModelByFirstLetter(favoriteModelFirstLetter);
        }

        if (favoriteModelsFiltered.size() == 1) {
            return favoriteModelsFiltered.get(0).code();
        }

        String modelCode = choseModelFromList(favoriteModelsFiltered);

        Optional<DefaultResponseDTO> chosenModelCode = filterModelByCode(modelCode);

        if (chosenModelCode.isPresent()) {
            return chosenModelCode.get().code();
        }

        return "";
    }

    private Optional<DefaultResponseDTO> filterModelByCode(String modelCode) {
        return modelResponse.models().stream().filter(model -> model.code().equals(modelCode)).findFirst();
    }
    private String filterModelByFirstLetter(char firstLetter) {
        List<DefaultResponseDTO> modelsList = modelResponse.models().stream()
            .filter(model -> model.name().toLowerCase().startsWith(
                    Character.toString(firstLetter).toLowerCase())).toList();

        String modelCode = choseModelFromList(modelsList);
        Optional<DefaultResponseDTO> modelFilteredByCode = filterModelByCode(modelCode);
        if (modelFilteredByCode.isPresent()) {
            return modelFilteredByCode.get().code();
        }

        return "";
    }

    private String choseModelFromList(List<DefaultResponseDTO> modelsList) {
        modelsList.forEach(model -> System.out.println(model.code() + ": " + model.name()));
        return READER.ask("Do you want any one of these models from the list above?",
                "if yes type down the code before \":\", if not just press enter");
    }

    // endregion private methods
}
