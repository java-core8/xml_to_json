package ru.tcreator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    final static String resourcePath = "src/main/resources/";
    static public void main(String[] args) throws Exception {
        String fileName = "data.xml";
        List<Employee> list = parseXML(resourcePath + fileName);
        writeStringToFile(parseToJson(list), "employees.json", resourcePath);
    }

    static public List<Employee> parseXML(String path) throws Exception {
        File xmlFile = new File(path);
        if(!xmlFile.exists()) throw new FileNotFoundException("файл не найден");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            Node root = doc.getDocumentElement();
            LinkedList<Employee> linkedList = new LinkedList();
            readNode(root, linkedList);
            return linkedList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    static public void readNode(Node node, List<Employee> allEmployes) {
        NodeList nodeList = node.getChildNodes();
        Map<String, String> mapEmloyee = new HashMap();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodeItem = nodeList.item(i);
            if(Node.ELEMENT_NODE == nodeItem.getNodeType()) {
                if(nodeItem.getNodeName().equals("employee")) {
                    readNode(nodeItem, allEmployes);
                } else {
                    mapEmloyee.put(nodeItem.getNodeName(), nodeItem.getTextContent());
                }
            }
        }
        if(!mapEmloyee.isEmpty()) {
            allEmployes.add(new Employee(
                    Long.parseLong(mapEmloyee.get("id")),
                    mapEmloyee.get("firstName"),
                    mapEmloyee.get("lastName"),
                    mapEmloyee.get("country"),
                    Integer.parseInt(mapEmloyee.get("age"))
            ));
        }

    }

    static public String parseToJson(List<Employee> obj) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(obj, listType);
    }

    static public void writeStringToFile(String textJson, String filename, String path) throws IOException {
        Path filePath = Paths.get(path, filename);
        File file = new File(path, filename);
        try {
            file.createNewFile();
            if (file.exists()) {
                Files.writeString(filePath, textJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
