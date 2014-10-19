import java.awt.Canvas; //the thing we draw on
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.Color;

public class Game extends Canvas implements Runnable
{
    public static PlayerPaddle player;
    UserInput inputReader;
    public static AIPaddle ai;
    public static Ball ball;
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    JFrame frame;    
    public final int WIDTH = 600; //sets game width
    public final int HEIGHT = WIDTH / 16 * 9; // makes the game rune 16:9
    public final Dimension gameSize = new Dimension(WIDTH, HEIGHT); // the games demensions
    public final String TITLE = "Acme Pong";
    int playerOneScore, playerTwoScore;
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); // creates a new image with our games width and height with RGB color
    
    boolean gameRunning = false; // the game is not running
    
    public void run() //allows the game to be made into a thread for the process to run
    {
        while(gameRunning) // if game running = true
        {
            update(); 
            render();
            
            try
            {
                Thread.sleep(8); // put the program thread on hault for 8 miliseconds
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }        
    }
    
    public synchronized void start() // starts the game (creates a thread for it to run on)
    {
        gameRunning = true;
        new Thread(this).start();
    }
    
    public synchronized void stop() //stops the game
    {
        gameRunning = true;
        System.exit(0);
    }
    
    public Game()
    {
        frame = new JFrame();
        
        this.setMinimumSize(gameSize); //set min size
        this.setPreferredSize(gameSize); // set preferred size
        this.setMaximumSize(gameSize); // set max size
        
        frame.add(this, BorderLayout.CENTER); // english: in the frame container add the Game class and the Canvas and place it in the center
        frame.pack(); // loads the frame (does not display it)
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exits the program and stops it from running
        frame.setVisible(true); // makes the game visible
        frame.setResizable(false); // does not let the game get resized
        frame.setTitle(TITLE); // sets the frame title
        frame.setLocationRelativeTo(null); // makes the game load to the center of the screen
        player = new PlayerPaddle(10, 60); // creates a player at location x,y
        ai = new AIPaddle(getWidth()-10, 60); // this needs to be updated if AIPaddle width is changed
        ball = new Ball(getWidth()/2, getHeight()/2); // spawns the ball in the center of the Canvas
        inputReader = new UserInput(this); // cerates instances of input handler within the canvas         
    }
    
    public void update()
    {
        player.update(this);
        ai.update(this);
        ball.update(this);
    }
    
    public void render()
    {
        BufferStrategy tactic = getBufferStrategy(); // the tactic of how the image is buffered
        
        if(tactic == null)
        {
            createBufferStrategy(5); //loads the image multiple times (2) to reduce screen tearing
            return;
        }
        
        Graphics pic = tactic.getDrawGraphics(); // draw the image according to the buffer strategy
        pic.drawImage(image, 0 ,0, getWidth(), getHeight(), null); // starts to draw the image in the upper left hand corner aka 0,0 and ends in the bottom right
        pic.setColor(Color.GREEN);
        pic.drawString("Player 1: " + playerOneScore, 90, 10);
        pic.drawString("Player 2: " + playerTwoScore, getWidth() - 150, 10);
        //pic.setColor(Color.BLUE); // sets the color ~~~note: must be after the draw image to avoid being the default(black).
        //pic.fillRect(0, 0, getWidth(), getHeight()); // draws a rectangle from the upper left hand corner to the lower right hand corner of the Canvas.
        player.render(pic); // uses the buffer and graphics to draw the player paddle (MUST BE AFTER THE BACKGROUND)
        ai.render(pic); // used to render the ai paddle
        ball.render(pic); // used to render the ball
        pic.dispose(); //discards the graphics 
        tactic.show(); //show the image that was buffered
    }
    
    public static void main(String[] args)
    {
        Game game = new Game(); //create a new instance of Game
        game.start(); // call the start method
    }
}
