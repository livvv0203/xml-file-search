package com.jieqing;

import com.jieqing.bruteForce.BruteForceProcessor;
import com.jieqing.lucene.LuceneProcessor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

    public void userInterface() throws IOException, SAXException, ParserConfigurationException, ParseException {
        System.out.println("Welcome to the Ultimate searching engine.");

        Scanner scanner = new Scanner(System.in);

        while(true) {

            System.out.println("\nPlease select a searching method:  \n");
            String nextAction = scanner.nextLine();

            switch (nextAction.toLowerCase()) {

                case "quit":
                    System.out.println("Thanks for using the system.");
                    return;

                case "bfs":
                    BruteForceProcessor b = new BruteForceProcessor();
                    b.parseFile();
                    b.searchKeyword();
                    break;

                case "lucene":
                    LuceneProcessor l = new LuceneProcessor();
                    // l.parseFile();
                    l.openIndex();
                    l.searchKeyword();
                    break;

                default:
                    System.out.println("Invalid input, please try again.");
            }
        }
    }
}
