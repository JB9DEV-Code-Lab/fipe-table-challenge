package JB9DEV.codelab.FIPEtablechallenge.exceptions;

public class MissingVehicleYearException extends RuntimeException {
    public MissingVehicleYearException() {
        super("The vehicle year code is missing, please set one according to the given years");
    }
}
