package JB9DEV.codelab.FIPEtablechallenge;

import JB9DEV.codelab.FIPEtablechallenge.presenters.BrandPresenter;
import JB9DEV.codelab.FIPEtablechallenge.presenters.IntroductionPresenter;
import JB9DEV.codelab.FIPEtablechallenge.presenters.ModelPresenter;
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
		BrandPresenter brand = new BrandPresenter(fipeApiService);
		ModelPresenter model = new ModelPresenter(fipeApiService);
		// endregion object instances

		// region application flow
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
		// endregion application flow
	}
}
