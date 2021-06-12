package com.jieqing.naturalLanguageProcessor;

import java.util.ArrayList;
import java.util.Scanner;

public class NLP {

    private String keywordProcessed;
    private String yearProcessed;

    private String userInput;

    public String processKeyword() {

        System.out.println("Ask me anything: ");

        ArrayList<String> keywordPool = new ArrayList<>();
        keywordPool.add("de");
        keywordPool.add("journal");

        Scanner scanner = new Scanner(System.in);
        userInput = scanner.nextLine().toLowerCase();

        // UserInput contains any of the element in the arrayList
        for (int i = 0; i <= 1; i++) {
            if (userInput.contains(keywordPool.get(i))) {
                keywordProcessed = keywordPool.get(i);
            }
        }
        return keywordProcessed;
    }

    public String processYear() {

        ArrayList<String> yearPool = new ArrayList<>();
        yearPool.add("1975");
        yearPool.add("1976");

        // UserInput contains any of the element in yearPool
        for (int i = 0; i <= 1; i++) {
            if (userInput.contains(yearPool.get(i))) {
                yearProcessed = yearPool.get(i);
            }
        }
        return yearProcessed;
    }

    // Constructor
    public NLP() {
        processKeyword();
        processYear();
    }

    // Getters
    public String getKeywordProcessed() {
        return keywordProcessed;
    }

    public String getYearProcessed() {
        return yearProcessed;
    }
}

























