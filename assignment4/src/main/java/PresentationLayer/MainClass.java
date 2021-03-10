package PresentationLayer;


import DataLayer.RestaurantSerializator;

public class MainClass {

    public static void main(String[] args) {
        RestaurantSerializator ser = new RestaurantSerializator(args[0]);

        AdministratorGui gui = new AdministratorGui();
        WaiterGui gui2 = new WaiterGui();
    }
}
