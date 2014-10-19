import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PlayerPaddle
{
    int x;
    int y;
    int width = 10;
    int height = 60;
    int speed = 5;
    
    boolean upKey = false;
    boolean downKey = false;
    
    Rectangle physicalBound;
    
    public PlayerPaddle(int x, int y) //takes the starting position of the paddle
    {
        this.x = x;
        this.y = y;
        
        physicalBound = new Rectangle(x, y, width, height);
        physicalBound.setBounds(x, y, width, height);
    }
    
    public void update(Game game)
    {
        physicalBound.setBounds(x, y, width, height);
        
        if(upKey && y > 0)
        {
            y -= speed;
        }
        
        else if(downKey && y < game.getHeight() - height)
        {
            y += speed;
        }
    }
    
    public void render(Graphics pic)
    {
        pic.setColor(Color.WHITE);
        pic.fillRect(x, y, width, height);
    }
    
}
