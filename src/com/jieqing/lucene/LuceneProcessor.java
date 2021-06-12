package com.jieqing.lucene;

import com.jieqing.file.XMLFile;
import com.jieqing.naturalLanguageProcessor.NLP;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.FSDirectory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LuceneProcessor {

    double keywordCount = 0;

    // Using Lucene package
    ScoreDoc[] keywordSearch;
    QueryParser queryParser;
    FSDirectory index;

    String outputFileDestination =
            "/Users/olivianoliu/Desktop/Java/xml-file-search/src/com/jieqing/lucene/luceneIndex";

    /**
     * Open the stored index without parsing xml file again
     * @throws IOException
     */
    public void openIndex() throws IOException {
        index = FSDirectory.open(Paths.get(outputFileDestination));
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        queryParser = new QueryParser("title", standardAnalyzer);
    }

    /**
     * Parse file and generate a query parser
     * Store the query parser in memory for further usage
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void parseFile() throws IOException, ParserConfigurationException, SAXException {

        // Create and store the index file in the system
        index = FSDirectory.open(Paths.get(outputFileDestination));

        // Create Standard Analyzer
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();

        // Instantiate the index writer configuration
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);

        // Instantiate index writer
        IndexWriter indexWriter = new IndexWriter(index, indexWriterConfig);

        AddDocumentIterated(indexWriter);

        // Close the writer
        indexWriter.close();
        queryParser = new QueryParser("title", standardAnalyzer);
    }

    /**
     * Method for adding documents
     * @param indexWriter
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private static void AddDocumentIterated(IndexWriter indexWriter) throws
            ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        // XML File Path
        XMLFile xmlFile = new XMLFile();
        String xmlFilePath = xmlFile.getXMLFilePath();

        System.out.println("\nParsing xml file for indexing lucene, please wait...\n");

        // Access the document
        org.w3c.dom.Document document = builder.parse(new File(xmlFilePath));
        // Normalize the text content
        document.getDocumentElement().normalize();
        // Node list of elements of Tag "Journal"
        NodeList nodeList = document.getElementsByTagName("Journal");

        ArrayList<Document> documentList = new ArrayList<>();

        // Iterate the Node List
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element e = (Element) node;

                // Bridge between array-based and collection-based APIs

                // Title Column
                String t = e.getElementsByTagName("Title")
                        .item(0).getTextContent();
                // Date Column
                String y = e.getElementsByTagName("PubDate")
                        .item(0).getTextContent();

                // Lucene Document
                Document luceneDoc = new Document();

                // Iterate the content
                luceneDoc.add(new TextField("title", t, Field.Store.YES));
                luceneDoc.add(new TextField("year", y, Field.Store.YES));

                documentList.add(luceneDoc);
            }
        } // End of for loop
        indexWriter.addDocuments(documentList);
    } // End of AddDocumentIterated()

    /**
     * Method for searching keyword
     * @throws ParseException
     * @throws IOException
     */
    public void searchKeyword() throws ParseException, IOException {

        // Extract keyword and year from user input
        NLP nlp = new NLP();
        String queryKeyword = nlp.getKeywordProcessed();
        String queryYear = nlp.getYearProcessed();

        queryParser.setAllowLeadingWildcard(true);

        String queryString = "title: \"*" + queryKeyword + "*\" AND year: " + queryYear + "*";

        System.out.println(queryString);
        Query query = queryParser.parse(queryString);

        // Using provided index
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs firstDoc = searcher.search(query, Integer.MAX_VALUE);

        // Store number of the search
        keywordSearch = firstDoc.scoreDocs;

        TopScoreDocCollector collector = TopScoreDocCollector.create(1, Integer.MAX_VALUE);

        keywordCount = keywordSearch.length;

        System.out.println("Found " + keywordSearch.length + " hits.");

        // Iterate the array of "hits"
        for (int i = 0; i < keywordSearch.length; ++i) {
            int docId = keywordSearch[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("year") + "\t" + d.get("title"));
            searcher.search(query, collector);
        }

        reader.close();

    }
}

















