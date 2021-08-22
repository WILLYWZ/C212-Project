package util;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Flags {

    public static final String MOVIE_FILE = "movie.txt";
    public static final String CUSTOMER_FILE = "customer.txt";
    public static final String ORDER_FILE = "order.txt";
    public static final String MANAGER_FILE = "manager.txt";


    public static final String MAIN = "Movie Theatre";
    public static final String CUSTOMER_MAIN_TITLE = "Customer main.main";
    public static final String MANAGER_MAIN_TITLE = "Manager main.main";

    public static final String primetime_start = "20:00:00.000";
    public static final String primetime_end = "23:00:00.000";

    public static final Frame f = new Frame();

    static {
        f.setSize(1400, 800);
        f.setLocation(0, 0);
        f.setTitle(MAIN);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

}

