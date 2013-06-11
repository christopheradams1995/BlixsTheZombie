/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spacezombies;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *@author Chris
 * This class is dedicated to an introduction to the story of the game. It will
 * have scrolling text down the page in a starwars theme-ish deal.
 */


public class IntroStory extends JFrame implements Runnable , MouseListener
{
    // Global Variables
    private BufferedImage background;
    int mx,my; // Mouse x and y
    boolean EndProgram = false;
    //private BufferedImage im;
    boolean firstTime = true;
    String[]StoryList = new String[16];

    int StoryY = 400;
    Graphics2D g2d;
    BufferedImage copy;
    Graphics2D big;
    int ScrollSpeed = 1;
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    
    public IntroStory()
    {

        // Sets The frame settings
        setLayout(null);
        setSize(600,400 );
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setTitle("SpaceZombies - Story");

        Thread t = new Thread(this);
        t.start();

        //Loads the background
        background = loadImage("space.png");
        
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
        
        // Story list for the scrolling text
           StoryList[0] = "In lands unknown to the human ";
           StoryList[1] = "race there was one lab. It's";
           StoryList[2] = " main purpose was to find ";
           StoryList[3] = "the cure for the virus killing the";
           StoryList[4] = " peaceful alien race named Zutig.";
           StoryList[5] = " Things went horrible wrong and";
           StoryList[6] = " the species unfortunatly went";
           StoryList[7] = " extinct.... Just a few decades later";
           StoryList[8] = " the human space exploration team";
           StoryList[9] = " ventured out.They discovered";
           StoryList[10] =" the wreck that once was an";
           StoryList[11] =" empire but that wasn't the only";
           StoryList[12] =" thing they found....";
           StoryList[13] ="....";
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
       
                big.setColor(Color.white);
                big.drawImage(background, 0, 0,600,400,this);
                big.setFont(new Font("Tempus Sans ITC", Font.BOLD, 34));
                big.drawString(StoryList[0] ,10, StoryY);
                big.drawString(StoryList[1] ,10, StoryY + 30);
                big.drawString(StoryList[2] ,10, StoryY + 70);
                big.drawString(StoryList[3] ,10, StoryY + 100);
                big.drawString(StoryList[4] ,10, StoryY + 130);
                big.drawString(StoryList[5] ,10, StoryY + 160);
                big.drawString(StoryList[6],10, StoryY + 190);
                big.drawString(StoryList[7],10, StoryY + 220 );
                big.drawString(StoryList[8] ,10, StoryY + 250 );
                big.drawString(StoryList[9] ,10, StoryY + 280 );
                big.drawString(StoryList[10] ,10, StoryY + 310 );
                big.drawString(StoryList[11] ,10, StoryY + 340 );
                big.drawString(StoryList[12] ,10, StoryY + 370 );
                if(StoryY <= -450)
                {
                    // Start the next frame
                    GameMenu start = new GameMenu();
                    EndProgram = true;
                    this.dispose();
                }
            // This is the Image that will be shown on the screen
            g2.drawImage(bi, 0, 0, this);
            
            
    }
    
    public void run()
    {
        int i = 0;
        int Time = 0;
        
        while(!EndProgram)
        {
            try
            {
                Time++;
                StoryY -= ScrollSpeed;

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
    public void mousePressed(MouseEvent e) 
    {
        ScrollSpeed = 10;
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        ScrollSpeed = 1;
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}



























