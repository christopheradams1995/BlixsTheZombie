
/**
 * Title: Rpg Game
 * @author Christopher Adams
 * @version 1.0
 * Description: This project is aimed towards making a simple rpg game.
 * The hero will be able to fight to level and find gold where they will
 * spend it at a shop or gamble at an INN. There will be monsters and random
 * events which will test the hero's courage and skill. The earlier versions will
 * be relatively simple graphics wise and new stuff will be added in the future.
 */

package spacezombies;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @author Christopher Adams
 * This class will hold the startup Menu which will lead the user to various
 * other forms such as NewGame, LoadGame , Options and Credits. This class will
 * only run once.
 * 
 */
public class SpaceZombies extends JFrame implements  Runnable , MouseListener
{
    // Global Variables
    private BufferedImage background;
    
    private Rectangle recNewGame = new Rectangle();
    private Rectangle recCredits = new Rectangle();;
    BufferedImage title = null;
    BufferedImage SplashScreen;
    int mx,my; // Mouse x and y
    
    // Button clicked and non-clicked
    private BufferedImage[] buttonState = new BufferedImage[2];
    private BufferedImage button;
    private BufferedImage newIcon;
    //private BufferedImage im;
    boolean firstTime = true, firstTime2 = true;
    boolean Splash = true;
    public  int Time;
    Graphics2D g2d;
    BufferedImage copy;
    Graphics2D big;
    boolean EndProgram = false;
    static SpaceZombies frame;
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    
    public SpaceZombies()
    {
        playerStats.loadStats();
        if(playerStats.isLoading)
            setTitle("-Loading Save-");
        // Sets The frame settings
        setLayout(null);
        setSize(600,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setIconImage(title);

        Thread t = new Thread(this);
        t.start();
        
        //Sets the rectangles for the buttons
        recNewGame.setBounds(20,90,200,200);
        recCredits.setBounds(450,340,100,100);
        // Used for the buffered Image
        
        // Defualt Image not clicked
        button = buttonState[0];
        
        //Loads the swing icon
        newIcon = loadImage("newIcon.png");
        this.setIconImage(newIcon);
        
        // vvolf games logo
        SplashScreen = loadImage("vvolfgames.png");
        
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
    
    public void actionPerformed(ActionEvent e)
    {
        System.out.println(e);
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
            bi = (BufferedImage) createImage(600, 450);
            // Big is the graphics context
            big = bi.createGraphics();
            firstTime = false; // Only runs once
       }
        try
        {
            //Finds the file locations for the images
            buttonState[0] = loadImage("ButtonPlanetNormal.png");
            buttonState[1] = loadImage("ButtonPlanetHover.png");
            background = loadImage("paperbackground.png");
            title = loadImage("28dayslater.PNG");
        }
        catch(Exception er)
        {
            System.out.println("The button/background image locations couldn't be found");
            er.printStackTrace();
        }
       
            // Adds all the Images to the graphics context of bi which is a
            // BufferedImage.
            if(Splash)
            {
                big.drawImage(SplashScreen,0,0,600,400,this);
            }
            else
            {
                big.drawImage(background, 0, 0,600,450,this);
                big.drawImage(button, 20,90,200,200,this);
                big.drawImage(title, 20,30,500,106,this);
            }
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
                System.out.println(Time);
                if(Time > 50 && firstTime2)
                {
                    Splash = false;
                    this.setSize(600,450);
                    firstTime2 = false;
                }
               
                 if(!playerStats.isLoading)
                    setTitle("SpaceZombies!");
                
                // New game mouse over button check
                if(!Splash)
                {
                    if(recNewGame.contains(mx,my))
                        button = buttonState[1];
                    else
                        button = buttonState[0];
                }
                Thread.sleep(10);
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
    
    public static void main(String[] args) 
    {
        try
        {
            // Changes the Defualt frame look
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch(Exception e){}

        // Starts the Entire Class
        frame = new SpaceZombies();
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        // button click events
        if(!Splash)
        {
                if(recNewGame.contains(mx,my))
                {
                    this.EndProgram = true;
                    IntroStory story = new IntroStory();
                    this.dispose();
                }
                
                if(recCredits.contains(mx,my))
                {
                    this.EndProgram = true;
                    Credits credits = new Credits();
                    this.dispose();
                }
        }

    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
