package com.example.decathlon.Athletes;

import java.util.Random;

public class RandomAthletes {
    //Generera kill namn
    private static final String[] maleNames = {"Adam", "Amir", "Aiden", "Ahmed",
            "Dario", "Stefan", "Lucas", "Ivan","Bert", "Diego", "John", "Peter",
            "Michael", "David", "James", "Kai", "Omar", "Tomas", "Kofi", "Raj",
            "Yugi", "Nia", "Satoshi", "Rafael", "Mateo", "Tenzin", "Samuel",
            "Carlos", "Liam", "Hassan", "Nguyen", "Oleg", "Jamal", "Miguel",
            "Yamaha", "Andrei", "Vlademir", "Ali"};
    //Generera Tjej namn
    private static final String[] femaleNames = {"Anna", "Maria", "Sara", "Emma",
            "Klara", "Mei", "Yuki", "Sofia", "Lina", "Elena", "Amara", "Isabella",
            "Aisha", "Karla", "Priya", "Eva", "Leia", "Maya", "Mira", "Liana", "Keira",
            "Nor", "Amina", "Chloe", "Videl", "Nina", "Yara", "Zara", "Lila", "Anika",
            "Sana", "Mina", "Hana", "Alyssa", "Carmen", "Rina", "Soraya", "Dina"};

    private static final Random rand = new Random();
    //Kan anropas för att generera de olika namn baserad på kön
    public static String generateRandomName(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            return maleNames[rand.nextInt(maleNames.length)];
        } else {
            return femaleNames[rand.nextInt(femaleNames.length)];
        }
    }
    //Metod för eventuellt MainGUI (Om möjligt)
    public static String generateNameForGender(String gender) {
        return generateRandomName(gender);
    }
}


