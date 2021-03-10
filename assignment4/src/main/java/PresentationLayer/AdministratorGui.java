package PresentationLayer;

import BusinessLayer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import BusinessLayer.MenuItem;
import DataLayer.RestaurantSerializator;


public class AdministratorGui extends JFrame {
    private JPanel content = new JPanel();
    private JLabel title = new JLabel("Administrator Window");
    private JButton create = new JButton("Create");
    private JButton delete = new JButton("Delete");
    private JButton edit = new JButton("Edit");
    private JButton view = new JButton("View");

   private RestaurantSerializator ser =  new RestaurantSerializator();


    public AdministratorGui(){
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());
        panel4.setLayout(new FlowLayout());
        panel5.setLayout(new FlowLayout());

        panel1.add(title);
        panel2.add(create);
        panel3.add(delete);
        panel4.add(edit);
        panel5.add(view);

        content.add(panel1);
        content.add(panel2);
        content.add(panel3);
        content.add(panel4);
        content.add(panel5);
        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Administrator");
        this.setSize(300,300);
        create();
        delete();
        edit();
        view();

    }

    public void create(){
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdministratorCreateMenuItem adminCreate = new AdministratorCreateMenuItem();
            }
        });
    }

    public void delete(){
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdministratorDeleteMenuItem adminDelete = new AdministratorDeleteMenuItem();
            }
        });
    }

    public void edit(){
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdministratorEditMenuItem adminEdit =  new AdministratorEditMenuItem();
            }
        });
    }

    public void view(){
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IRestaurantProcessing restaurant = ser.deserializare();
                Set<MenuItem> menu = ((Restaurant)restaurant).getMenuItems();
                int j=0;
                String[] header = {"menu item", "price"};
                Object[][] cells = new Object[menu.size()][2]; //creaza celulele tabelului
                for(MenuItem i : menu){
                    if(i instanceof BaseProduct){
                        cells[j][0]=((BaseProduct) i).getName();
                        cells[j][1]=((BaseProduct) i).getPrice();
                        j++;
                    }
                    else if(i instanceof CompositeProduct){
                        cells[j][0]=((CompositeProduct) i).getName();
                        cells[j][1]=((CompositeProduct) i).getPrice();
                        j++;
                    }
                }

                AdministratorViewMenu adminView = new AdministratorViewMenu(cells,header);
            }
        });
    }

}
