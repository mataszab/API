package com.example.api;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Documented;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BmwModelsXmlParser {
    public static String getBmwModels(InputStream stream) throws IOException {
        String result = new String();
        try {
            DocumentBuilderFactory xmlDocFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlDocBuilder = xmlDocFactory.newDocumentBuilder();
            Document doc = xmlDocBuilder.parse(stream);
            NodeList modelNodes = doc.getElementsByTagName("ModelsForMake");
            for (int i = 0; i < modelNodes.getLength(); ++i) {
                Element modelNode = (Element) modelNodes.item(i);
                String makeName = modelNode.getElementsByTagName("Make_Name").item(0).getFirstChild().getNodeValue();
                String modelName = modelNode.getElementsByTagName("Model_Name").item(0).getFirstChild().getNodeValue();
                result += (String.format("Make: %s,  model: %s \n", makeName, modelName));
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return result;
    }
}
