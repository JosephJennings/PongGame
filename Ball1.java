import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball
{
    int x;
    int y;
    int size = 30;
    int vx, vy; // velocity in x and velocity in y
    int speed = 3;
    
    Rectangle physicalBound;
    
    public Ball(int x, int y)
    {
        this.x = x;
        this.y = y;
        vx = speed;
        vy = speed;
        
        physicalBound = new Rectangle(x, y, size, size);
        physicalBound.setBounds(x, y, size, size);
    }
    
    public void update(Game game)
    {
        physicalBound.setBounds(x, y, size, size);
        
        if(x <= 0)
        {
            vx = speed;
        }
        else if(x + size >= game.getWidth())
        {
            vx = -speed;
            game.playerOneScore++; // add one to player one's score when the ball hits the wall
        }
        
        if(y <= 0)
        {
            vy = speed;
        }
        else if(y + size >= game.getHeight())
        {
            vy = -speed;
            game.playerTwoScore++; // add one to player two's score when the ball hits the wall
        }
        
        x += vx;
        y += vy;
        
        impact(game);
    }
    
    private void impact(Game game)
    {
        if(physicalBound.intersects(game.player.physicalBound))
        {
            vx = speed;
        }else if(physicalBound.intersects(game.ai.physicalBound))
        {
            vx = -speed;
        }
    }
    
    public void render(Graphics pic)
    {
        pic.setColor(Color.WHITE);
        pic.fillOval(x, y, size, size);
    }
}
