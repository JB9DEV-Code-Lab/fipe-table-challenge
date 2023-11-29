package JB9DEV.codelab.FIPEtablechallenge.presenters;

import JB9DEV.codelab.FIPEtablechallenge.dtos.VehicleDetailsDTO;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingBrandCodeException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleModelException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleTypeException;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleYearException;
import JB9DEV.codelab.FIPEtablechallenge.interfaces.IPresenter;
import JB9DEV.codelab.FIPEtablechallenge.services.RequestFipeApiService;

public class VehicleDetailsPresenter implements IPresenter {
    private final RequestFipeApiService FIPE_API_SERVICE;
    private VehicleDetailsDTO vehicleDetails;

    public VehicleDetailsPresenter(RequestFipeApiService fipeApiService) {
        this.FIPE_API_SERVICE = fipeApiService;
    }

    // region public methods
    @Override
    public String show() {
        fetchVehicleDetails();
        return parseVehicleDetails();
    }
    // endregion public methods

    // region private methods
    private void fetchVehicleDetails() {
        try {
            vehicleDetails = FIPE_API_SERVICE.fetchVehicleDetails();
        } catch (MissingVehicleTypeException | MissingBrandCodeException | MissingVehicleModelException | MissingVehicleYearException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private String parseVehicleDetails() {
        if (vehicleDetails == null) {
            return "Couldn't fetch vehicle details for the given data";
        }
        return """
        Brand: %s
        Model: %s
        Year: %d
        Month Reference: %s
        Fuel: %s
        Price: %s
        """.formatted(
                vehicleDetails.brand(), vehicleDetails.model(), vehicleDetails.modelYear(), vehicleDetails.monthRef(),
                vehicleDetails.fuel(), vehicleDetails.price());
    }
    // endregion private methods
}
