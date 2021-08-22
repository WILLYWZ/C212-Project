package perspective;

import main.Main;
import model.Movie;
import model.Order;
import model.Ticket;
import util.Flags;
import util.JEnhancedOptionPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static util.Flags.*;

/**
 * draw the GUI fro pay perspective
 */
public class PayP extends PageMain {


    public PayP(String title) {
        super(title);
    }

    @Override
    public void run() {

        Frame f = this.frame;
        Order order = new Order();
        order.setCname(Main.nowCustomerName);
        ArrayList<Ticket> tickets = new ArrayList<>();
        order.setTotalPrice(BigDecimal.ZERO);

        f.setLayout(new BorderLayout());

        TextArea ta = new TextArea();
        ta.setText("current tickets:");
        TextArea taTotal = new TextArea();
        taTotal.setText("total: 0");

        //input
        Label label = new Label("input movie name what you want buy", Label.CENTER);
        TextField tf = new TextField(50);

        Button b1 = new Button("add");
        b1.addActionListener(e -> {
            String name = tf.getText();
            if (name == null || name.equals("")) {
                Object[] kk = {"ok"};
                JOptionPane.showOptionDialog(this.frame, " movie name not empty", "wrong", 1,
                        1, null, kk, null);
                return;
            }
            Movie movie = new Movie();
            try {
                java.util.List<String> allMovie = movie.readFromFile(MOVIE_FILE).stream()
                        .map(movie::readFromString).map(Movie::getTitle).collect(Collectors.toList());
                if (!allMovie.contains(name)) {
                    Object[] kk = {"ok"};
                    JOptionPane.showOptionDialog(this.frame, " movie is not exist", "wrong", 1,
                            1, null, kk, null);
                    return;
                }

                Object[] options = {"Child", "Adult", "Senior"};
                int response = JOptionPane.showOptionDialog(f, "select ticket type", "buy a ticket", JOptionPane.YES_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                Movie movie1 = Movie.getByName(name);

                if (response == 0 && movie1.getLevel().equals(Movie.Rate.R)) {
                    Object[] kk = {"ok"};
                    JOptionPane.showOptionDialog(this.frame, "DON'T BUY R MOVIE TO CHILD", "wrong", 1,
                            1, null, kk, null);
                    return;
                }

                Ticket.Type type = getTypeByInputAndTime(response);
                Ticket ticket = new Ticket();
                ticket.setMovieName(name);
                ticket.setType(type);
                BigDecimal price = ticket.colPrice();
                ticket.setPrice(price);

                tickets.add(ticket);
                ta.setText(tickets.stream().map(Ticket::toString).collect(Collectors.joining("\n")));

                order.setTickets(tickets);
                order.colPrice();
                taTotal.setText("total:" + order.getTotalPrice());


            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });


        Button b = new Button("pay");

        b.addActionListener(e -> {
            Object[] options = {"cash", "credit"};
            int response = JOptionPane.showOptionDialog(f, "select pay way", "buy a ticket", JOptionPane.YES_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (response == 0) {
                saveOrder(order);
                showOrderAndGotoCustomer(order);
                this.frame.setVisible(false);
            }
            if (response == 1) {
                Object[] a = {"confirm"};
                String inputValue = JEnhancedOptionPane.showInputDialog("Please input a card number", a);
                saveOrder(order);
                showOrderAndGotoCustomer(order);
                this.frame.setVisible(false);
            }
        });

        Button returnB = new Button("return");
        returnB.addActionListener(e -> {
            this.frame.setVisible(false);
            new CustomerMainPage(MAIN).run();
        });

        Panel up = new Panel();
        up.setLayout(new FlowLayout());
        up.add(label);
        up.add(tf);
        up.add(b1);
        up.add(b);
        up.add(returnB);


        f.add(up, BorderLayout.NORTH);
        f.add(ta, BorderLayout.CENTER);
        f.add(taTotal, BorderLayout.SOUTH);


        f.setVisible(true);

    }

    private Ticket.Type getTypeByInputAndTime(int i) {
        String now = LocalTime.now().toString();
        switch (i) {
            case 0:
                if (now.compareTo(primetime_start) >= 0 && now.compareTo(primetime_end) <= 0) {
                    return Ticket.Type.CHILD_PRIMETIME;
                } else {
                    return Ticket.Type.CHILD_MATINEE;
                }
            case 1:
                if (now.compareTo(primetime_start) >= 0 && now.compareTo(primetime_end) <= 0) {
                    return Ticket.Type.ADULT_PRIMETIME;
                } else {
                    return Ticket.Type.ADULT_MATINEE;
                }
            case 2:
                if (now.compareTo(primetime_start) > 0 && now.compareTo(primetime_end) <= 0) {
                    return Ticket.Type.SENIOR_PRIMETIME;
                } else {
                    return Ticket.Type.SENIOR_MATINEE;
                }
        }
        return Ticket.Type.SENIOR_MATINEE;
    }

    private void saveOrder(Order order) {
        try {

            java.util.List<String> allOrder = Order.readFromFile(ORDER_FILE);
            order.setCname(Main.nowCustomerName);
            order.setTs(System.currentTimeMillis());
            allOrder.add(order.toString());
            Order.saveIntoFile(ORDER_FILE, allOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOrderAndGotoCustomer(Order order) {
        Frame f = Flags.f;
        f.setLayout(new FlowLayout());

        java.util.List<Ticket> tickets = order.getTickets();

        Label la = new Label("This Is Your Order:", Label.CENTER);
        la.setPreferredSize(new Dimension(1400, 100));
        la.setBounds(0, 0, 1400, 100);
        f.add(la);

        for (int i = 0; i < tickets.size(); i++) {
            Label a1 = new Label((i + 1) + ":" + tickets.get(i).toString(), Label.CENTER);
            a1.setPreferredSize(new Dimension(1400, 100));
            a1.setBounds(0, 100 * (i + 1), 1400, 100);
            f.add(a1);

        }

        Button button = new Button("done");
        button.setBounds(0, 0, 100, 500);
        button.addActionListener(e -> {
            f.removeAll();
            f.setVisible(false);
            gotoCustomer();
        });


        f.add(button);

        f.setVisible(true);
    }

    public static void main(String[] args) {
        new PayP("pay ").run();

    }

    private void gotoCustomer() {
        new CustomerMainPage(CUSTOMER_MAIN_TITLE).run();
    }


}
