package JB9DEV.codelab.FIPEtablechallenge.presenters;

import JB9DEV.codelab.FIPEtablechallenge.dtos.DefaultResponseDTO;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingBrandCodeException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleTypeException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleYearException;
import JB9DEV.codelab.FIPEtablechallenge.interfaces.IPresenter;
import JB9DEV.codelab.FIPEtablechallenge.services.RequestFipeApiService;
import JB9DEV.codelab.FIPEtablechallenge.utils.Reader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YearPresenter implements IPresenter {
    private final RequestFipeApiService FIPE_API_SERVICE;
    private final Reader READER = new Reader();
    private List<DefaultResponseDTO> availableYears;
    private final Map<String, Map<String, String>> FUEL_MAP = new HashMap<>();

    public YearPresenter(RequestFipeApiService fipeApiService) {
        this.FIPE_API_SERVICE = fipeApiService;
    }

    @Override
    public String show() {
        fetchYears();
        setFuelMap();
        showFuelYearOptions();

        return chooseYear();
    }

    public List<DefaultResponseDTO> getAvailableYears() {
        return availableYears;
    }
    // endregion public methods

    // region private methods
    private void fetchYears() {
        try {
            availableYears = FIPE_API_SERVICE.getYears();
        } catch(MissingBrandCodeException | MissingVehicleTypeException | MissingVehicleYearException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void setFuelMap() {
        Map<String, String> codeYearMap = new HashMap<>();

        availableYears.forEach(yearDTO -> {
            String[] yearFuel = yearDTO.name().split("\\s");
            codeYearMap.put(yearDTO.code(), yearFuel[0]);
            if (yearFuel.length > 1) {
                FUEL_MAP.put(yearFuel[1], codeYearMap);
            } else {
                FUEL_MAP.put("fuel not available for %s".formatted(FIPE_API_SERVICE.getModelCode()), codeYearMap);
            }
        });
    }

    private void showFuelYearOptions() {
        FUEL_MAP.keySet().forEach(key -> {
            System.out.println(key + ": ");

            Map<String, String> codeYearMap = FUEL_MAP.get(key);
            codeYearMap.keySet().forEach(code -> System.out.println(code + ": " + codeYearMap.get(code)));
        });
    }

    private boolean isChosenYearValid(String chosenYear) {
        if (chosenYear.isEmpty()) {
            return true;
        }

        return availableYears.stream().anyMatch(year -> year.code().equals(chosenYear));
    }

    private String chooseYear() {
        String chosenYear = READER.ask("Which year you want?",
                "type down the code before \": \". If you want to see  not %s for all these options just press enter"
                        .formatted(FIPE_API_SERVICE.getVehicleType()));

        if (!isChosenYearValid(chosenYear)) {
            System.out.println("This year is invalid, please select one from the list below");
            showFuelYearOptions();
            return chooseYear();
        }

        return chosenYear;
    }
    // endregion private methods
}

