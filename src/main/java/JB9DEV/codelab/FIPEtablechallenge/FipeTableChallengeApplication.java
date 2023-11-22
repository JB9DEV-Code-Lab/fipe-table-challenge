package JB9DEV.codelab.FIPEtablechallenge;

import JB9DEV.codelab.FIPEtablechallenge.presenters.BrandPresenter;
import JB9DEV.codelab.FIPEtablechallenge.presenters.IntroductionPresenter;
import JB9DEV.codelab.FIPEtablechallenge.presenters.ModelPresenter;
import JB9DEV.codelab.FIPEtablechallenge.services.RequestFipeApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class FipeTableChallengeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FipeTableChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* Steps
			* [X] call presenter introduction, to give a small introduction about the application
			* [X] call reader to ask which type of vehicle the person wants to search for
			* [X] call reader to ask if there is any specific brand that the person wants to filter (brand name if yes, blank if no)
				if the brand isn't in the list return by the API
					[X] call filter to get only the brands with the same first letter
					[X] call presenter to list them
				if there is no brand with the same first letter
					[X] call presenter to show all brands
				if there is
					[X] call presenter to show them
					[X] call reader to ask if one of them should be the one or if the person wants to list all of them
					[X] if that is the case, call reader to ask which branch the person wants to search for
			* [x±] call reader to ask which of the models the person wants to search for
			* [ ] call presenter to list all models and years that matches the choices
			*
			*
		* It'll require:
			* 	DTO for:
				* [X] brands
				* [x] model
				* [ ] vehicles
			* 	services to:
				* [X] fetch data from an API
				* [X] desirialize from DTO
				* [ ] serialize to DTO
			* 	presenters to handle what should be asked and printed
				* [ ] should have a presenter for each step in the application flow
				* 	[x] introduction
				* 	[x] brand
				* 	[x] model
				* 	[ ] vehicle
				* [x] should have a public method to show what should be presented in each step and the presenter knows
		*/
		// region object instances
		IntroductionPresenter introduction = new IntroductionPresenter();
		RequestFipeApiService fipeApiService = new RequestFipeApiService();
		BrandPresenter brand = new BrandPresenter(fipeApiService);
		ModelPresenter model = new ModelPresenter(fipeApiService);
		// endregion object instances

		String vehicleType = introduction.show();
		fipeApiService.setVehicleType(vehicleType);

		String vehicleBrandCode = brand.show();
		fipeApiService.setBrandCode(vehicleBrandCode);

		String vehicleModelCode = model.show();
		fipeApiService.setModelCode(vehicleModelCode);

		System.out.printf("""
			Vehicle type: %s
			Brand: %s
			Model: %s
		""", vehicleType, vehicleBrandCode, vehicleModelCode);
	}
}
