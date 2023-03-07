package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ClientLog clientLog = new ClientLog();
        // Список возможных продуктов
        String[] products = {
                "Молоко", "Хлеб", "Сыр", "Яйца", "Гречка", "Мука", "Макароны", "Курица", "Картошка", "Яблоки"
        };
        //Список цен на продукты
        int[] prices = {60, 50, 150, 80, 90, 120, 100, 200, 45, 130};
        String result;
        // При старте программа должна искать этот файл в корне проекта
        File myFile = new File("basket.json");
        //Создаем пустую корзину
        Basket basket = new Basket(prices, products);
        // и если он находится,
        if (myFile.exists())
        // восстанавливать корзину из него;
        {   //Заменить на десериализацию
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

        }
        // если файл не найдет,
        else
        // то стоит начать с пустой корзины
        {
            try {
                if (myFile.createNewFile()) //Создаем файл basket.txt
                {
                    System.out.println("Файл был создан");

                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        int sumProducts = 0;
        Scanner scanner = new Scanner(System.in);

        //int[] productsCount = new int[10];

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
                    try (FileWriter fw = new FileWriter("basket.json")

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
                    //*************
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
        File logFile = new File("log.csv");
        clientLog.exportAsCSV(logFile);
    }
}
