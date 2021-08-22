package main;

import model.Customer;
import perspective.CustomerMainPage;
import perspective.PageMain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * draw the GUI for main
 */
public class Main extends PageMain {

    public static String nowCustomerName = "";
    public static int WRONG_PASSWD_TIME = 0;

    public Main(String title) {
        super(title);
    }

    @Override
    public void run() {
        main(null);
    }

    public static void main(String[] args) {
        //main

        Frame f = new Main("Movie Theatre").frame;

        f.setLayout(new BorderLayout());

        //input
        Label label = new Label("input your name:");
        TextField tf = new TextField(20);
        Button b1 = new Button("confirm");

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //create a user
                try {
                    Customer.setNew(tf.getText());
                } catch (Exception q) {
                    q.printStackTrace();
                }
                f.setVisible(false);
                Main.nowCustomerName = tf.getText();
                new CustomerMainPage("customer page").run();

            }
        });

        Panel panel = new Panel();
        panel.setLayout(new FlowLayout());
        panel.add(label);
        panel.add(tf);
        panel.add(b1);

        f.add(panel, BorderLayout.CENTER);

        f.setLayout(new FlowLayout());

        f.setVisible(true);

    }

    public void exit() {
        System.exit(1);
    }


}