package JB9DEV.codelab.FIPEtablechallenge.exceptions;

public class MissingBrandCodeException extends RuntimeException {
    public MissingBrandCodeException() {
        super("Missing the brand code!");
    }
}
