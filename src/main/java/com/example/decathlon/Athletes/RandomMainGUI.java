package com.example.decathlon.Athletes;

import org.example.athletes.Athletes.RandomAthletes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class randomMainGUIAthletes {

    public static void generateParticipantsForEvent(String eventType, int maxParticipants) {
        //Börjar räkna varje deltagare från lista och har en for-loop som lägger in varje deltagare
        int beginCount = 0;
        Random rand = new Random();

        List<String> participantNames = new ArrayList<>();

        for (int i = 0; i < maxParticipants; i++) {
            String gender;
            if ("decathlon".equalsIgnoreCase(eventType)) {
                gender = "male";
            } else if ("heptathlon".equalsIgnoreCase(eventType)) {
                gender = "female";
            } else {

                gender = "male";
            }

            // Anropa RandomAthletes som genererar namn
            String name = RandomAthletes.generateNameForGender(gender);
            participantNames.add(name);
            beginCount++;
        }

        // For-loop för att eventuellt lägga in de olika namn för MainGUI
        for (String name : participantNames) {
            System.out.println("Generated participant: " + name);

        }
    }
    //Generera antal detagare
    public static void main(String[] args) {
        int maxParticipants = 40;
        generateParticipantsForEvent("decathlon", maxParticipants);
        generateParticipantsForEvent("heptathlon", maxParticipants);
    }
}
