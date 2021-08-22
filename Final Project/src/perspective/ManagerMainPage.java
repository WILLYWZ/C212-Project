package perspective;


import main.Main;
import model.Movie;
import util.Flags;
import util.JEnhancedOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import static util.Flags.CUSTOMER_MAIN_TITLE;
import static util.Flags.MANAGER_MAIN_TITLE;

/**
 * draw the GUI for manager perspective
 */
public class ManagerMainPage extends PageMain {


    public ManagerMainPage(String title) {
        super(title);
    }

    private void addMovie() {
        //input and save the movie2
        this.frame.setVisible(false);
        Frame f = Flags.f;
        f.setLayout(null);


        Label la = new Label("Please Input Movie Info", Label.CENTER);
        la.setPreferredSize(new Dimension(1400, 100));
        la.setBounds(0, 0, 1400, 100);

        Label namel = new Label("name", Label.RIGHT);
        namel.setBounds(250, 100, 100, 50);
        TextField nametf = new TextField(50);
        nametf.setBounds(350, 100, 700, 50);
        Label nameinfo = new Label("the title of movie");
        nameinfo.setBounds(350, 150, 700, 15);
        nameinfo.setForeground(Color.GRAY);


        Label descl = new Label("desc", Label.RIGHT);
        descl.setBounds(250, 200, 100, 50);
        TextField desctf = new TextField(50);
        desctf.setBounds(350, 200, 700, 50);
        Label descinfo = new Label("the description of movie");
        descinfo.setBounds(350, 250, 700, 15);
        descinfo.setForeground(Color.GRAY);

        Label pricel = new Label("price", Label.RIGHT);
        pricel.setBounds(250, 300, 100, 50);
        TextField pricetf = new TextField(50);
        pricetf.setBounds(350, 300, 700, 50);
        Label priceinfo = new Label("double value of price, example:10.2");
        priceinfo.setBounds(350, 350, 700, 15);
        priceinfo.setForeground(Color.GRAY);

        Label runtimel = new Label("runtime", Label.RIGHT);
        runtimel.setBounds(250, 400, 100, 50);
        TextField runtimetf = new TextField(50);
        runtimetf.setBounds(350, 400, 700, 50);
        Label runtimeinfo = new Label("runtime of movie,example:03/03/2019 16:00:00");
        runtimeinfo.setBounds(350, 450, 700, 15);
        runtimeinfo.setForeground(Color.GRAY);

        Label levell = new Label("level", Label.RIGHT);
        levell.setBounds(250, 500, 100, 50);
        TextField leveltf = new TextField(50);
        leveltf.setBounds(350, 500, 700, 50);
        Label levelinfo = new Label("level of movie, G,PG,PG_13,R,NC_17");
        levelinfo.setBounds(350, 550, 700, 15);
        levelinfo.setForeground(Color.GRAY);


        Label showtimesl = new Label("showtimes", Label.RIGHT);
        showtimesl.setBounds(250, 600, 100, 50);
        TextField showtimestf = new TextField(50);
        showtimestf.setBounds(350, 600, 700, 50);
        Label showtimeinfo = new Label("show time of movie, like:04/24/2019 [12:00, 14:00, 16:00, 18:00], 01/25/2019 [11:30, 13:30, 15:30]");
        showtimeinfo.setBounds(350, 650, 700, 15);
        showtimeinfo.setForeground(Color.GRAY);


        Button confirm = new Button("confirm");
        confirm.addActionListener(e -> {
            Movie movie = new Movie();
            movie.setTitle(nametf.getText());
            movie.setDesc(desctf.getText());
            movie.setPrice(new BigDecimal(pricetf.getText()));
            // todo
            movie.setTimeOfMovie(runtimetf.getText());

            movie.setTimeOfShow(showtimestf.getText());
            movie.setLevel(Movie.Rate.valueOf(leveltf.getText()));
            String info = Movie.addNew(movie);
            Object[] kk = {"ok"};
            JOptionPane.showOptionDialog(this.frame, info, "add movie result", 1,
                    1, null, kk, null);
            f.setVisible(false);
            new ManagerMainPage(MANAGER_MAIN_TITLE).run();

        });
        confirm.setBounds(600, 700, 100, 50);

        Button returnb = new Button("return");
        returnb.addActionListener(e -> {
            f.removeAll();
            f.setVisible(false);
            new ManagerMainPage(MANAGER_MAIN_TITLE).run();

        });
        returnb.setBounds(700, 700, 100, 50);

        f.add(la);

        f.add(namel);
        f.add(nametf);
        f.add(nameinfo);

        f.add(descl);
        f.add(desctf);
        f.add(descinfo);


        f.add(pricel);
        f.add(pricetf);
        f.add(priceinfo);


        f.add(runtimel);
        f.add(runtimetf);
        f.add(runtimeinfo);


        f.add(levell);
        f.add(leveltf);
        f.add(levelinfo);


        f.add(showtimesl);
        f.add(showtimestf);
        f.add(showtimeinfo);

        f.add(confirm);
        f.add(returnb);
        f.setVisible(true);


    }

    private void editMovie() {
        //update the info of movie
        Frame f = Flags.f;
        Object[] wo = {"confirm", "cancel"};
        String input = JEnhancedOptionPane.showInputDialog("please input movie title", wo);
        if (input == null || "".equals(input)) {
            return;
        }

        Movie movie = Movie.getByName(input);
        if (null == movie) {
            Object[] kk = {"ok"};
            JOptionPane.showOptionDialog(this.frame, "movie not exists", "wrong", 1,
                    1, null, kk, null);
        } else {
            this.frame.setVisible(false);
            f.removeAll();
            f.setVisible(false);

            Frame f1 = Flags.f;
            f1.setLayout(null);

            Label la = new Label("Please Edit Movie Info", Label.CENTER);
            la.setPreferredSize(new Dimension(1400, 100));
            la.setBounds(0, 0, 1400, 100);

            Label namel = new Label("name", Label.RIGHT);
            namel.setBounds(250, 100, 100, 50);
            TextField nametf = new TextField(50) {
                @Override
                public boolean isEditable() {
                    return false;
                }
            };
            nametf.setBounds(350, 100, 700, 50);
            nametf.setText(movie.getTitle());
            Label nameinfo = new Label("the title of movie");
            nameinfo.setBounds(350, 150, 700, 15);
            nameinfo.setForeground(Color.GRAY);


            Label descl = new Label("desc", Label.RIGHT);
            descl.setBounds(250, 200, 100, 50);
            TextField desctf = new TextField(50);
            desctf.setBounds(350, 200, 700, 50);
            desctf.setText(movie.getDesc());
            Label descinfo = new Label("the description of movie");
            descinfo.setBounds(350, 250, 700, 15);
            descinfo.setForeground(Color.GRAY);

            Label pricel = new Label("price", Label.RIGHT);
            pricel.setBounds(250, 300, 100, 50);
            TextField pricetf = new TextField(50);
            pricetf.setBounds(350, 300, 700, 50);
            pricetf.setText(movie.getPrice().toString());
            Label priceinfo = new Label("double value of price, example:10.2");
            priceinfo.setBounds(350, 350, 700, 15);
            priceinfo.setForeground(Color.GRAY);

            Label runtimel = new Label("runtime", Label.RIGHT);
            runtimel.setBounds(250, 400, 100, 50);
            TextField runtimetf = new TextField(50);
            runtimetf.setBounds(350, 400, 700, 50);
            runtimetf.setText(movie.getTimeOfMovie());
            Label runtimeinfo = new Label("runtime of movie,example:03/03/2019 16:00:00");
            runtimeinfo.setBounds(350, 450, 700, 15);
            runtimeinfo.setForeground(Color.GRAY);

            Label levell = new Label("level", Label.RIGHT);
            levell.setBounds(250, 500, 100, 50);
            TextField leveltf = new TextField(50);
            leveltf.setBounds(350, 500, 700, 50);
            leveltf.setText(movie.getLevel().toString());
            Label levelinfo = new Label("level of movie, G,PG,PG_13,R,NC_17");
            levelinfo.setBounds(350, 550, 700, 15);
            levelinfo.setForeground(Color.GRAY);


            Label showtimesl = new Label("showtimes", Label.RIGHT);
            showtimesl.setBounds(250, 600, 100, 50);
            TextField showtimestf = new TextField(50);
            showtimestf.setBounds(350, 600, 700, 50);
            showtimestf.setText(movie.getTimeOfShow());
            Label showtimeinfo = new Label("show time of movie, like:04/24/2019 [12:00, 14:00, 16:00, 18:00], 01/25/2019 [11:30, 13:30, 15:30]");
            showtimeinfo.setBounds(350, 650, 700, 15);
            showtimeinfo.setForeground(Color.GRAY);


            Button confirm = new Button("confirm");
            confirm.addActionListener(e2 -> {
                Movie movie1 = new Movie();
                movie1.setTitle(nametf.getText());
                movie1.setDesc(desctf.getText());
                movie1.setPrice(new BigDecimal(pricetf.getText()));
                movie1.setTimeOfMovie(runtimetf.getText());

                movie1.setTimeOfShow(showtimestf.getText());
                movie1.setLevel(Movie.Rate.valueOf(leveltf.getText()));
                String info = Movie.setOne(nametf.getText(), movie1);
                Object[] kk = {"ok"};
                JOptionPane.showOptionDialog(this.frame, info, "edit movie result", 1,
                        1, null, kk, null);
                f.setVisible(false);
                new ManagerMainPage(MANAGER_MAIN_TITLE).run();

            });
            confirm.setBounds(600, 700, 100, 50);

            Button returnb = new Button("return");
            returnb.addActionListener(e1 -> {
                f.removeAll();
                f.setVisible(false);
                new ManagerMainPage(MANAGER_MAIN_TITLE).run();

            });
            returnb.setBounds(700, 700, 100, 50);

            f1.add(la);

            f1.add(namel);
            f1.add(nametf);
            f1.add(nameinfo);

            f1.add(descl);
            f1.add(desctf);
            f1.add(descinfo);


            f1.add(pricel);
            f1.add(pricetf);
            f1.add(priceinfo);


            f1.add(runtimel);
            f1.add(runtimetf);
            f1.add(runtimeinfo);


            f1.add(levell);
            f1.add(leveltf);
            f1.add(levelinfo);


            f1.add(showtimesl);
            f1.add(showtimestf);
            f1.add(showtimeinfo);

            f1.add(confirm);
            f1.add(returnb);

            f1.setVisible(true);
        }

    }

    private void gotoCustomer() {
        new CustomerMainPage(CUSTOMER_MAIN_TITLE).run();
    }

    @Override
    public void run() {
        Frame f = this.frame;


        Label la = new Label("Click Button What You Want.", Label.CENTER);
        la.setPreferredSize(new Dimension(1400, 100));
        la.setBounds(0, 0, 1400, 100);
        f.add(la);

        f.setLayout(null);

        Button b1 = new Button("Add movie");
        b1.addActionListener(e -> {
            addMovie();
        });

        b1.setBounds(0, 100, 350, 100);


        Button b2 = new Button("Edit movie");
        b2.addActionListener(e -> {
            editMovie();
        });
        b2.setBounds(350, 100, 350, 100);


        Button b3 = new Button("Enter Customer Mode");
        b3.addActionListener(e -> {
            f.setVisible(false);
            gotoCustomer();
        });
        b3.setBounds(700, 100, 350, 100);


        Button b4 = new Button("Close Application");
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        b4.setBounds(1050, 100, 350, 100);

        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.add(b4);

        f.setVisible(true);

    }

    public static void main(String[] args) {
        new ManagerMainPage("mana").run();
    }
}
