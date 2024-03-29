package ui;

import javax.swing.*;

public class GuiInteraction implements SeasonStrategiesApp.UserInteraction {

    @Override
    public String queryUserForStringWithRetry(String inputMessage, SeasonStrategiesApp.ValidateStringInput vs) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, inputMessage);
            if (input == null) {
                System.exit(0);
            }
            if (vs.validate(input)) {
                return input;
            } else {
                output("Received unexpected input, please try again");
            }
        }
    }

    @Override
    public int queryUserForIntWithRetry(String inputMessage, SeasonStrategiesApp.ValidateIntInput vi) {
        while (true) {
            String strInput = JOptionPane.showInputDialog(null, inputMessage);
            if (strInput == null) {
                System.exit(0);
            }
            try {
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
        JOptionPane.showMessageDialog(null, s);
    }
}

