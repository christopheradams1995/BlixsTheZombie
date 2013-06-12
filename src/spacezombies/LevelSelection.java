
package spacezombies;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @author Christopher Adams
 * Allows the user to choose the level of the game they want. The levels are 
 * depicted by planets. The user needs to win the previous planet before they
 * can play the next one.
 */
public class LevelSelection extends JFrame implements  Runnable , MouseListener
{
    // Global Variables
    boolean EndProgram = false;
    int mx,my; // Mouse x and y
    //Rectangles used for mouse clicks
    private Rectangle recBack = new Rectangle(120,90,1727,73);
    private Rectangle recLevel1 = new Rectangle(120,90,1727,73);
    private Rectangle recLevel2 = new Rectangle(120,90,1727,73);
    private Rectangle recLevel3 = new Rectangle(120,90,1727,73);
    private Rectangle recLevel4 = new Rectangle(120,90,1727,73);
    private Rectangle recLevel5 = new Rectangle(120,90,1727,73);
    private Rectangle recLevel6 = new Rectangle(120,90,1727,73);
    private Rectangle recLevel7 = new Rectangle(120,90,1727,73);
    private Rectangle recLevel8 = new Rectangle(120,90,1727,73);
    private Rectangle recLevel9 = new Rectangle(120,90,1727,73);
    private Rectangle recLevel10 = new Rectangle(120,90,1727,73);
    
    //Variables related to drawing graphics
    int lockX,lockY;
    boolean isLock = false;
    boolean firstTime = true;
    public int Time;
    
    Graphics2D big;
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    BufferedImage backgroundF;
    BufferedImage lock;
    
    public LevelSelection()
    {
        // Sets The frame settings
        setLayout(null);
        setSize(600,400 );
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setTitle("Space Zombies - Level Selection");
        
        //Sets the rectangles for the buttons
        recBack.setBounds(30,300,60,60);
        recLevel1.setBounds(25,25,70,70);
        recLevel2.setBounds(100,25,70,70);
        recLevel3.setBounds(250,25,70,70);
        recLevel4.setBounds(350,25,70,70);
        recLevel5.setBounds(460,25,70,70);
        recLevel6.setBounds(25,130,70,70);
        recLevel7.setBounds(100,130,70,70);
        recLevel8.setBounds(250,130,70,70);
        recLevel9.setBounds(350,130,70,70);
        recLevel10.setBounds(470,275,100,150);

        //Main background Image
        backgroundF = loadImage("LevelSelection.png");
        lock = loadImage("lock.png");
        
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
        
        Thread t = new Thread(this);
        t.start();
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
            bi = (BufferedImage) createImage(600, 400);
            // Big is the graphics context
            big = bi.createGraphics();
            firstTime = false; // Only runs once
       }

        // Adds all the Images to the graphics context of bi which is a
        // BufferedImage.
        big.drawImage(backgroundF, 0, 0,600,400,this);
        if(isLock)
        {
            big.drawImage(lock, lockX-30, lockY-30,55,69,this);
        }

        // This is the Image that will be shown on the screen
        g2.drawImage(bi, 0, 0, this);
            
            
    }
    
    public void run()
    {
        Time = 0;
        while(!EndProgram)
        {
            try
            {
                if(isLock)
                {
                    Time++;
                }
                if(Time >= 100)
                {
                    isLock = false;
                    Time = 0;
                }
                
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
                
                if(recLevel1.contains(mx,my))
                {
                     Game start = new Game(1);
                     EndProgram = true;
                     this.dispose();
                }
                
                if(recLevel2.contains(mx,my))
                {
                    if(playerStats.playerStats[7] >= 2)
                    {
                        Game start = new Game(2);
                        EndProgram = true;
                        this.dispose();
                    }
                    else
                    {
                        isLock = true;
                        lockX = mx;
                        lockY = my;
                    }
                }
                
                if(recLevel3.contains(mx,my))
                {
                    if(playerStats.playerStats[7] >= 3)
                    {
                        Game start = new Game(3);
                        EndProgram = true;
                        this.dispose();
                    }
                    else
                    {
                        isLock = true;
                        lockX = mx;
                        lockY = my;
                    }
                }
                
                if(recLevel4.contains(mx,my))
                {
                    if(playerStats.playerStats[7] >= 4)
                    {
                        Game start = new Game(4);
                        EndProgram = true;
                        this.dispose();
                    }
                    else
                    {
                        isLock = true;
                        lockX = mx;
                        lockY = my;
                    }
                }
                
                if(recLevel5.contains(mx,my))
                {
                    if(playerStats.playerStats[7] >= 5)
                    {
                        Game start = new Game(5);
                        EndProgram = true;
                        this.dispose();
                    }
                    else
                    {
                        isLock = true;
                        lockX = mx;
                        lockY = my;
                    }
                }
                
                if(recLevel6.contains(mx,my))
                {
                    if(playerStats.playerStats[7] >= 6)
                    {
                        Game start = new Game(6);
                        EndProgram = true;
                        this.dispose();
                    }
                    else
                    {
                        isLock = true;
                        lockX = mx;
                        lockY = my;
                    }
                }
                
                if(recLevel7.contains(mx,my))
                {
                    if(playerStats.playerStats[7] >= 7)
                    {
                        Game start = new Game(7);
                        EndProgram = true;
                        this.dispose();
                    }
                    else
                    {
                        isLock = true;
                        lockX = mx;
                        lockY = my;
                    }
                }
                
                if(recLevel8.contains(mx,my))
                {
                    if(playerStats.playerStats[7] >= 8)
                    {
                        Game start = new Game(8);
                        EndProgram = true;
                        this.dispose();
                    }
                    else
                    {
                        isLock = true;
                        lockX = mx;
                        lockY = my;
                    }
                }
                
                if(recLevel9.contains(mx,my))
                {
                    if(playerStats.playerStats[7] >= 9)
                    {
                        Game start = new Game(9);
                        EndProgram = true;
                        this.dispose();
                    }
                    else
                    {
                        isLock = true;
                        lockX = mx;
                        lockY = my;
                    }
                }
                
                if(recLevel10.contains(mx,my))
                {
                    if(playerStats.playerStats[7] >= 10)
                    {
                        Game start = new Game(10);
                        EndProgram = true;
                        this.dispose();
                    }
                    else
                    {
                        isLock = true;
                        lockX = mx;
                        lockY = my;
                    }
                }
                
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
