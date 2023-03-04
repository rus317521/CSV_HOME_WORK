package org.example;

import java.io.*;

public class Basket {
    protected int[] prices; //Цены
    protected String[] productsInBasket; //Названия продуктов
    protected int[] amountProduct; //Количество продуктов

    //конструктор, принимающий массив цен и названий продуктов;
    public Basket(int[] prices, String[] productsInBasket) {
        this.prices = prices;
        this.productsInBasket = productsInBasket;
        amountProduct = new int[productsInBasket.length];

        for (int i = 0; i < productsInBasket.length; i++) {
            amountProduct[i] = 0;
        }

    }

    public Basket(int[] prices, String[] productsInBasket, int[] amountProduct) {
        this.prices = prices;
        this.productsInBasket = productsInBasket;
        this.amountProduct = amountProduct;

    }

    // addToCart(int productNum, int amount)
    // - метод добавления amount штук продукта номер productNum в корзину;
    public void addToCart(int productNum, int amount) {
        amountProduct[productNum - 1] += amount;
    }

    //printCart() - метод вывода на экран покупательской корзины.
    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int j = 0; j < productsInBasket.length; j++) {
            if (amountProduct[j] > 0) {
                System.out.println(productsInBasket[j] + " " + amountProduct[j] + " шт " + prices[j] + " руб/шт " + prices[j] * amountProduct[j] + " руб. в сумме");
            }

        }
    }

    //saveTxt(File textFile) - метод сохранения корзины в текстовый файл;
    // использовать встроенные сериализаторы нельзя;
    public void saveTxt(File textFile) {
        try (FileWriter writer = new FileWriter(textFile)
             //  FileReader inputStream = new FileReader(textFile)
        ) {
            int countProduct = 0;
            for (int i = 0; i < productsInBasket.length; i++) {
                if (amountProduct[i] > 0) countProduct++;
            }
            writer.write(countProduct + "\n");

            //Продукты
            for (int j = 0; j < productsInBasket.length; j++) {
                if (amountProduct[j] > 0) {
                    writer.write(productsInBasket[j] + " ");

                }
            }
            //Цены
            writer.write("\n");
            for (int j = 0; j < amountProduct.length; j++) {
                if (amountProduct[j] > 0) {
                    writer.write(prices[j] + " ");
                }
            }
            writer.write("\n");
            //Количество товара
            for (int j = 0; j < amountProduct.length; j++) {
                if (amountProduct[j] > 0) {
                    writer.write(amountProduct[j] + " ");
                }

            }
            writer.write("\n");
            //Стоимость товара
            for (int j = 0; j < amountProduct.length; j++) {
                if (amountProduct[j] > 0) {

                    writer.write(prices[j] * amountProduct[j] + " ");

                }

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    } //END saveTxt

    //static Basket loadFromTxtFile(File textFile) - статический(!)
    // метод восстановления объекта корзины из текстового файла,
    // в который ранее была она сохранена;
    public static Basket loadFromTxtFile(File textFile) {
        int sizeBasket = 0;
        String sizeBasketStr;
        String[] productsInBasketB = null;
        int[] amountB = null;
        Basket basket = null;
        int[] pricesB = null;

        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            //чтение построчно
            String productStr;
            String priseStr;
            String amountStr;
            String sumPriseStr;
            //Считываем размер корзины
            if ((sizeBasketStr = br.readLine()) != null) {
                sizeBasket = Integer.parseInt(sizeBasketStr);
                //Восстановить массив продуктов
            }

            int ap = sizeBasket;
            String[] pricesBstr = new String[ap]; //Цены String
            pricesB = new int[ap]; //Цены int
            String[] amountBstr = new String[ap]; //Количество  String
            amountB = new int[ap]; //Количество  int
            String[] pricesBsumStr = new String[ap]; //суммарные Цены String
            int[] pricesBsum = new int[ap]; //Суммарные Цены int
            productsInBasketB = new String[ap]; //Названия продуктов


            //Читаем строку с продуктами
            if ((productStr = br.readLine()) != null) {   //productStr = br.readLine();
                productsInBasketB = productStr.split(" ");
                //Восстановить массив продуктов
            }
            //Цены на продукты
            if ((priseStr = br.readLine()) != null) {
                pricesBstr = priseStr.split(" ");
                for (int l = 0; l < pricesBstr.length; l++) {
                    pricesB[l] = Integer.parseInt(pricesBstr[l]);
                }


            }
            //Количество продуктов
            if ((amountStr = br.readLine()) != null) {  // amountStr = br.readLine();
                amountBstr = amountStr.split(" ");
                for (int l = 0; l < amountBstr.length; l++) {
                    amountB[l] = Integer.parseInt(amountBstr[l]);
                }

            }

            basket = new Basket(pricesB, productsInBasketB, amountB);
            //  return basket;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return basket;
    }

    //геттеры, которые вы посчитаете нужными.
    public String[] getproductsInBasket() {
        return productsInBasket;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getAmountProduct() {
        return amountProduct;
    }
}


