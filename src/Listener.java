import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Listener implements WindowListener {

    private JFrame frame;

    public Listener(JFrame frame) {
        this.frame = frame;
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
    if (JOptionPane.showConfirmDialog(frame,"Do you really want to close the window?") == JOptionPane.YES_OPTION){
        frame.dispose();
        System.exit(0);

    }
    }


    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
