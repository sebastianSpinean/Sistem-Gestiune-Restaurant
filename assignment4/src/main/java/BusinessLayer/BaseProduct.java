package BusinessLayer;



public class BaseProduct extends MenuItem  {

    private String name;  //nume produs
    private float price;  //pret

    public BaseProduct(){

    }

    public BaseProduct(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public float computePrice() {   //returneaza pretul
        return price;
    }

    public String getName() {
        return name;
    }



    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String toString(){
        return name+" "+price;
    }
}
