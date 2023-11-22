package JB9DEV.codelab.FIPEtablechallenge.presenters;

import JB9DEV.codelab.FIPEtablechallenge.dtos.DefaultResponseDTO;
import JB9DEV.codelab.FIPEtablechallenge.exceptions.MissingVehicleTypeException;
import JB9DEV.codelab.FIPEtablechallenge.interfaces.IPresenter;
import JB9DEV.codelab.FIPEtablechallenge.services.RequestFipeApiService;
import JB9DEV.codelab.FIPEtablechallenge.utils.Reader;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BrandPresenter implements IPresenter {
    private final Reader READER = new Reader();
    private List<DefaultResponseDTO> brands;
    private RequestFipeApiService fipeApiService;

    public BrandPresenter(RequestFipeApiService fipeApiService) {
        this.fipeApiService = fipeApiService;
    }

    // region public methods
    @Override
    public String show() {
        String vehicleType = fipeApiService.getVehicleType();
        fetchBrands(vehicleType);
        String favoriteBrand = READER.ask("Is there any favorite brand which do you want search for?",
                "If there is any please type it down, if not just press enter.");

        if (favoriteBrand != null && !favoriteBrand.isEmpty()) {
            Optional<DefaultResponseDTO> filteredFavoriteBrand = filterFavoriteBrand(favoriteBrand);

            if (filteredFavoriteBrand.isPresent()) {
                return filteredFavoriteBrand.get().code();
            }

            return filterBrandByFirstLetter(favoriteBrand);
        }

        listAllBrands();

        return filterBrandByCode();
    }
    // endregion public methods

    // region private methods
    private void fetchBrands(String vehicleType) {
        try {
            brands = fipeApiService.getBrands();
        } catch (MissingVehicleTypeException exception) {
            System.out.println("An error happened, here are the details: " + exception.getMessage());
        }
    }

    private Optional<DefaultResponseDTO> filterFavoriteBrand(String favoriteBrand) {
        return brands.stream()
                .filter(brand -> brand.name().toLowerCase().contains(favoriteBrand.toLowerCase()))
                .findFirst();
    }

    private String filterBrandByFirstLetter(String favoriteBrand) {
        List<DefaultResponseDTO> matchingBrandByFirstLetter = brands.stream()
                .filter(brand -> brand.name().toLowerCase().startsWith(
                        Character.toString(favoriteBrand.toLowerCase().charAt(0))))
                .toList();

        if (matchingBrandByFirstLetter.size() == 1) {
            DefaultResponseDTO uniqueBrandOption = matchingBrandByFirstLetter.getFirst();
            System.out.printf(
                    "Couldn't find %s, but starting with %s there is this one %s:%n",
                    favoriteBrand,
                    uniqueBrandOption.name().toUpperCase().charAt(0),
                    uniqueBrandOption.name()
            );

            String shouldUseIt = READER.ask("Is this one good for you?", "y / n");
            if (shouldUseIt.equalsIgnoreCase("y")) {
                System.out.printf("Ok, using %s as chosen brand!%n", uniqueBrandOption.name());
                return uniqueBrandOption.code();
            }
        }

        System.out.printf(
                "Couldn't find %s. Here is a list with all brands starting by %s:%n",
                favoriteBrand,
                favoriteBrand.toUpperCase().charAt(0)
        );
        matchingBrandByFirstLetter.forEach(brand -> System.out.println(brand.code() + ": " + brand.name()));
        return filterBrandByCode();
    }

    private String filterBrandByCode() {
        int chosenBrand = READER.askForInteger("Which brand do you want?",
                "Chose a brand by its code (the number before \":\")"
        );
        Optional<DefaultResponseDTO> filteredBrand = brands.stream()
                .filter(brand -> brand.code().equals(Integer.toString(chosenBrand)))
                .findFirst();

        if (filteredBrand.isPresent()) {
            return filteredBrand.get().code();
        }

        System.out.println("This code doesn't match to any listed code, please choose one of these:");
        listAllBrands();

        return filterBrandByCode();
    }

    private void listAllBrands() {
        StringBuilder brandsToPrint = new StringBuilder();
        brands.forEach(brand -> brandsToPrint.append(brand.code()).append(": ").append(brand.name()).append("\n"));

        System.out.println(brandsToPrint);
    }
    // endregion private methods
}
