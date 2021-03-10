package BusinessLayer;
import java.util.ArrayList;

/**
 * interfata care defineste operatiile pentru administrator si waiter
 */
public interface IRestaurantProcessing {

    /**
     * creaza un nou base product
     * @param line numele noului base product
     * @param pret pretul noului base product
     * @return 0 daca produsul exista deja si 1 daca se creaza
     */
    public int createNewBaseProduct(String line, String pret);
    /**
     * creaza un nou composite product
     * @param line elementele din care va fi format noul produs
     * @return 0 daca un element care compune produsul nu exista 1 daca se creaza si 2 daca produsul exista deja
     */
    public int createNewCompositeProduct(String line);
    /**
     * sterge un anumit produs
     * @param item numele produsului care va fi sters
     */
    public void deleteMenuItem(String item);
    /**
     * editeaza un base product
     * @param item numele produsului
     * @param price noul pret
     */
    public void editMenuItem(String item, float price);
    /**
     * creaza o noua comanda
     * @param items elementele ce vor fi comandate
     * @param table numarul mesei de la care se face comanda
     */
    public void createOrder(ArrayList<MenuItem> items, int table);
    /**
     * calculeaza pretul total al unei comenzi
     * @param items elementele ce vor fi comandate
     * @return pretul total al comenzii
     */
    public float computePriceForOrder(ArrayList<MenuItem> items);
    /**
     * genereaza o factura
     * @param items elementele ce vor fi comandate
     * @param order comanda
     * @param price pretul total
     */
    public void generateBill(ArrayList<MenuItem> items, Order order, float price);


}
