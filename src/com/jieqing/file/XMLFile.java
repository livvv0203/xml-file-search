package com.jieqing.file;

import java.util.Scanner;

public class XMLFile {

    Scanner scanner = new Scanner(System.in);

    private String xmlFilePath;

    // Constructor
    public XMLFile() {};

    // Getter for XML file path
    public String getXMLFilePath() {
        xmlFilePath =
                scanner.nextLine();
                
        return xmlFilePath;
    }
}









