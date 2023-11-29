package JB9DEV.codelab.FIPEtablechallenge;

import JB9DEV.codelab.FIPEtablechallenge.presenters.*;
import JB9DEV.codelab.FIPEtablechallenge.services.RequestFipeApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FipeTableChallengeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FipeTableChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// region object instances
		IntroductionPresenter introduction = new IntroductionPresenter();
		RequestFipeApiService fipeApiService = new RequestFipeApiService();
		BrandPresenter brandPresenter = new BrandPresenter(fipeApiService);
		ModelPresenter modelPresenter = new ModelPresenter(fipeApiService);
		YearPresenter yearPresenter = new YearPresenter(fipeApiService);
		VehicleDetailsPresenter vehicleDetailsPresenter = new VehicleDetailsPresenter(fipeApiService);
		// endregion object instances

		// region application flow
		String vehicleType = introduction.show();
		fipeApiService.setVehicleType(vehicleType);

		String vehicleBrandCode = brandPresenter.show();
		fipeApiService.setBrandCode(vehicleBrandCode);

		String vehicleModelCode = modelPresenter.show();
		fipeApiService.setModelCode(vehicleModelCode);

		String yearCode = yearPresenter.show();
		if (yearCode.isEmpty()) {
			System.out.println("\n\nHere all available details for this vehicle:");
			yearPresenter.getAvailableYears().forEach(yearDTO -> {
				fipeApiService.setYearCode(yearDTO.code());
				showVehicleDetails(vehicleDetailsPresenter,
						"################################################################################");
			});
		} else {
			fipeApiService.setYearCode(yearCode);
			showVehicleDetails(vehicleDetailsPresenter,
					"\n\nHere are all vehicle details according to your choices:");
		}
		// endregion application flow
	}

	private void showVehicleDetails(VehicleDetailsPresenter vehicleDetailsPresenter, String message) {
		String vehicleDetails = vehicleDetailsPresenter.show();
		System.out.println(message);
		System.out.println(vehicleDetails);
	}
}
