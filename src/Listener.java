import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Listener implements WindowListener {

    public JFrame frame;


    /**
     * Creates a new window event listener instance.
     *
     * @param frame The window this listener is attached to.
     */
    public Listener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Method called when trying to close the window.
     * Displays a confirmation dialog and closes the application if "Yes" is selected.
     *
     * @param e The window close event.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        int choice = JOptionPane.showConfirmDialog(frame, "Do you really want to close the window?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            frame.dispose();
            System.exit(0);
        } else {
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
