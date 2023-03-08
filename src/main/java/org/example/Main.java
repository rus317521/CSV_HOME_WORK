package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        //Блок load
        Boolean loadEnabled = true; //Нужно ли загружать данные корзины при старте программы из файла
        String fileNameForLoad = ""; //Имя файла из которого необходимо загружать данные корзины
        String fileForLoadFormat = "";//формат файла из которого необходимо загружать данные(json или text)
        //Блок save
        Boolean saveEnabled = true; //нужно ли сохранять данные корзины после каждого ввода
        String fileNameForSave = ""; //Имя файла в который необх сохранять данные корзины после каждого ввода
        String fileForSaveFormat = "";//формат файла в который необх сохранять данные корзины (json или text)
        //Блок log
        Boolean logEnabled = true; //нужно ли сохранять лог при завершении программы
        String fileNameForLog = ""; //Имя файла (.csv) в который необх сохранять лог при завершении программы

        ClientLog clientLog = new ClientLog();
        // Список возможных продуктов
        String[] products = {
                "Молоко", "Хлеб", "Сыр", "Яйца", "Гречка", "Мука", "Макароны", "Курица", "Картошка", "Яблоки"
        };
        //Список цен на продукты
        int[] prices = {60, 50, 150, 80, 90, 120, 100, 200, 45, 130};
        String result;

        //***  Cчитываем настройки из файла shop.xml   ***

        //Получим объект Document для XML-файла чтобы считать наш XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        //Получим все корневые элементы объекта doc
        NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node_ = nodeList.item(i);
            if (Node.ELEMENT_NODE == node_.getNodeType()) {
                for (int j = 0; j < node_.getChildNodes().getLength(); j++) {

                    if (node_.getChildNodes().item(j).getNodeName().equals("enabled")) {
                        Node nodeEnabled = node_.getChildNodes().item(j);
                        String strEnabled = nodeEnabled.getTextContent();
                        if (node_.getNodeName().equals("load")) {
                            loadEnabled = !strEnabled.equals("false");
                        }
                        if (node_.getNodeName().equals("save")) {
                            saveEnabled = !strEnabled.equals("false");
                        }
                        if (node_.getNodeName().equals("log")) {
                            logEnabled = !strEnabled.equals("false");
                        }
                    }
                    if (node_.getChildNodes().item(j).getNodeName().equals("fileName")) {

                        Node nodeFileName = node_.getChildNodes().item(j);
                        String strFileName = nodeFileName.getTextContent();
                        if (node_.getNodeName().equals("load"))
                            fileNameForLoad = strFileName;
                        else if (node_.getNodeName().equals("save"))
                            fileNameForSave = strFileName;
                        else fileNameForLog = strFileName;
                    }
                    if (node_.getChildNodes().item(j).getNodeName().equals("format")) {

                        Node nodeFileFormat = node_.getChildNodes().item(j);
                        String strFileFormat = nodeFileFormat.getTextContent();
                        if (node_.getNodeName().equals("load"))
                            fileForLoadFormat = strFileFormat;
                        else
                            fileForSaveFormat = strFileFormat;
                    }
                }

            }

        }
        //System.out.println("loadEnabled  " + loadEnabled);
        //System.out.println("fileNameForLoad  " + fileNameForLoad);
        //System.out.println("fileForLoadFormat  " + fileForLoadFormat);

        //System.out.println("saveEnabled  " + saveEnabled);
        //System.out.println("fileNameForSave  " + fileNameForSave);
        //System.out.println("fileForSaveFormat  " + fileForSaveFormat);

        //System.out.println("logEnabled  " + logEnabled);
        //System.out.println("fileNameForLog  " + fileNameForLog);

        //***  Закончили считывать настройки из файла shop.xml  ***

        File fileForSave = new File(fileNameForSave);

        File myFile = new File(fileNameForLoad);
        //Создаем пустую корзину
        Basket basket = new Basket(prices, products);
        // если он находится и данные необходимо загружать
        if (myFile.exists() & loadEnabled)
        // восстанавливать корзину из него;
        {   //Заменить на десериализацию
            if (fileForLoadFormat.equals("json")) {
                // откроем входной поток для чтения файла
                try (BufferedReader br = new BufferedReader(new FileReader(myFile))
                ) {
                    String s = br.readLine();
                    StringReader reader = new StringReader(s);
                    ObjectMapper mapper = new ObjectMapper();
                    basket = mapper.readValue(reader, Basket.class);

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            } else {
                Basket basketLoad = basket.loadFromTxtFile(myFile);
                basket = basketLoad;
            }
        }
        // если файл не найдет либо загрузка не требуетс
        else
        // то стоит начать с пустой корзины
        {
            try {

                if (saveEnabled)
                    fileForSave.createNewFile(); //Создаем файл basket.*

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        int sumProducts = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + "  " + products[i] + "  " + prices[i] + " руб/шт");
        }
        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                if (sumProducts > 0) {
                    basket.printCart();
                    //************* Сериализуем
                    //в json
                    if (saveEnabled & fileForSaveFormat.equals("json")) {
                        try (FileWriter fw = new FileWriter(fileNameForSave)

                        ) {
                            // запишем экземпляр класса в файл
                            //Писать результат сериализации будем во Writer(StringWriter)
                            StringWriter writer = new StringWriter();
                            //Это объект Jackson, который выполняет сериализацию
                            ObjectMapper mapper = new ObjectMapper();
                            //Сама сериализация
                            mapper.writeValue(writer, basket);
                            //Преобразовываем все записанное во StringWriter в строку
                            result = writer.toString();
                            System.out.println(result);
                            //Записываем строку в json - формате в файл basket.json
                            fw.write(result);
                            fw.flush();

                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    //Закончили Сериализацию в json *************
                    //Начнем Сериализацию в text если это необходимо *************
                    else if (saveEnabled & fileForSaveFormat.equals("text")) {
                        basket.saveTxt(fileForSave);
                    }
                }
                break;
            }

            String[] parts = input.split(" ");
            int number = Integer.parseInt(parts[0]) - 1;
            int count = Integer.parseInt(parts[1]);
            basket.addToCart(Integer.parseInt(parts[0]), count);
            clientLog.log(number + 1, count);

            sumProducts = sumProducts + prices[number] * count;
        }
        if (logEnabled) {
            // File logFile = new File("log.csv");
            File logFile = new File(fileNameForLog);
            clientLog.exportAsCSV(logFile);
        }
    }
}
