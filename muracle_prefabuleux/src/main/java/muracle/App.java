package muracle;


import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import muracle.vue.MainWindow;

import javax.swing.*;


public class App {

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        try {
            UIManager.setLookAndFeel(new FlatNordIJTheme());
            SwingUtilities.updateComponentTreeUI(mainWindow);
        }catch (Exception ignored){}
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

