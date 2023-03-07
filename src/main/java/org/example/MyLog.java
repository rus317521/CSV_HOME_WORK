package org.example;

public class MyLog {
    private int productNum;
    private int amount;

    public MyLog() {
    }

    public MyLog(int productNum, int amount) {
        this.productNum = productNum;
        this.amount = amount;
    }

    public int getProductNum() {
        return productNum;
    }

    public int getAmount() {
        return amount;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
