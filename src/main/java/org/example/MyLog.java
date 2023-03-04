package  org.example;
public class MyLog {

    //private  int id;
    private int productNum;
    private int amount;

    public MyLog( int productNum, int amount)
    {
        this.productNum = productNum;
        this.amount = amount;

    }
   // public int getId() {return id;}
    public int getProductNum() {return productNum;}
    public int getAmount() {return amount;}

  //  public void setId(int id) {this.id = id;}
    public void setProductNum(int productNum) {this.productNum = productNum;}
    public void setAmount(int amount) {this.amount = amount;}
}
