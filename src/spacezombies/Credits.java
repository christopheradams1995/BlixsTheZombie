
package spacezombies;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Displays the credits on a page for the user to read.
 */


public class Credits extends JFrame implements  Runnable , MouseListener, MouseWheelListener
{
    boolean EndProgram = false;
    // Global Variables
    private BufferedImage background;
    BufferedImage scroll , clickLeft;
    
    //Mouse event rectangles
    Rectangle scrollUp = new Rectangle(1,1);
    Rectangle scrollDown = new Rectangle(1,1);
    Rectangle Back = new Rectangle(1,1);
    
    int mx,my; // Mouse x and y
    
    //Drawing related variables
    String[]StoryList = new String[16];
    int scrolly = 0;
    boolean firstTime = true;
    Graphics2D big;
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    
    public Credits()
    {
        this.setBackground(Color.BLACK);
        // Sets The frame settings
        setLayout(null);
        setSize(600,400 );
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        this.setTitle("Space Zombies");
        
        //Loads the background
        background = loadImage("creditsBackground.jpg");
        scroll = loadImage("scroll.png");
        clickLeft = loadImage("clickLeft.png");
        
        //Rectangles positions and sizes
        scrollUp.setBounds(500, 35,50,50);
        scrollDown.setBounds(500,330,50,50);
        Back.setBounds(30,350,70,40);

        
        
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
        this.addMouseWheelListener(this);
        
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
            big.setColor(Color.yellow);
            firstTime = false; // Only runs once
       }
        // Adds all the Images to the graphics context of bi which is a
        // BufferedImage.

        big.drawImage(background, 0, 0,600,400,this);
        big.drawImage(scroll, 500, 35,37,352,this);

        big.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 34));
        big.drawString("Credits" ,250, 50 + scrolly);
        big.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 16));
        big.drawString("Programmer: Christopher Adams",100,80 + scrolly);
        big.drawString("-Note : these authors are from OpenGameArt.ORG",100,100 + scrolly);
        big.drawString("Graphics Designers:",100,120 + scrolly);
        big.drawString("Zombies:",120,140 + scrolly);
        big.drawString("Curt - cjc83486 ",140,160 + scrolly);
        big.drawString("http://opengameart.org/content/zombie-rpg-sprites",110,180 + scrolly);
        big.drawString("Vortex Background:",120,200 + scrolly);
        big.drawString("darkrose",140,220 + scrolly);
        big.drawString("main menu planets:",120,240 + scrolly);
        big.drawString("scorcher24",140,260 + scrolly);
        big.drawString("Spaceship and Spacebackground:",120,280 + scrolly);
        big.drawString("Gamedevtuts+",140,300 + scrolly);
        big.drawString("Buttons:",120,320 + scrolly);
        big.drawString("Blarumyrran",140,340 + scrolly);
        big.drawString("Character screen GUI: ",120,360 + scrolly);
        big.drawString("Lamoot and Dakal ",140,380 + scrolly);
        big.drawString("All art belongs to their respected owners.",100,400 + scrolly);
        big.drawString("...",100,420 + scrolly);
        big.drawString("Please email me at ",100,440 + scrolly);
        big.drawString("vvolfgames@hotmail.com",100,460 + scrolly);
        big.drawString("about any concerns",100,480 + scrolly);
        // This is the Image that will be shown on the screen
        g2.drawImage(bi, 0, 0, this);
            
    }
    
    public void run()
    {
        while(!EndProgram)
        {
            try
            {
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
        // button click events
        if(scrollUp.contains(mx,my))
        {
            System.out.println("Scroll up");
            scrolly += 50;
        }
        
        if(scrollDown.contains(mx,my))
        {
            System.out.println("Scroll Down");
            scrolly -= 50;
        }
        
        if(Back.contains(mx,my))
        {
            System.out.println("Back");
            EndProgram = true;
            SpaceZombies frame = new SpaceZombies();
            frame.firstTimeSplash = false;
            this.dispose();
        }
        
        if(scrolly <-300)
        {
            scrolly = -300;
        }
        if(scrolly >0)
        {
            scrolly = 0;
        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        if(e.getWheelRotation() == 1)
        {
            scrolly -= 50;
        }
        else
        {
            scrolly += 50;
        }
        
        if(scrolly <-300)
        {
            scrolly = -300;
        }
        if(scrolly >0)
        {
            scrolly = 0;
        }
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

}
