
package spacezombies;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @author Christopher Adams
 * This class holds the bullet entity. Every time the player shoots this will be
 * created and loaded onto the screen.
 */

public class Bullet extends JFrame implements Runnable
{
    // Global Variables
    Game game; // parent class
    static Sound s = new Sound("Res/Sounds/Explosion.wav"); // Sound played when shot
    boolean EndProgram = false;
    int BulletNumber; // Current Array index
    
    //Rectangles for collisions
    Rectangle rec = new Rectangle();
    
    //bullet properties
    public int x, y ,w ,h;
    
    public static char bulletType = 'A';
    public static int bulletDamage = 1;
    public static int bulletSpeed = 6;
    public int bulletHealth = 1;

    //Drawing related variables
    boolean firstTime = true;
    public int Time;
    Graphics2D big;
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    BufferedImage bullet;
    
    public Bullet(Game game , int BulletNumber)
    {
        this.BulletNumber = BulletNumber;
        this.game = game;
        
        x = game.Shipx + 100;
        y = game.Shipy + 20;
        w = 51;
        h = 20;
        
        bullet = loadImage("bullet.png");
        
        switch(bulletType)
        {
            case 'A' :
                bulletDamage = 1;
                break;
            case 'B' :
                bulletDamage = 2;
                bulletHealth = 2;
                bulletSpeed = 9;
                break;
            case 'C' :
                bulletDamage = 2;
                bulletHealth = 3;
                bulletSpeed = 12;
                break;
        }
        
        s.Play(); // plays the sound
        
        Thread t = new Thread(this);
        t.start();
    }
    
    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    public void updates()
    {
       game.big.drawImage(bullet, x, y, w , h , game);
    }
    
    
    public void run()
    {
        Time = 0;
        
        while(!EndProgram)
        {
            try
            {
                Time++;
                rec.setBounds(x, y, w, h);
                x += bulletSpeed;
                if(x > 850)
                {
                    System.out.println("Bullet has been terminated");
                    EndProgram = true;
                    game.bullets[BulletNumber] = null;
                    this.dispose();
                }
                Thread.sleep(10);
            }catch(Exception er){}
        }
    }

    public BufferedImage loadImage(String fnm)
    {
        try
        {   
             // Finds the location of the image
             BufferedImage im = ImageIO.read(getClass().getResource("Res/" + fnm));
            return im;
        }
        catch(Exception er)
        {
            er.printStackTrace();
            return null;
        }
    }
}
