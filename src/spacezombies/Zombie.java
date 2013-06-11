
package spacezombies;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @author Christopher Adams
 * This class holds the zombie(s) entities.
 */

public class Zombie extends JFrame implements Runnable
{
    // Global Variables
    public int Time, cooldown;
    public int x, y, w ,h;
    Graphics2D g2d;
    boolean EndProgram = false;
    boolean firstTime = true;
    Graphics2D big;
    Graphics2D g2;
    BufferedImage[] Zombies = new BufferedImage[3];
    BufferedImage zombie;
    int CurrentZombieFrame = 0;
    Game game;
    int speed = 4;
    int Health;
    Rectangle rec = new Rectangle();
    static Random ranLoc = new Random();
    static Random ranSpeed = new Random();
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    public String type;
    public static boolean isExpert = false;
    public boolean isDie = false;
    public int PositionInArray;
    
    public Zombie(Game game , String type)
    {
        this.type = type;
        this.game = game;
        Thread t = new Thread(this);
        Zombies[0] = loadImage("Zombies/"+type+"/1.png");
        Zombies[1] = loadImage("Zombies/"+type+"/2.png");
        Zombies[2] = loadImage("Zombies/"+type+"/3.png");
        //sets the location and width , height of the zombie
        int nextRanLoc = ranLoc.nextInt(270) + 50;
        x = 800;
        y = nextRanLoc;
        w = 62;
        h = 76;
        //
        zombie = Zombies[0];
        if(isExpert)
        {
            speed = ranSpeed.nextInt(7) + 1;
        }else
        speed = ranSpeed.nextInt(5) + 1;
        
        switch(type)
        {
            case"A" : Health = 1;
                      cooldown = 20;
                      if(isExpert)
                      {
                          Health = 3;
                          cooldown = 15;
                      }
                break;
            case"B" : Health = 2; 
                      cooldown = 20;
                      speed = 5;
                      if(isExpert)
                      {
                          Health = 5;
                          cooldown = 15;
                          speed = 7;
                      }
                break;
            case"C" : Health = 100; 
                      speed = 9;
                      cooldown = 70;
                      w = 184;
                      h = 186;
                      y = 220;
                break;
                    
        }
        t.start();

        
    }

    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    public void updates()
    {
       game.big.drawImage(zombie, x, y, w , h , game);
       
    }
    
    
    public void run()
    {
        int i = 0;
        Time = 0;
        int ZombieChangeTime = 0;
        int backgroundFrameLength = 7;
        
        while(!EndProgram)
        {
            try
            {
                if(isDie)
                {
                    Time++;
                    if(Time > 70)
                    {
                        game.zombie[PositionInArray] = null;
                        this.dispose();
                    }
                }
                if(!isDie)
                rec.setBounds(x+10,y+10,w-10,h-30);
                ZombieChangeTime++;
                if(ZombieChangeTime > cooldown)
                {
                    ZombieChangeTime = 0;
                    zombie = Zombies[CurrentZombieFrame];
                    
                    CurrentZombieFrame++;
                    if(CurrentZombieFrame > 2)
                    {
                        if(!isDie)
                        CurrentZombieFrame = 0;
                        else CurrentZombieFrame = 2;
                    }
                }
                x -= speed;
                if(x < -100 && type.equals("A"))
                {
                    //System.out.println("Zombie has been terminated");
                    EndProgram = true;
                    this.dispose();
                }
                else if(x < -50)
                {
                    int newran = ranLoc.nextInt(270) + 50;
                    x = 800;
                    y = game.Shipy;
                }
                Thread.sleep(10);
            }catch(Exception er){}
        }
    }
    
    public void Die(int PositionInArray)
    {
        isDie = true;
        rec = new Rectangle(0,0,0,0);
        this.PositionInArray = PositionInArray;
        speed = 0;
        Zombies[0] = loadImage("Zombies/"+type+"/Dead/1.png");
        Zombies[1] = loadImage("Zombies/"+type+"/Dead/2.png");
        Zombies[2] = loadImage("Zombies/"+type+"/Dead/3.png");
        zombie = Zombies[0];
        cooldown = 15;
        if(type != "C")
        {
            w = 124;
            h = 152;
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
