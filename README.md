# Triathlon Race-Day Nutrition Planner

## Nutrition - The Lost Fourth Discipline 

**Overview**

This application will assist in nutrition planning for various triathlons during a racing season. Based on race distance and anticipated race conditions, the application will create tailored nutrition strategies for triathletes, in accordance with their biometrics, over the course of a racing season. The strategies would be developed based on established nutritional guidelines to ensure that the essential macronutrients and calories are absorbed throughout the duration of the race.

**Users**

This application will mostly be used by triathletes to plan their fuelling/nutrition strategies prior to a racing season. However, it could also be used by endurance athletes practicing any of the three disciplines comprising triathlons – swimming, cycling, and running. 

**Personal Note**

This project is of interest to me as I failed to efficiently devise a race nutrition strategy for my first half-ironman distance triathlon and ended up suffering from intense cramps throughout the run leg. I believe there is immense need for this tool as there are no nutrition calculators that exist that specifically tailor one’s optimal macro intake to specific race conditions.

## User Stories

- As a user, I want to be able to develop an arbitrary number of race nutrition strategies during a typical racing season. As I improve in my triathlon journey, I expect to be able to complete more races every season.
- As a user, I want to be able to specify the quantity and type of nutritional/food items and/or supplements to be used in the development of my nutrition strategy.
- As a user, I want to be able to view a complete racing season summary of the nutrition strategies developed for each race. This list would show a list of the items to be consumed and their macronutrient content to help me understand and follow the strategies effectively.
- As a user, I want to be able to rate the effectiveness of the racing season nutrition strategies and record my feedback based on specific parameters.
- As a user, I want to have the option to save my complete racing season summary to file. Prior to quitting, I want to be presented with the option to save the current state of my application which includes my full racing season nutrition summary.
- As a user, I want to have the option to reload my complete racing season summary from file. Upon starting the application, I want to be presented with the option to load the state of my application that was previously saved.

## Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by typing "Yes" when prompted to view the season's nutrition strategies for all the races that you will be participating in. 
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by typing "Yes" when prompted to view the most frequent nutrition item that you will need to purchase for the racing season. This is contingent on the program being able to create race nutrition strategies with the athlete's preferred nutrition items and within the maximum allowable number of nutrition items for each race.
- You can locate my visual component upon completing the race queries and the program outputting the summary of race nutrition strategies.
- You can save the state of the application by typing "Save" when prompted prior to quitting.
- You can reload the state of the application by typing "Load" when prompted upon starting the program.

## Phase 4: Task 2
Event Logging Sample:

Sun Apr 07 23:17:19 PDT 2024
Anticipated caloric absorption rate determined based on anticipated weather conditions.

Sun Apr 07 23:17:19 PDT 2024
Average finish time obtained based on user biometrics and race details.

Sun Apr 07 23:17:19 PDT 2024
Average macronutrients to complete the race calculated.

Sun Apr 07 23:17:19 PDT 2024
Optimum nutrition plan to complete the race with the athlete's preferred nutrition items determined.

Sun Apr 07 23:17:19 PDT 2024
Race nutrition plan added to the season's strategies.

Sun Apr 07 23:17:23 PDT 2024
Anticipated caloric absorption rate determined based on anticipated weather conditions.

Sun Apr 07 23:17:23 PDT 2024
Average finish time obtained based on user biometrics and race details.

Sun Apr 07 23:17:23 PDT 2024
Average macronutrients to complete the race calculated.

Sun Apr 07 23:17:23 PDT 2024
Optimum nutrition plan to complete the race with the athlete's preferred nutrition items determined.

Sun Apr 07 23:17:23 PDT 2024
Race nutrition plan added to the season's strategies.

Sun Apr 07 23:17:30 PDT 2024
Most frequent nutrition item name and count determined.

Sun Apr 07 23:17:31 PDT 2024
Season strategies rated.


## Phase 4: Task 3
Looking at the design presented in my UML class diagram, I can identify a few areas for improvement.
Firstly, I currently have the UserInteraction interface within the SeasonStrategiesApp class to handle the GUI and 
Console user interactions. To enhance the single responsibility principle, I would extract that interface to avoid having nested classes.
Additionally, there is significant coupling between classes that I can work to reduce. For example, the SeasonStrategies class contains a list of RaceStrategy objects and a list of RaceNutrition objects, similarly, the RaceStrategy class contains a list of RaceNutrition objects. By refactoring, I can improve the association relationships between these classes and possibly eliminate the reference from SeasonStrategies to RaceNutrition. 
The same concept applies to the Triathlete class as both SeasonStrategies and RaceStrategy hold references to it but in an improved design only one of those references could be maintained. 
