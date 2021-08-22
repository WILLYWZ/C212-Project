package perspective;

import main.Main;
import model.Manager;
import model.Movie;
import model.Order;
import model.Ticket;
import util.Flags;
import util.JEnhancedOptionPane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static model.AbstractReadFile.SEPARATOR;
import static model.AbstractReadFile.SEPARATOR_2;
import static util.Flags.MANAGER_MAIN_TITLE;

public class CustomerMainPage extends PageMain {


    public CustomerMainPage(String title) {
        super(title);
    }

    @Override
    public void run() {
        Frame cmpFrame = this.frame;

        cmpFrame.setLayout(null);

        Label la = new Label("Click Button What You Want.", Label.CENTER);
        la.setPreferredSize(new Dimension(1400, 100));
        la.setBounds(0, 0, 1400, 100);
        cmpFrame.add(la);

        Button b1 = new Button("Purchase a ticket");
        b1.addActionListener(e -> {
            cmpFrame.setVisible(false);
            new PayP("buy a ticket").run();
        });
        b1.setBounds(0, 100, 280, 100);

        Button b2 = new Button("View movies");
        b2.addActionListener(e -> {
            listMovieByType();
        });
        b2.setBounds(280, 100, 280, 100);

        Button b3 = new Button("View tickets");
        b3.addActionListener(e -> {
            listAllOrder(Main.nowCustomerName);
        });
        b3.setBounds(560, 100, 280, 100);


        Button b4 = new Button("Enter Manager Mode");
        b4.addActionListener(e -> {

            Object[] ha = {"confirm", "cancel"};
            String inputValue = JEnhancedOptionPane.showInputDialog("Please input your passwd", ha);

            if (Manager.isPasswdRight(Main.nowCustomerName, inputValue)) {
                gotoManager();
            } else {
                Main.WRONG_PASSWD_TIME += 1;
                Object[] kk = {"ok"};
                JOptionPane.showOptionDialog(this.frame, "password had wrong " + Main.WRONG_PASSWD_TIME + " times!", "wrong", 1,
                        1, null, kk, null);
                if (Main.WRONG_PASSWD_TIME >= 3) {
                    System.exit(1);
                }

            }

        });
        b4.setBounds(840, 100, 280, 100);


        Button b5 = new Button("Close Application");
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        b5.setBounds(1120, 100, 280, 100);


        cmpFrame.add(b1);
        cmpFrame.add(b2);
        cmpFrame.add(b3);
        cmpFrame.add(b4);
        cmpFrame.add(b5);

        cmpFrame.setVisible(true);

    }


    private void gotoManager() {
        this.frame.setVisible(false);

        new ManagerMainPage(MANAGER_MAIN_TITLE).run();
    }

    public static void main(String[] args) {
        new CustomerMainPage("haha").run();
    }

    private void listMovieByType() {
        Object[] options = {"This Day", "Next Week"};
        int response = JOptionPane.showOptionDialog(this.frame, "select ticket type", "buy a ticket", JOptionPane.YES_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        //show this day
        Frame f = Flags.f;
        f.removeAll();
        f.setVisible(false);
        f.setTitle("Movie List");
        f.setLayout(new FlowLayout());

        //table
        String[][] datas = {};
        String[] titles = {"title", "price", "desc", "runtime", "showtime", "rate"};
        DefaultTableModel model = new DefaultTableModel(datas, titles);


        Button b = new Button("Return");
        b.addActionListener(e -> {
            f.removeAll();
            f.setVisible(false);
        });
        b.setPreferredSize(new Dimension(1000, 50));
        b.setBounds(0, 0, 100, 30);

        java.util.List<Movie> movies;
        java.util.List<Movie> movieAll = Movie.listAllMovie();
        if (response == 0) {
            movies = movieAll.stream().filter(per -> {
                java.util.List<String> allTiime = Movie.showtimeToList(per.getTimeOfShow());
                String toay = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                for (String s : allTiime) {
                    if (s.startsWith(toay)) {
                        return true;
                    }
                }
                return false;
            }).collect(Collectors.toList());

        } else {
            movies = movieAll.stream().filter(per -> {
                java.util.List<String> allTiime = Movie.showtimeToList(per.getTimeOfShow());
                String start = LocalDate.now().plusDays(8 - LocalDate.now().getDayOfWeek().getValue()).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String end = LocalDate.now().plusDays(14 - LocalDate.now().getDayOfWeek().getValue()).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                for (String s : allTiime) {
                    if (s.compareTo(start) >= 0 && s.compareTo(end) <= 0) {
                        return true;
                    }

                }
                return false;
            }).collect(Collectors.toList());
        }

        movies.forEach(per -> {
            String[] data = per.toString().split(SEPARATOR);
            model.addRow(data);
        });

        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        JTableHeader head = table.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 35));
        head.setFont(new Font("", Font.PLAIN, 16));

        JScrollPane comp = new JScrollPane(table);
        comp.setPreferredSize(new Dimension(1000, 400));
        comp.setBounds(0, 0, 1000, 400);
        f.add(comp);
        f.add(b);
        f.setVisible(true);
    }

    private void listAllOrder(String name) {
        Frame f = Flags.f;

        f.setTitle("Order List");

        f.setLayout(new FlowLayout());

        //table
        String[][] datas = {};
        String[] titles = {"movie_title", "type", "price"};
        DefaultTableModel model = new DefaultTableModel(datas, titles);


        Button b = new Button("Return");
        b.addActionListener(e -> {
            f.removeAll();
            f.setVisible(false);
        });
        b.setPreferredSize(new Dimension(1000, 50));
        b.setBounds(0, 0, 100, 30);

        java.util.List<Ticket> tickets = new ArrayList<>();
        Order.listAllOrder().stream().filter(per -> {
            return name.equals(per.getCname());
        }).forEach(per -> {
            tickets.addAll(per.getTickets());
        });


        tickets.forEach(per -> {
            String[] data = per.toString().split(SEPARATOR_2);
            model.addRow(data);
        });

        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        JTableHeader head = table.getTableHeader();
        head.setPreferredSize(new Dimension(head.getWidth(), 35));
        head.setFont(new Font("", Font.PLAIN, 16));

        JScrollPane comp = new JScrollPane(table);
        comp.setPreferredSize(new Dimension(1000, 400));
        comp.setBounds(0, 0, 1000, 400);

        f.add(comp);

        f.add(b);

        f.setVisible(true);
    }
}
