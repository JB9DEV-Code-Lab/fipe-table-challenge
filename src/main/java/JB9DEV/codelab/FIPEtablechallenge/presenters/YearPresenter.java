package JB9DEV.codelab.FIPEtablechallenge.presenters;

import JB9DEV.codelab.FIPEtablechallenge.dtos.DefaultResponseDTO;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingBrandCodeException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleModelException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleTypeException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleYearException;
import JB9DEV.codelab.FIPEtablechallenge.interfaces.IPresenter;
import JB9DEV.codelab.FIPEtablechallenge.services.RequestFipeApiService;
import JB9DEV.codelab.FIPEtablechallenge.utils.Reader;

import java.util.*;

public class YearPresenter implements IPresenter {
    private final RequestFipeApiService FIPE_API_SERVICE;
    private final Reader READER = new Reader();
    private List<DefaultResponseDTO> availableYears;
    private final Map<String, Map<String, String>> FUEL_MAP = new HashMap<>();

    public YearPresenter(RequestFipeApiService fipeApiService) {
        this.FIPE_API_SERVICE = fipeApiService;
    }

    // region public methods
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
            availableYears = FIPE_API_SERVICE.fetchYears();
        } catch(MissingVehicleTypeException | MissingBrandCodeException | MissingVehicleModelException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void setFuelMap() {
        Set<String> fuelSet = new HashSet<>();

        availableYears.forEach(yearDTO -> fuelSet.add(splitYearDTOName(yearDTO)[1]));
        fuelSet.forEach(fuel -> {
            Map<String, String> codeYearMap = new HashMap<>();

            availableYears.forEach(yearDTO -> {
                if (yearDTO.name().contains(fuel)) {
                    codeYearMap.put(yearDTO.code(), splitYearDTOName(yearDTO)[0]);
                }
            });
            FUEL_MAP.put(fuel, codeYearMap);
        });
    }

    private String[] splitYearDTOName(DefaultResponseDTO yearDTO) {
        return yearDTO.name().split("\\s");
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

