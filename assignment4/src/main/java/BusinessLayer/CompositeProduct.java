package BusinessLayer;


import java.util.HashSet;
import java.util.Set;

public class CompositeProduct extends MenuItem{


    private String name;  //nume
    private float price;  //pret
    private Set<MenuItem> menu;  //multimea elementelor din care este format


    public CompositeProduct(){

        menu = new HashSet<>();
    }

    @Override
    public float computePrice() {    //calculeaza pretul facand suma preturilor elementelor care compun produsul
        float totalPrice=0;
        for(MenuItem i : menu){
            totalPrice+=i.computePrice();
        }
        return totalPrice;
    }

    public void addMenuItem(MenuItem item){
        menu.add(item);
    }


    public Set<MenuItem> getMenu() {
        return menu;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name +" "+price;
    }
}
