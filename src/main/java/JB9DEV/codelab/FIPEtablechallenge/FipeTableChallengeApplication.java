package JB9DEV.codelab.FIPEtablechallenge;

import JB9DEV.codelab.FIPEtablechallenge.presenters.BrandPresenter;
import JB9DEV.codelab.FIPEtablechallenge.presenters.IntroductionPresenter;
import JB9DEV.codelab.FIPEtablechallenge.presenters.ModelPresenter;
import JB9DEV.codelab.FIPEtablechallenge.presenters.YearPresenter;
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
		// endregion object instances

		// region application flow
		String vehicleType = introduction.show();
		fipeApiService.setVehicleType(vehicleType);

		String vehicleBrandCode = brandPresenter.show();
		fipeApiService.setBrandCode(vehicleBrandCode);

		String vehicleModelCode = modelPresenter.show();
		fipeApiService.setModelCode(vehicleModelCode);

		String yearCode = yearPresenter.show();
		fipeApiService.setYearCode(yearCode);

		System.out.printf("""
			Vehicle type: %s
			Brand: %s
			Model: %s
			Year: %s
		""", vehicleType, vehicleBrandCode, vehicleModelCode, yearCode);
		// endregion application flow
	}
}
