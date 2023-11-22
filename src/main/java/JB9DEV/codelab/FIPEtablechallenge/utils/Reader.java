package JB9DEV.codelab.FIPEtablechallenge.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Reader {
    // region public methods
    public String ask(String question) {
        return ask(question, "");
    }

    public String ask(String question, String tip) {
        askQuestion(question, tip);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int askForInteger(String question) {
        return askForInteger(question, "");
    }

    public int askForInteger(String question, String tip) {
        askQuestion(question, tip);
        int answer;
        Scanner scanner = new Scanner(System.in);

        try {
            answer = scanner.nextInt();
            scanner.nextLine();

            return answer;
        } catch (InputMismatchException exception) {
            System.out.println("Please type a number without decimals.");
            return askForInteger(question);
        }
    }

    public double askForDouble(String question) {
        return askForDouble(question, "");
    }

    public double askForDouble(String question, String tip) {
        askQuestion(question, tip);
        double answer;
        Scanner scanner = new Scanner(System.in);

        try {
            answer = scanner.nextDouble();
            scanner.nextLine();

            return answer;
        } catch (InputMismatchException exception) {
            System.out.println("Please type a number that has decimals.");
            return askForDouble(question);
        }
    }
    // endregion public methods

    // region private methods
    private void askQuestion(String question, String tip) {
        if (tip.equals("")) {
            if (!question.endsWith("?")) {
                System.out.print(question + "?\n> ");
                return;
            }

            System.out.println(question);
            return;
        }

        System.out.println(question + " [" + tip + "]");
    }
    // endregion private methods
}
