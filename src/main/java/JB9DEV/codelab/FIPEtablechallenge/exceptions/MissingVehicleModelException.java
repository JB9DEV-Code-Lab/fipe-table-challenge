package JB9DEV.codelab.FIPEtablechallenge.exceptions;

public class MissingVehicleModelException extends RuntimeException {
    public MissingVehicleModelException() {
        super("Vehicle model is missing, please select one of the given models");
    }
}
