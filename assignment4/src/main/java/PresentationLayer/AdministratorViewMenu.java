package PresentationLayer;



import javax.swing.*;


public class AdministratorViewMenu extends JFrame {

    private JTable tabel;
    private JScrollPane scroll ;


    public AdministratorViewMenu(Object[][] cells, String[] headers){
        tabel = new JTable(cells,headers);


        scroll = new JScrollPane(tabel);

        this.add(scroll);
        this.pack();
        this.setVisible(true);
        this.setTitle("View Menu Items");


    }



}
