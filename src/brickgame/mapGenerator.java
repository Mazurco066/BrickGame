package brickgame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class mapGenerator {
    
    //Class atributes
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    
    //default constructor for this class
    public mapGenerator(int row, int col){
           
        map = new int[row][col];
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                
                map[i][j] = 1;
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    
    }
    
    //method that draws the blocks on the screen
    public void draw(Graphics2D g){
        
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                
                if(map[i][j] > 0){
                    
                    g.setColor(Color.blue);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.white);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
        
    }
    
    //nnethod that changes the value of a map position
    public void setValue(int value, int row, int col){
        
        map[row][col] = value;
    }
    
}
    
