package BusinessLayer;

import DataLayer.FileWriterBill;
import DataLayer.RestaurantSerializator;


import java.util.*;

import java.io.*;

public class Restaurant extends Observable implements IRestaurantProcessing, Serializable  {

    /**
     * comenzile
     */
    private Map<Order, ArrayList<MenuItem>> orderItems = new HashMap<Order, ArrayList<MenuItem>>(); //comenzile
    /**
     * meniul
     */
     Set<MenuItem> menuItems = new HashSet<MenuItem>(); //meniul
    /**
     * numarul comenzii
     */
    int idOrder; //numarul comenzii

    /**
     * constructor
     */
    public Restaurant(){
    }


    /**
     * reprezinta invariantul clasei
     * @return ture daca toate elementele din meniu sunt diferite de null si false in caz contrar
     */
    public boolean containsNull(){
        for(MenuItem i : menuItems){ //verifica ca fiecare element sa nu fie null
            if(i == null){
                return false;
            }
        }
        return true;
    }

    /**
     * creaza un nou base product
     * @param produs numele noului base product
     * @param pret pretul noului base product
     * @pre numele produsului si pretul sa fie diferite de null
     * @post se verifica invariantul
     * @return 0 daca produsul exista deja si 1 daca se creaza
     */
    @Override
    public int createNewBaseProduct(String produs, String pret) {
        assert produs != null;
        assert pret != null;
        produs=eliminareSpatii(produs); //elimina spatiile din nume
        for(MenuItem i : menuItems){   //parcurge meniul
            if(i instanceof BaseProduct){
                if(((BaseProduct) i).getName().equals(produs)){  //returneaza 0 daca produsul exista deja
                    return 0;
                }
            }
        }
        MenuItem base = new BaseProduct(produs,Float.parseFloat(pret)); //face un nou produs
        menuItems.add(base);
        RestaurantSerializator.serializare(this); //serializeaza
        assert containsNull();
        return 1;

    }

    /**
     * creaza un nou composite product
     * @param line elementele din care va fi format noul produs
     * @pre se verifica ca numele sa fie diferit de null
     * @post se verifica invariantul
     * @return 0 daca un element care compune produsul nu exista 1 daca se creaza si 2 daca produsul exista deja
     */
    @Override
    public int createNewCompositeProduct(String line) {
        assert line != null;
        MenuItem composite = new CompositeProduct(); //instantiaza un nou produs
        String[] lines = line.split("\n");   //desparte dupa "\n"
        if(lines.length==1){   //o singura linie
            return createSimpleCompositeProduct(lines[0]);
        }
        else {
            for (String i : lines) {     //mai multe linii
                i = eliminareSpatii(i);
                String[] words = i.split(" ");  //desparte dupa spatiu
                if (words.length == 1) {  //doar un cuvant
                    BaseProduct base = findBaseProduct(words[0]); //cauta sa vada daca exista acel base product
                    if (base == null) {
                        return 0;
                    }
                    ((CompositeProduct) composite).addMenuItem(base); //il adauga in lista lui composite

                } else { //mai multe cuvinte
                    CompositeProduct localComposite = findCompositeProduct(words); //cauta acel composite
                    if(localComposite == null){
                        return 0;
                    }
                    ((CompositeProduct) composite).addMenuItem(localComposite); //il adauga in lista lui composite
                }
            }
        }
        lines[0]=eliminareSpatii(lines[0]);
        String nume=lines[0];
        for(int k=1; k<lines.length; k++){
            lines[k]=eliminareSpatii(lines[k]);
            nume=nume+", "+lines[k];               //formeaza numele produsului
        }
        ((CompositeProduct) composite).setName(nume);
        ((CompositeProduct) composite).setPrice(((CompositeProduct) composite).computePrice()*9/10); //pune pretul
       if(findSameComposite((CompositeProduct)composite)){
           return 2;  //daca exista deja nu il adauga
       }
        menuItems.add(composite);
        RestaurantSerializator.serializare(this); //serializeaza
       assert containsNull();
       return 1;
    }

    /**
     * creaza un composite format doar din base prodacturi
     * @param line elementele din care va fi format produsul
     * @pre se verifica ca numele sa fie diferit de null
     * @post se verifica invariantul
     * @return 0 daca un element care compune produsul nu exista 1 daca se creaza si 2 daca produsul exista deja
     */
    public int createSimpleCompositeProduct(String line){
        assert line != null;
        MenuItem localComposite = new CompositeProduct(); //creaza un nou composite
        line=eliminareSpatii(line);
        String[] word = line.split(" "); //desparte in cuvinte
        for (String j : word) {  //pentru fiecare cuvant
            BaseProduct localBase = findBaseProduct(j); //verifica daca exista acel base
            if (localBase == null) {
                return 0;
            }
            ((CompositeProduct) localComposite).addMenuItem(localBase); //il adauga in lista lui localComposite
        }
        ((CompositeProduct) localComposite).setName(line);
        ((CompositeProduct) localComposite).setPrice(((CompositeProduct) localComposite).computePrice() * 9 / 10); //pune pret
        if(findCompositeProduct(line.split(" "))!=null){
            return 2; //daca exista deja nu il mai adauga
        }
        menuItems.add(localComposite);
        RestaurantSerializator.serializare(this); //serializeaza
        assert containsNull();
        return 1;
    }

    /**
     * sterge un anumit produs
     * @pre se verifica ca numele sa fie diferit de null
     * @post se verifica invariantul
     * @param item numele produsului care va fi sters
     */
    @Override
    public void deleteMenuItem(String item) {

        assert item != null;
        String[] word1 = item.split(","); //desparte dupa virgula
        if(word1.length == 1){ //nu avem virgula
            word1[0]=eliminareSpatii(word1[0]);
            String[] word2 = word1[0].split(" "); //desparte in cuvinte
            if(word2.length == 1){//un singur cuvant
                word2[0]=eliminareSpatii(word2[0]);
                BaseProduct base = findBaseProduct(word2[0]); //cauta acel base
                menuItems.remove(base); //il sterge
                Set<MenuItem> delete = new HashSet<MenuItem>();
                for(MenuItem i : menuItems){ //sterge toate produsele care contin acel base
                    if((i instanceof BaseProduct && ((BaseProduct) i).getName().contains(word2[0])) || (i instanceof CompositeProduct && ((CompositeProduct) i).getName().contains(word2[0]))){
                        delete.add(i);
                    }
                }
                menuItems.removeAll(delete); //le sterge
            }
            else{ //avem mai multe cuvinte
                CompositeProduct comp = findCompositeProduct(word2); //cauta acel composite
                menuItems.remove(comp); //este sters
                Set<MenuItem> delete = new HashSet<MenuItem>();
                for(MenuItem i : menuItems){ //sterge toate elementele care contin acel composite
                    if( i instanceof CompositeProduct && ((CompositeProduct) i).getMenu().contains((CompositeProduct) comp)){
                        delete.add(i);
                    }
                }
                menuItems.removeAll(delete);
            }
        }
        else{
            deleteCompositeProduct(word1); //avem un composite care contine si alt composite
        }
        RestaurantSerializator.serializare(this); //serializeaza
        assert containsNull();
    }

    /**
     * sterge un composite product format din cel putin doua elemente
     * @param word1 numele elementelor
     * @post se verifica invariantul
     */
    public void deleteCompositeProduct(String[] word1){
        Set<MenuItem> items = new HashSet<MenuItem>();
        for(String i : word1){ //pentru fiecare grup de cuvinte
            i=eliminareSpatii(i);
            if(i.split(" ").length == 1){ //daca avem doar un cuvant
                String[] base = i.split(" ");
                items.add(findBaseProduct(base[0])); //se adauga un base
            }
            else items.add(findCompositeProduct(i.split(" "))); //se adauga un composite
        }
        CompositeProduct compositeProduct = new CompositeProduct();
        for(MenuItem i : menuItems){ // se verifica daca exista un astfel de composite
            if(i instanceof CompositeProduct && ((CompositeProduct) i).getMenu().containsAll(items)){
                compositeProduct=(CompositeProduct) i;
            }
        }
        menuItems.remove((CompositeProduct)compositeProduct); // daca da se sterge
        assert containsNull();
    }

    /**
     * editeaza un base product
     * @param item numele produsului
     * @param price noul pret
     * @pre se verifica ca numele sa fie diferit de null si pretul sa fie mai mare ca 0
     * @post se verifica invariantul
     */
    @Override
    public void editMenuItem(String item, float price) {
        assert item != null;
        assert price >= 0;
        item=eliminareSpatii(item);
        MenuItem base = findBaseProduct(item); // se gaseste base-ul care se editeaza
        if(base == null){
            return;
        }
        ((BaseProduct)base).setPrice(price); //se modifica pretul
        for(MenuItem i : menuItems){ //se modifica pretul tuturor elementelor care contin acel base
            if(i instanceof CompositeProduct && ((CompositeProduct) i).getName().contains(item)){
                ((CompositeProduct) i).setPrice(i.computePrice()*9/10);
            }
        }
        RestaurantSerializator.serializare(this);
        assert containsNull();

    }

    /**
     * creaza o noua comanda
     * @param items elementele ce vor fi comandate
     * @param table numarul mesei de la care se face comanda
     */
    @Override
    public void createOrder(ArrayList<MenuItem> items, int table) {
        assert table >= 0;
        assert !items.isEmpty();
        idOrder++; //creste numarul comenzii
        Order order = new Order(idOrder, new Date(), table); // se creaza comanda
        orderItems.put(order,items);
        RestaurantSerializator.serializare(this); //se serializeaza
        generateBill(items,order,computePriceForOrder(items)); //se genereaza factura
        setChanged();  // se trimite notificare la chef
        notifyObservers(order);
    }

    /**
     * calculeaza pretul total al unei comenzi
     * @param items elementele ce vor fi comandate
     * @return pretul total al comenzii
     */
    @Override
    public float computePriceForOrder(ArrayList<MenuItem> items) {
        assert !items.isEmpty();
        float price=0;
        for(MenuItem i : items){  //calculeaza pretul total al comenzii
            if(i instanceof BaseProduct){
                price+=((BaseProduct) i).getPrice();
            }
            else if(i instanceof CompositeProduct){
                price+=((CompositeProduct) i).getPrice();
            }
        }
        assert price>=0;
        return price;
    }

    /**
     * genereaza o factura
     * @param items elementele ce vor fi comandate
     * @param order comanda
     * @param price pretul total
     */
    @Override
    public void generateBill(ArrayList<MenuItem> items, Order order, float price) {
        assert !items.isEmpty();
        assert order!=null;
        assert price>=0; //genereaza o factura
        FileWriterBill writer = new FileWriterBill();
        writer.generateBill(items,order,price);
    }

    /**
     * obtine comenzile
     * @return comenzile
     */
    public Map<Order, ArrayList<MenuItem>> getOrderItems() {
        return orderItems;
    }

    /**
     * obtine meniul
     * @return meniul
     */
    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * gaseste un anumit base product
     * @param name numele produsului
     * @return produsul cu acel nume
     */
    public BaseProduct findBaseProduct(String name){
        assert name!= null;
        for(MenuItem i : menuItems){
            if(i instanceof BaseProduct){ //cauta un base product cu un anumit nume
                if(((BaseProduct) i).getName().equals(name)){
                    return (BaseProduct) i;
                }
            }
        }
        return null;
    }

    /**
     * gaseste un composite product
     * @param words elementele din care este format
     * @return prdusul
     */
    public CompositeProduct findCompositeProduct(String[] words){
        assert words.length>=1;
        int nr=0;
        String name = words[0];
        for(int k=1; k<words.length; k++){   //se construieste numele composite-ului cautat
            name+=" "+words[k];
        }
        for(MenuItem i : menuItems){ //se parcurge meniul
            if(i instanceof CompositeProduct){
                nr=0;
                for(String j : words){ //pentru fiecare composite verificam cate elemente cautate contine
                    if(((CompositeProduct) i).getName().contains(j)){
                        nr++;
                    }
                    if(nr==words.length && ((CompositeProduct) i).getName().length() == name.length())  { //daca este format doar din elementele cautate
                        return (CompositeProduct) i; //il returnam
                     }
                }
            }
        }
        return null;
    }

    /**
     * gaseste un composite product acre este format din aceleasi elemente cu cel primit ca argument
     * @param comp produsul pe care il cautam
     * @return true daca mai exista sau false daca nu
     */
    public boolean findSameComposite(CompositeProduct comp){ //cauta un composite dupa multimea elementelor din care este format
        assert comp!=null;
        for(MenuItem i : menuItems){
            if(i instanceof CompositeProduct && !((CompositeProduct) i).getName().equals(comp.getName())){
                if(((CompositeProduct) i).getMenu().containsAll(comp.getMenu())){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * elimina spatiile
     * @param line stringul din care se elimina spatiile
     * @return stringul dupa ce au fost eliminate spatiile
     */
    public String eliminareSpatii(String line){
        assert line!=null;
        line=line.replaceAll("[ ]+"," "); //inlocuieste spatiile multiple cu un spatiu
        if(line.startsWith(" ")){
            line=line.substring(1); //se elimina spatiul de la inceputul cuvantului
        }
        if(line.endsWith(" ")){
            line=line.substring(0,line.length()-1);//se elimina spatiul de la sfarsitul cuvantului
        }
        return line;
    }

}
