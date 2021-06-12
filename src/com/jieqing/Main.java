package com.jieqing;

import org.apache.lucene.queryparser.classic.ParseException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ParseException {

        UserInterface ui = new UserInterface();
        ui.userInterface();

    }
}
