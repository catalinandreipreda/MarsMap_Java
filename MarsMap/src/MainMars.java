import javax.swing.*;
import java.awt.*;

public class MainMars {
    public static void main(String[] args) {


        String fileName = "data/marsPolarLarge.csv";


        //Variables
        int width = 1200, height = 600;


        //Creating the main window
        JFrame root = new JFrame("Mars"); //Creating the root
        root.setSize(width, height); //Setting the size
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Setting the close operation

        root.add(new MarsDisplay(width, height, fileName));
        root.setVisible(true); //Displaying the window
    }
}
