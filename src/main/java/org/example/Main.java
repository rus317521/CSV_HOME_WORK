import org.example.Basket;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Список возможных продуктов
        String[] products = {
                "Молоко", "Хлеб", "Сыр", "Яйца", "Гречка", "Мука", "Макароны", "Курица", "Картошка", "Яблоки"
        };
        //Список цен на продукты
        int[] prices = {60, 50, 150, 80, 90, 120, 100, 200, 45, 130};

        // При старте программа должна искать этот файл в корне проекта
        File myFile = new File("basket.txt");
        //Создаем пустую корзину
        Basket basket = new Basket(prices, products);
        // и если он находится,
        if (myFile.exists())
        // восстанавливать корзину из него;
        {
            Basket basketN = basket.loadFromTxtFile(myFile);
            basketN.printCart();
            //++++++++
            for (int i = 0; i < basket.getproductsInBasket().length; i++) {
                for (int j = 0; j < basketN.productsInBasket.length; j++) {
                    if (basket.getproductsInBasket()[i].equals(basketN.getproductsInBasket()[j])) {
                        basket.addToCart(i + 1, basketN.getAmountProduct()[j]);
                    }
                }
            }
            //++++++++++
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

        int[] productsCount = new int[10];

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + "  " + products[i] + "  " + prices[i] + " руб/шт");
        }
        ;
        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                if (sumProducts > 0) {
                    basket.printCart();
                }
                break;
            }

            String[] parts = input.split(" ");
            int number = Integer.parseInt(parts[0]) - 1;
            int count = Integer.parseInt(parts[1]);
            basket.addToCart(Integer.parseInt(parts[0]), count);
            //После ввода каждой покупки пользователем вам следует
            // сохранять пользовательскую корзину в файл basket.txt
            basket.saveTxt(myFile);
            sumProducts = sumProducts + prices[number] * count;
        }
    }
}
