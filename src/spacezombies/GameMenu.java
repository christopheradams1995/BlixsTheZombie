
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
public class GameMenu extends JFrame implements Runnable , MouseListener
{
    // Global Variables
    private BufferedImage background;
    
    private Rectangle recLevelSelect = new Rectangle(120,90,1727,73);
    private Rectangle recCharacter = new Rectangle(0,0,48,238);
    private Rectangle recExit = new Rectangle(0,0,48,238);
    Rectangle recPopUpClose = new Rectangle(1,1);
    Rectangle PopUpDown = new Rectangle(1,1);
    Rectangle PopUpUp = new Rectangle(1,1);
    int mx,my; // Mouse x and y
    boolean EndProgram = false;
    // Button clicked and non-clicked 
    private BufferedImage[] buttonState = new BufferedImage[2];
    private BufferedImage[] buttons = new BufferedImage[4];
    BufferedImage[] background2 = new BufferedImage[8];
    //private BufferedImage im;
    boolean firstTime = true;
    private GraphicsConfiguration gc;
    public  int Time;
    Graphics2D g2d;
    BufferedImage copy;
    Graphics2D big;
    BufferedImage backgroundF;
    BufferedImage txtLevelSelect, txtCharacter, txtExit;
    boolean FightClick = false;
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    

    
    public GameMenu()
    {
        
        // Sets The frame settings
        setLayout(null);
        setSize(760,600 );
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setTitle("Space Zombies - Menu");

        Thread t = new Thread(this);
        t.start();
        
        //Sets the rectangles for the buttons
        recLevelSelect.setBounds(80,90,600,73);
        recCharacter.setBounds(80,200,600,73);
        recExit.setBounds(80,310,600,73);
        
        backgroundF = loadImage("space.png");
        
        //Text for the buttons
        txtLevelSelect = loadImage("txtLevelSelect.png");
        txtCharacter = loadImage("txtCharacter.png");
        txtExit = loadImage("txtExit.png");
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
        
        //loads the playerStats
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
            bi = (BufferedImage) createImage(1000, 600);
            // Big is the graphics context
            big = bi.createGraphics();
            firstTime = false; // Only runs once
       }
        try
        {
            //Finds the file locations for the images
            buttonState[0] = loadImage("Button.png");
            buttonState[1] = loadImage("Button clicked.png");
        }
        catch(Exception er)
        {
            System.out.println("The button/background image locations couldn't be found");
            er.printStackTrace();
        }
       
            // Adds all the Images to the graphics context of bi which is a
            // BufferedImage.
                big.drawImage(backgroundF, 0, 0,1000,600,this);
                big.drawImage(buttons[0], 20,90,727,73,this);
                big.drawImage(buttons[1], 20,200,727,73,this);
                big.drawImage(buttons[2], 20,310,727,73,this);
                // Button Text
                big.setFont(new Font("GungsuhChe", Font.BOLD, 34));
                big.drawImage(txtLevelSelect, 220,97,318,52,this);
                big.drawImage(txtCharacter, 230,217,279,40,this);
                big.drawImage(txtExit, 310,323,124,43,this);
                
            // This is the Image that will be shown on the screen
            g2.drawImage(bi, 0, 0, this);
            
            
    }
    
    public void run()
    {
        int i = 0;
        Time = 0;
        int backgroundFrameLength = 7;
        while(!EndProgram)
        {
            try
            {
                Time++;
                // New game mouse over button check
                    if(recLevelSelect.contains(mx,my))
                        
                        buttons[0] = buttonState[1];
                    else
                        buttons[0] = buttonState[0];

                    // Load game mouse over button check
                    if(recCharacter.contains(mx,my))
                        
                        buttons[1] = buttonState[1];
                    else
                        buttons[1] = buttonState[0];
                
                    // Options mouse over button check
                    if(recExit.contains(mx,my))
                        buttons[2] = buttonState[1];
                    else
                        buttons[2] = buttonState[0];
                Thread.sleep(90);
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
            // Fight
        if(recLevelSelect.contains(mx,my))
        {
            System.out.println("character selection");
            this.EndProgram = true;
            LevelSelection story = new LevelSelection();
            this.dispose();
        }
        // Shop
        if(recCharacter.contains(mx,my))
        {
            this.EndProgram = true;
            Character character = new Character();
            this.dispose();
        }

        // Inn
        if(recExit.contains(mx,my))
        {
            System.exit(0);
        }

    }
    
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
