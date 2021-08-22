package perspective;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class PageMain {

    public Frame frame;

    public PageMain(String title) {
        Frame f = new Frame();
        f.setSize(400, 400);
        f.setLocation(0, 0);
        f.setBounds(0,0,1400,800);
        f.setTitle(title);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });

        this.frame = f;
    }

    public abstract void run();

}
