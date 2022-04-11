package com.softwaredevii.todolistattempt;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

//figure out how to calculate time between events
public class taskdurationparser {


    private static final String FILENAME = "app.log";

    public static void main(String[] args) {

        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        long startTime = 0;
        long endTime = 0;
        int i = 1;
        boolean viewAllSelected = false;

        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root Element : " + doc.getDocumentElement().getNodeName());
            System.out.println("------------------");

            // get <staff>
            NodeList list = doc.getElementsByTagName("Event");

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    if (element.getAttribute("loggerName").equals("com.softwaredevii.todolistattempt.HelloServlet")) {

                            // get staff's attribute
                            String id = element.getAttribute("loggerName");
                            String time = element.getAttribute("timeMillis");

                            // get text
                            String message = element.getElementsByTagName("Message").item(0).getTextContent();

                            if (message.equals("User Selection was made: 1") && !viewAllSelected) {
                                System.out.println("\nViewAll Selection :" + node.getNodeName() + " " + i);
                                System.out.println("User selection was made at : " + time + " millis");

                                startTime = Long.parseLong(time);
                                viewAllSelected = true;
                            }

                            if (message.equals("SQLSingleton viewAll entityManagerFactory.close()") && viewAllSelected) {
                                endTime = Long.parseLong(time);
                                System.out.println("Task completed at: " + time + " millis");
                                System.out.println("Duration :" + (endTime - startTime) + " millis");
                                i++;
                                viewAllSelected = false;
                            }

//                        System.out.println("Current Element :" + node.getNodeName());
//                        System.out.println("Logger Name : " + id);
//                        System.out.println("Message : " + message.trim());

                        }
                    }

            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }

}
