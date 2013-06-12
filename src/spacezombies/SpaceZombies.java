
/**
 * First game made using java.
 * Title: SpaceZombies
 * @author Christopher Adams
 * 
 * Description:
 * This game is arcade side-scroller themed with a leveling system. You gain xp by killing zombies
 * and complete levels by killing the witches.
 * There are three types of zombies:
 * A: Common zombies which take off one health when hit.
 * B: Witches which follow you and kill you instantly.
 * C: The Boss! Faster and stronger version of the common zombie. Just like the
 * witch it will kill you instantly when it touches you.
 */

package spacezombies;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * This class will lead the user to Credits and the game. It's a navigation
 * screen with a splash screen. The splash screen will be displayed first then
 * wait x time then close.
 */
public class SpaceZombies extends JFrame implements  Runnable , MouseListener
{
    // Global Variables
    static SpaceZombies frame;
    boolean EndProgram = false;
    
    //Buffered Images
    private BufferedImage background;
    private BufferedImage title = null;
    private BufferedImage SplashScreen;
    private BufferedImage button;
    private BufferedImage newIcon;
    private BufferedImage[] buttonState = new BufferedImage[2];
    
    //Rectangles used for mouse events
    private Rectangle recNewGame = new Rectangle();
    private Rectangle recCredits = new Rectangle();;

    int mx,my; // Mouse x and y

    //Variables used for painting
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d;
    Graphics2D big;
    boolean firstTimeDrawing = true;
    boolean firstTimeSplash = true;
    boolean Splash = true;
    public int Time;
    
    
    
    public SpaceZombies()
    {
        // Loads the players stats
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
        
        //Loads the swing icon
        newIcon = loadImage("newIcon.png");
        this.setIconImage(newIcon);
        
        //Sets the rectangles for the buttons
        recNewGame.setBounds(20,90,200,200);
        recCredits.setBounds(450,340,100,100);
        
        // Defualt Image not clicked
        button = buttonState[0];
        
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
       
       if(firstTimeDrawing)
       {
            bi = (BufferedImage) createImage(600, 450);
            // Big is the graphics context
            big = bi.createGraphics();
            firstTimeDrawing = false; // Only runs once
       }
        try
        {
            //Finds the file locations for the images
            buttonState[0] = loadImage("ButtonPlanetNormal.png");
            buttonState[1] = loadImage("ButtonPlanetHover.png");
            background = loadImage("mainMenuBackground.png");
            title = loadImage("mainTitle.PNG");
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
        Time = 0;
        while(!EndProgram)
        {
            try
            {
                Time++;
                System.out.println(Time);
                if(Time > 200 || !firstTimeSplash)
                {
                    Splash = false;
                    this.setSize(600,450);
                    firstTimeSplash = false;
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
