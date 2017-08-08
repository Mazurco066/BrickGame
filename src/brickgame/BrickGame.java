/*
    Library imports section
*/
package brickgame;

import javax.swing.JFrame;

/**
 * @author Mazurco066
 */
public class BrickGame {

    public static void main(String[] args) {
        // TODO code application logic here
        
        //creating a gameplay class
        gameplay game = new gameplay();
        
        //Creating a JFrame Window for the game and ajusting its properties
        JFrame obj = new JFrame();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Brick Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
    }
    
}
