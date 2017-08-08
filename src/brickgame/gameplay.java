package brickgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

//Class that extends JPanel for the JFrame in main class and Key and Action Listeners for keyboard events
public class gameplay extends JPanel implements KeyListener, ActionListener{
    
    //Class Atributes
    private boolean play = false;
    private boolean again = false;
    private int score = 0;
    private int difficult = 1;
    
    //Atribute that defines the total of targets to be destroyed
    private int totalBricks = 21;
    
    //Atributes that defines time intervals
    private Timer timer;
    private int delay = 8;
    
    //Atributes that defines the initial positions of screen elements
    private int playerX = 310;
    private int ballX = 120;
    private int ballY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    //atribute type mapGenerator that will draw the blocks
    private mapGenerator map;
    
    //Default Constructor of this class
    public gameplay(){
        
        map = new mapGenerator(3, 7);   //defines the number of rows and columns by the default constructor
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }
    
    //Method that change the color of the object
    public void paint(Graphics g){
        
        //background
        g.setColor(Color.white);    //defines background color
        g.fillRect(1, 1, 692, 592);
        
        //blocks
        map.draw((Graphics2D)g);    //mapGenerator's method for drawing the blocks on the screen
        
        //borders
        g.setColor(Color.black);   //defines de border color
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        //highscores
        g.setColor(Color.red);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("SCORE: " + score, 545, 30);
        
        //paddle
        g.setColor(Color.black);   //defines the player controlled paddle's color
        g.fillRect(playerX, 550, 100, 8);   //defines paddle's dimension and coordinates
        
        //ball
        g.setColor(Color.red);    //defines the ball color
        g.fillOval(ballX, ballY, 20, 20);   //defines ball's dimension and coordinates
        
        //StartCondition
        if (totalBricks == 21 && play == false){
            
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME START", 255, 300);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter key to Start", 255, 400);
        }
        
        //Win Condition
        if (totalBricks <= 0){
            
            //stoping the ball
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU ROCKS", 255, 300);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Your Score: " + score, 280, 330);
            g.drawString("Difficult: " + difficult, 310, 360);
            
            
        }
        
        //Game Over Condition
        if(ballY > 570){
            
            //stoping the ball
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER", 255, 300);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Difficult: " + difficult, 310, 330);
            g.drawString("Enter to Restart", 280, 360);
        }
        
        g.dispose();
    }
    
    //Methods that increments positions vars
    public void moveRight(){
        play = true;
        playerX += 20;
    }
    
    public void moveLeft(){
       play = true;
       playerX -= 20;
    }
    
    //Action Methods
    @Override
    public void actionPerformed(ActionEvent ae) {
        timer.start();
        
        if (play){  //condition that moves the ball and changes it direction when it touchs the border
            
            //Colision Conditionals for paddle
            if (new Rectangle(ballX, ballY, 20 ,20).intersects(new Rectangle(playerX, 550, 100 , 8 ))){
                
                //verify if the ball colides it the player's paddle
                ballYdir = -ballYdir;
            }
            
            //Colision conditionals for bricks
            A: for (int i = 0; i < map.map.length; i++){
                for (int j = 0; j < map.map[0].length; j++){
                    
                    if (map.map[i][j] > 0){
                        
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int BWidth = map.brickWidth;
                        int BHeight = map.brickHeight;
                        
                        Rectangle rect = new Rectangle(brickX, brickY, BWidth, BHeight);
                        Rectangle ballRect = new Rectangle(ballX, ballY, 20 ,20);
                        Rectangle brickRect = rect;
                        
                        //verifying the colision
                        if (ballRect.intersects(brickRect)){
                            
                            map.setValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            
                            //verifying if colided with widht or height
                            if (ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width){
                               
                                ballXdir = -ballXdir;
                            }
                            else{
                                
                                ballYdir = -ballYdir;
                            }
                            
                            break A;
                        }
                    }
                }
            }
            
            //Border Colision Conditions
            ballX += ballXdir;
            ballY +=ballYdir;
            if (ballX < 0){ 
                ballXdir = -ballXdir;
            }
            else if (ballY < 0){
                ballYdir = -ballYdir;
            }
            else if (ballX > 670){
                ballXdir = -ballXdir;
            }
        }
        repaint();  //recall the "paint" method
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
        //detects the moviment key that was pressed
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_RIGHT:{
                
                if (play){
                    if (playerX >= 600){
                        playerX = 600;
                    }
                    else{
                        moveRight();
                    }
                }
                break;
                
            }
                
            case KeyEvent.VK_LEFT:{
                
                if(play){
                    if (playerX < 10){
                        playerX = 10;
                    }
                    else{
                        moveLeft();
                    }
                }
                break;
            }
                
            case KeyEvent.VK_ENTER:{
                //restart condition
                if (!play){
                    
                    //Randomizing ball's spawn
                    Random gerador = new Random();
                    
                    play = true;
                    again = true;
                    ballX = gerador.nextInt(500);   //ok working
                    ballY = 350;
                    
                    switch (difficult) {
                        case 1:{
                            ballXdir = -1;
                            ballYdir = -2;
                            break;
                        }
                        case 2:{
                            ballXdir = -2;
                            ballYdir = -4;
                            break;
                        }
                        default:{
                            ballXdir = -3;
                            ballYdir = -6;
                            break;
                        }
                    }
                    
                    playerX = 310;
                    score = 0;
                    totalBricks = 21;
                    map = new mapGenerator(3, 7);
                    
                    repaint();
                }   
                break;
            }
            
            case KeyEvent.VK_1:{
                
                if (again){
                difficult = 1;
                }
                break;
            }
            
            case KeyEvent.VK_2:{
                
                if (again){
                difficult = 2;
                }
                break;
            }
            
            case KeyEvent.VK_3:{
                
                if (again){
                difficult = 3;
                }
                break;
            }
                
            default:{
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {} //not used
    
    @Override
    public void keyTyped(KeyEvent ke) {}    //not used
    
}
