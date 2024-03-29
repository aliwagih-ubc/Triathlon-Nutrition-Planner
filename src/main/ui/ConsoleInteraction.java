package ui;

import java.util.Scanner;

public class ConsoleInteraction implements SeasonStrategiesApp.UserInteraction {
    private Scanner sc = new Scanner(System.in);

    // EFFECTS: Queries the user for a string input. Allows the user to continue querying if an unexpected input or
    //    // wrong input type is received.
    @Override
    public String queryUserForStringWithRetry(String inputMessage, SeasonStrategiesApp.ValidateStringInput vs) {
        while (true) {
            output(inputMessage);
            try {
                String input = sc.nextLine();
                if (vs.validate(input)) {
                    return input;
                } else {
                    output("Received unexpected input, please try again");
                }
            } catch (Exception e) {
                output("Received wrong input type, please try again");
            }
        }
    }

    // EFFECTS: Queries the user for an int input. Allows the user to continue querying if an unexpected input or
    // wrong input type is received.
    @Override
    public int queryUserForIntWithRetry(String inputMessage, SeasonStrategiesApp.ValidateIntInput vi) {
        while (true) {
            output(inputMessage);
            try {
                String strInput = sc.nextLine();
                int input = Integer.parseInt(strInput);
                if (vi.validate(input)) {
                    return input;
                } else {
                    output("Received unexpected input, please try again");
                }
            } catch (Exception e) {
                output("Received wrong input type, please try again");
            }
        }
    }

    @Override
    public void output(String s) {
        System.out.println(s);
    }
}
