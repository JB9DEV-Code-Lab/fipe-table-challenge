package JB9DEV.codelab.FIPEtablechallenge.exceptions;

import JB9DEV.codelab.FIPEtablechallenge.enums.VehicleTypes;

import java.util.Arrays;

public class MissingVehicleTypeException extends RuntimeException {
    public MissingVehicleTypeException() {
        super("The vehicle type is missing, please set one of these: " + Arrays.toString(VehicleTypes.values()));
    }
}
