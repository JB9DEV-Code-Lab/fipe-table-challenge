package JB9DEV.codelab.FIPEtablechallenge.presenters;

import JB9DEV.codelab.FIPEtablechallenge.enums.VehicleTypes;
import JB9DEV.codelab.FIPEtablechallenge.interfaces.IPresenter;
import JB9DEV.codelab.FIPEtablechallenge.utils.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class IntroductionPresenter implements IPresenter {
    private final Reader READER = new Reader();

    // region public methods
    @Override
    public String show() {
        System.out.println("""
        ######################################################################################################
                                                 FIPE Table Challenge
        Welcome, here you'll be able to check bikes, cars or trucks prices on Brazilian market, in order to
        compare them considering its brands, models and years. You just need to answer a few questions to get
        the answer, so let's start!
        ######################################################################################################
        """);

        return ask("Which type of vehicle do you whant to search for:");
    }
    // endregion public methods

    // region private methods
    private String ask(String question) {
        List<String> vehicleTypeOptions = new ArrayList<>();
        Arrays.stream(VehicleTypes.values()).forEach(option -> vehicleTypeOptions.add(option.toString().toLowerCase()));
        String answer = READER.ask(question + " " + vehicleTypeOptions);

        if (!vehicleTypeOptions.contains(answer.toLowerCase())) {
            System.out.print("This option is invalid, you must choose one of the given options. ");
            return ask(question);
        }

        return answer;
    }
    // endregion private methods
}
