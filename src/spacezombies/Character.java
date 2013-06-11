
package spacezombies;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @author Christopher Adams
 * This class will hold the startup Menu which will lead the user to various
 * other forms such as NewGame, LoadGame , Options and Credits. This class will
 * only run once.
 * 
 */
public class Character extends JFrame implements  Runnable , MouseListener
{
    // Global Variables
    private BufferedImage background;
    //Rectangles
    private Rectangle recBack = new Rectangle(120,90,1727,73);
    private Rectangle recNextShip = new Rectangle(120,90,1727,73);
    private Rectangle recPrevShip = new Rectangle(120,90,1727,73);
    private Rectangle recSpeed = new Rectangle(350,110,150,40);
    private Rectangle recPower = new Rectangle(350,175,150,40);
    private Rectangle recLuck = new Rectangle(350,230,150,40);
    private Rectangle recSave = new Rectangle(350,230,150,40);

    
    int mx,my; // Mouse x and y
    boolean EndProgram = false;
    // Button clicked and non-clicked 
    private BufferedImage[] buttonState = new BufferedImage[2];
    private BufferedImage[] buttons = new BufferedImage[4];
    private BufferedImage[] Ships = new BufferedImage[3];
    
    BufferedImage[] background2 = new BufferedImage[8];
    //private BufferedImage im;
    boolean firstTime = true;
    private GraphicsConfiguration gc;
    public  int Time;
    Graphics2D g2d;
    BufferedImage copy;
    Graphics2D big;
    BufferedImage[] CurrentArray;
    BufferedImage backgroundF;
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    int currentShip = playerStats.playerStats[0];
    

    
    public Character()
    {

        // Sets The frame settings
        setLayout(null);
        setSize(600,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setTitle("Space Zombies - Character");

        Thread t = new Thread(this);
        t.start();
        
        //Sets the rectangles for the buttons
        recBack.setBounds(100,300,80,80);
        recNextShip.setBounds(240,130,50,50);
        recPrevShip.setBounds(80,130,50,50);
        recSave.setBounds(220,300,80,80);
        
        recSpeed.setBounds(350,110,150,40);
        recPower.setBounds(350,175,150,40);
        recLuck.setBounds(350,230,150,40);

        
        //3 ships to choose from
        Ships[0] = loadImage("Ship1.png");
        Ships[1] = loadImage("Ship2.png");
        Ships[2] = loadImage("Ship3.png");
        
        //town
        
        backgroundF = loadImage("Character.png");
        
        //Mouse movement events
        addMouseMotionListener(new MouseMotionAdapter()
        {
            public void mouseMoved(MouseEvent e)
            {
                mx = e.getX();
                my = e.getY();
            }
        });
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    @Override
    public void update(Graphics g)
    {
       Graphics2D g2 = (Graphics2D) g;
       
       if(firstTime)
       {
            bi = (BufferedImage) createImage(600,400);
            // Big is the graphics context
            big = bi.createGraphics();
            firstTime = false; // Only runs once
       }
       
        // Adds all the Images to the graphics context of bi which is a
        // BufferedImage.
        big.drawImage(backgroundF, 0, 0,600,400,this);
        big.drawImage(Ships[currentShip], 120, 120, 117 , 63 , this);
        // Button Text
        big.setFont(new Font("GungsuhChe", Font.BOLD, 34));
        big.drawString(""+ playerStats.playerStats[2] ,450, 130);
        big.drawString(""+ playerStats.playerStats[1] ,450, 200);
        big.drawString(""+ playerStats.playerStats[3] ,450, 270);
        big.drawString(""+ playerStats.playerStats[6] ,470, 330);
        // This is the Image that will be shown on the screen
        g2.drawImage(bi, 0, 0, this);   
    }
    
    
    public void run()
    {
        int i = 0;
        Time = 0;
        while(!EndProgram)
        {
            try
            {
                Time++;
                
                Thread.sleep(30);
                repaint();
            }catch(Exception er){}
        }
    }

    /**
     * 
     * @param fnm : file location of the image
     * @return : Returns a BufferedImage
     */
    
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

    @Override
    public void mouseClicked(MouseEvent e) 
    {
                // Back
                if(recBack.contains(mx,my))
                {
                    this.EndProgram = true;
                    GameMenu menu = new GameMenu();
                    this.dispose();
                }
                
                if(recNextShip.contains(mx,my))
                {
                    System.out.println("potato");
                    if(currentShip == 2)
                        currentShip = 0;
                    else
                    {
                        currentShip++;
                    }
                }
                
                if(recPrevShip.contains(mx,my))
                {
                    
                    if(currentShip == 0)
                        currentShip = 2;
                    else
                    {
                        currentShip--;
                    }
                }
                
                if(recSave.contains(mx,my))
                {
                    playerStats.playerStats[0] = currentShip;
                    playerStats.saveStats();
                    System.out.println("SAVED!");
                }
                
                if(recSpeed.contains(mx,my))
                {
                    System.out.println("Speed");
                    if(playerStats.playerStats[6] > 0  && playerStats.playerStats[2] <= 5)
                    {
                        playerStats.playerStats[2]++;
                        playerStats.playerStats[6]--;
                    }
                }
                
                if(recPower.contains(mx,my))
                {
                    System.out.println("Power");
                    if(playerStats.playerStats[6] > 0  && playerStats.playerStats[1] <= 9)
                    {
                        playerStats.playerStats[1]++;
                        playerStats.playerStats[6]--;
                    }
                }
                
                if(recLuck.contains(mx,my))
                {
                    System.out.println("Luck");
                    if(playerStats.playerStats[6] > 0 && playerStats.playerStats[3] <= 19)
                    {
                        playerStats.playerStats[3]++;
                        playerStats.playerStats[6]--;
                    }
                }

    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
