package puf.m2.rcw;

import java.awt.EventQueue;

public class MainUi {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

}
