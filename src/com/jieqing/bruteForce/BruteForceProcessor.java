package com.jieqing.bruteForce;

import com.jieqing.file.XMLFile;
import com.jieqing.naturalLanguageProcessor.NLP;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class BruteForceProcessor {

    NodeList nodeList;

    public void parseFile() throws ParserConfigurationException, IOException, SAXException {

        XMLFile xmlFile = new XMLFile();

        String xmlFilePath = xmlFile.getXMLFilePath();

        System.out.println("Parsing XML File by Brute Force search...\n");

        // Create a DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Access XML File
        Document document = builder.parse(new File(xmlFilePath));

        // Normalize the text nodes
        document.getDocumentElement().normalize();

        // NodeList contains all objects under Tag "Journal"
        nodeList = document.getElementsByTagName("Journal");

    }

    public void searchKeyword() {

        NLP nlp = new NLP();

        String keyword = nlp.getKeywordProcessed();
        String year = nlp.getYearProcessed();

        if (keyword != null) {
            System.out.println("Searching keyword: " + keyword + " with year: " + year + "\n");
        }
        else if (keyword == null) {
            System.out.println("None of the article title contains this keyword in database.\n");
        }

        // Iterate the Node List
        for (int i = 0; i < nodeList.getLength(); i++) {

            // Get item in the collection
            Node node = nodeList.item(i);
            // Node is an Element node
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                // Get text content from Tag "Title"
                String strTitle = element.getElementsByTagName("Title").item(0).getTextContent();

                // Get text content from Tag "Year"
                String strYear = element.getElementsByTagName("Year").item(0).getTextContent();

                // Print out Title with keyword and year, year could be null
                if (keyword == null && year == null) {
                    continue;
                }

                else if (keyword != null && (strTitle.indexOf(keyword) >= 0) && year == null) {
                    System.out.println("Title: " + strTitle);
                }

                else if (((keyword != null && (strTitle.indexOf(keyword) >= 0)))
                        && ((strYear == null || (strYear != null && strYear.indexOf(year) >= 0)))) {
                    System.out.println("Title: " + strTitle + " Year: " + strYear);
                }
            }
        }
    } // End of for loop
}








