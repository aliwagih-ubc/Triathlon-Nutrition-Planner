package ui;

import java.io.FileNotFoundException;

// creates an instance of the SeasonStrategiesApp class and runs it.
public class Main {
    public static void main(String[] args) {
        try {
            SeasonStrategiesApp seasonStrategiesApp = new SeasonStrategiesApp();
            seasonStrategiesApp.run();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Unable to run application.");
        }
    }
}
