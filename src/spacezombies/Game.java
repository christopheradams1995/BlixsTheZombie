
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
 * --
 * 
 */
public class Game extends JFrame implements Runnable , MouseListener, KeyListener
{
    // Global Variables
    private int Health = 3;
    int mx,my; // Mouse x and y
    boolean EndProgram = false;
    // Button clicked and non-clicked 
    private BufferedImage Ship;
    
    //Ships collision rectangle
    Rectangle shipRec;
    //private BufferedImage im;
    boolean firstTime = true;
 //   private GraphicsConfiguration gc;
    public int Time, ZombieSpawn = 0;;
    Graphics2D g2d;
    Graphics2D g2;
    BufferedImage copy;
    Graphics2D big;
    BufferedImage[] CurrentArray;
    BufferedImage background1;
    BufferedImage background2;
    int back1X = 0,back1Y = 0,back2X = 904,back2Y = 0;
    int Shipx = 50, Shipy = 50;
    BufferedImage[] backgrounds = new BufferedImage[2];
    BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
    Bullet[] bullets = new Bullet[30];
    int speed = 5, bulletCooldown = 65 , commonZombieCooldown = 100, WitchZombieCooldown = 1000; // Game Info
    int currentShip = 0;
    boolean isLeft = false , isRight = false , isDown = false, isUp = false, isSpace = false;
    boolean isCooldown = false, isGameOver = false, isWonGame = false;
    int CurrentBullet = 0;
    int CurrentZombie = 0;
    int GameKills = 0;
    int fps = 60;
    BufferedImage[] Ships = new BufferedImage[4];
    Zombie[] zombie = new Zombie[50];
    int level;
    //Used for the game loop
    int ShipAnimationCurrent = 0;
    int GameOverWait = 0;
    //Used for the fps calculation
    long lastFpsTime;
    public static int fpsToPrint;
    
    
    public Game(int level)
    {
        this.level = level;
        // Sets The frame settings
        setLayout(null);
        setSize(800,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setTitle("Space Zombies - Level Selection");

        Thread t = new Thread(this);
        t.start();

        background1 = loadImage("GameSpace1.png");
        background2 = loadImage("GameSpace2.png");
        // sets the current ship colour dependiing on what the player chose
        if(playerStats.playerStats[0] == 0)
        {
            Ships[0] = loadImage("Ship/blue/f1.png");
            Ships[1] = loadImage("Ship/blue/f2.png");
            Ships[2] = loadImage("Ship/blue/f3.png");
            Ships[3] = loadImage("Ship/blue/f4.png");
        }
        else if(playerStats.playerStats[0] == 1)
        {
            Ships[0] = loadImage("Ship/red/f1.png");
            Ships[1] = loadImage("Ship/red/f2.png");
            Ships[2] = loadImage("Ship/red/f3.png");
            Ships[3] = loadImage("Ship/red/f4.png");
        }
        else if(playerStats.playerStats[0] == 2)
        {
            Ships[0] = loadImage("Ship/yellow/f1.png");
            Ships[1] = loadImage("Ship/yellow/f2.png");
            Ships[2] = loadImage("Ship/yellow/f3.png");
            Ships[3] = loadImage("Ship/yellow/f4.png");
        }
        Ship = Ships[0];
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
        addKeyListener(this);
        UpdateStats();
    }
    
    //Updates the player data depending on what stats the player has
    public void UpdateStats()
    {
        Health = 3 + playerStats.playerStats[3] * 3;
        switch(playerStats.playerStats[3])
        {
            case 3 : Bullet.bulletType = 'A'; break;
            case 4 : Bullet.bulletType = 'A'; break;
            case 5 : Bullet.bulletType = 'A'; break;
            case 6 : Bullet.bulletType = 'B'; break;
            case 7 : Bullet.bulletType = 'B'; break;
            case 8 : Bullet.bulletType = 'B'; break;
            case 9 : Bullet.bulletType = 'C'; break;
            case 10 : Bullet.bulletType = 'C'; break;
            case 11 : Bullet.bulletType = 'C'; break;
            case 12 : Bullet.bulletType = 'C'; break;
            case 13 : Bullet.bulletType = 'C'; break;
            case 14 : Bullet.bulletType = 'C'; break;
            case 15 : Bullet.bulletType = 'C'; break;
            case 16 : Bullet.bulletType = 'C'; break;
            case 17 : Bullet.bulletType = 'C'; break;
            case 18 : Bullet.bulletType = 'C'; break;
        }
        
        bulletCooldown = 65 - (5 * playerStats.playerStats[1]);
        if(playerStats.playerStats[1] == 10){bulletCooldown = 7;}
        speed = 5 + playerStats.playerStats[2];
    }
    

    @Override
    public void paint(Graphics g)
    {
        update(g);
    }
    
    @Override
    public void update(Graphics g)
    {
       g2 = (Graphics2D) g;
       
       if(firstTime)
       {
            bi = (BufferedImage) createImage(1000,400);
            // Big is the graphics context
            big = bi.createGraphics();
            big.setColor(Color.RED);
            firstTime = false; // Only runs once
       }
       
            // Adds all the Images to the graphics context of bi which is a
            // BufferedImage.
                big.drawImage(background1, back1X, back1Y,904,400,this);
                big.drawImage(background2, back2X, back2Y,877,400,this);
                big.drawImage(Ship, Shipx, Shipy, 117 , 63 , this);
                // Saves resources by not loading the font if it's already loaded
                if(!big.getFont().equals(new Font("GungsuhChe", Font.BOLD, 24)))
                big.setFont(new Font("GungsuhChe", Font.BOLD, 24));
                big.drawString("FPS: " + fpsToPrint ,200, 380);
                big.drawString("Health: " + Health ,650, 380);
                big.drawString("Kills: " + playerStats.playerStats[5] ,500, 380);
                big.drawString("Level: " + playerStats.playerStats[4] ,350, 380);
                
                if(isGameOver)
                {
                    if(!big.getFont().equals(new Font("GungsuhChe", Font.BOLD, 64)))
                    big.setFont(new Font("GungsuhChe", Font.BOLD, 64));
                    big.drawString("GAME OVER - Saving" ,100, 200);
                }
                if(isWonGame)
                {
                    if(!big.getFont().equals(new Font("GungsuhChe", Font.BOLD, 48)))
                    big.setFont(new Font("GungsuhChe", Font.BOLD, 48));
                    big.drawString("YOU HAVE WON! - Saving" ,30, 200);
                }
                    
                if(playerStats.playerStats[6] != 0)
                {
                    if(!big.getFont().equals(new Font("GungsuhChe", Font.BOLD, 24)))
                    big.setFont(new Font("GungsuhChe", Font.BOLD, 24));
                    big.drawString("~Level UP~" ,0, 380);
                }
                
                for(int i=0;i<30;i++)
                {
                    try
                    {
                        if(bullets[i] != null)
                        bullets[i].updates();
                    }
                    catch(Exception e)
                    {
                        
                    }
                }
                
                for(int i=0;i<50;i++)
                {
                    try
                    {
                        if(zombie[i] != null)
                        zombie[i].updates();
                    }
                    catch(Exception e)
                    {
                        
                    }
                }
                // Button Text

                
            // This is the Image that will be shown on the screen
            g2.drawImage(bi, 0, 0, this);
    }
    
    
    public void run()
    {
        long lastLoopTime = System.nanoTime();
        final int TargetFps = 45;
        final long OptimalTime = 1000000000 / TargetFps;
        
        while(!EndProgram)
        {
            /**
             * This will work out how long since the last update which will
             * be used to calculate the animation speed. This would vary 
             * from system to system.
             */
            
            long now = System.nanoTime();
            //How long it takes to do an entire loop of the program
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            //double delta = updateLength / ((double)OptimalTime);
            
            //Updates the frame counter
            lastFpsTime += updateLength;
            fps++;
            
            //Update our FPS counter if a second has passed since
            // the last recording
            if(lastFpsTime >= 1000000000)
            {
                System.out.println("FPS = " + fps + ")");
                fpsToPrint = fps;
                lastFpsTime = 0;
                fps = 0;
            }
            
            
            try
            {
                gameUpdate(); // game logic
                repaint(); // drawings
                try
                {
                    Thread.sleep( (lastLoopTime-System.nanoTime() + OptimalTime)/1000000);
                }catch(Exception are){}
            }
            catch(Exception er)
            {
                
            }
        }
    }
    
    //MAIN GAME UPDATE
    public void gameUpdate()
    {
        Time++;
        ZombieSpawn++;

        playerStats.LevelCheck();

        if((isGameOver || isWonGame) && !playerStats.isSaving)
        {
            playerStats.isSaving = true;
            playerStats.saveStats();
        }
        
        if(isWonGame)
        {
            if(playerStats.playerStats[7] == 0)
            playerStats.playerStats[7] = 1;
            
            if(level == playerStats.playerStats[7])
            {
                playerStats.playerStats[7] = level+1;
            }
        }
        
        //checks which level it is
        switch(level)
        {
            case 1:
                levelOne();
                Zombie.isExpert = false;
                    break;
            case 2:
                levelTwo();
                Zombie.isExpert = false;
                    break;
            case 3:
                levelThree();
                Zombie.isExpert = false;
                    break;
            case 4:
                levelFour();
                Zombie.isExpert = false;
                    break;
            case 5:
                levelFive();
                Zombie.isExpert = false;
                    break;
            case 6:
                levelTwo(); //expert mode
                Zombie.isExpert = true;
                    break;
            case 7:
                levelThree();//expert mode
                Zombie.isExpert = true;
                    break;
            case 8:
                levelFour();//expert mode
                Zombie.isExpert = true;
                    break;
            case 9:
                levelFive();//expert mode
                Zombie.isExpert = true;
                    break;
            case 10:
                levelTen();
                    break;
        }

        if(CurrentZombie > 49)
        {
            CurrentZombie = 0;
        }


        // Background scrolling
        back1X -=1;
        back2X -=1;
        if(back1X <= -870)
        {
            back1X = 870;
        }

        if(back2X <= -870)
        {
            back2X = 870;
        }

        //Character Movement
        if(isRight)
        {
            Shipx += speed;
        }

        if(isLeft)
        {
            Shipx -= speed;
        }

        if(isUp)
        {
            Shipy -= speed;
        }

        if(isDown)
        {
            Shipy += speed;
        }
        if(isSpace && isCooldown == false)
        {
            bullets[CurrentBullet] = new Bullet(this, CurrentBullet);
            CurrentBullet++;
            if(CurrentBullet > 29)
            {
                CurrentBullet = 0;
            }
            isCooldown = true;
        }

        //System.out.println("ShipY = " + Shipy + " ShipX = " + Shipx);

        if(Shipx > 675)
            Shipx = 675;
        if(Shipy > 335)
            Shipy = 335;
        if(Shipy < 25)
            Shipy = 25;
        if(Shipx < -45)
            Shipx = -45;

        //Bullet cooldown
        if(Time > bulletCooldown)
        {
            isCooldown = false;
            Time = 0;
        }


        Ship = Ships[ShipAnimationCurrent];
        shipRec = new Rectangle(Shipx,Shipy,117,53);
        //bullet and zombie collision
        for(int i=0;i<30;i++)
        {
            for(int e=0;e<50;e++)
            {
                try
                {
                    if(bullets[i].rec.intersects(zombie[e].rec))
                    {
                        bullets[i].bulletHealth--;
                        zombie[e].Health -= Bullet.bulletDamage;
                        
                        if(bullets[i].bulletHealth <= 0)
                        {
                            System.out.println("bullet be deeeeead");
                            bullets[i] = null;
                        }
                        
                        if(zombie[e].Health <= 0)
                        {
                            GameKills++;
                            switch(zombie[e].type)
                            {
                                case "A":
                                    playerStats.playerStats[5]++;
                                    break;
                                case "B":
                                    playerStats.playerStats[5] += 10;
                                    break;   
                                case "C":
                                    playerStats.playerStats[5] += 100;
                                    break;    
                            }
                            zombie[e].Die(e);
                            
                        }

                    }
                }
                catch(Exception aa){}
            }
        }

        //Checks to see if a zombie hits the ship
        for(int e=0;e<50;e++)
        {
            try
            {
                if(shipRec.intersects(zombie[e].rec))
                {
                    //System.out.println("HEALTH DOWN!");

                    if(zombie[e].type.equals("A"))
                    {
                        Health--;
                        zombie[e].Die(e);
                        GameKills++;
                    }
                    else
                    {
                        Health = 0;
                    }
                    if(Health <= 0)
                    {
                        //GAME OVER
                        isGameOver = true;
                    }


                }
            }
            catch(Exception ea){}
        }


        if((isGameOver || isWonGame))
        {
                GameOverWait++;
                if(GameOverWait > 100)
                {
                    endGame();
                }
                System.out.println("GameWait = " + GameOverWait);
        }

        ShipAnimationCurrent++;
        if(ShipAnimationCurrent > 3)
        {
            ShipAnimationCurrent = 0;
        }

    }
    
    //Different Levels for different implementation
    public void levelOne()
    {
        commonZombieCooldown = 50;
        
        if(!isWonGame && !isGameOver)
        {
            if(CurrentZombie == 25 && zombie[24] == null)
            {
                isWonGame = true;

            }
            else
            if(ZombieSpawn > commonZombieCooldown)
            {
                ZombieSpawn = 0;
                if(CurrentZombie == 24)
                {
                    zombie[CurrentZombie] = new Zombie(this,"B");
                    
                }
                else
                zombie[CurrentZombie] = new Zombie(this,"A");
                CurrentZombie++;
                
                if(CurrentZombie == 25)
                {
                    ZombieSpawn = -100000;//stops spawning
                }
            }
        }
    }
    
    public void levelTwo()
    {
        commonZombieCooldown = 40;
        String zombieType = "A";
        //Spawns special zombies
        switch(CurrentZombie)
        {
            case 5: zombieType = "B";break;
            case 10: zombieType = "B";break;
            case 36: zombieType = "B";break; 
            case 37: zombieType = "B";break;
            case 38: zombieType = "B";break;    
        }
        
        if(!isWonGame && !isGameOver)
        {
            if(CurrentZombie == 40 && zombie[36] == null && zombie[37] == null && zombie[38] == null)
            {
                isWonGame = true;

            }
            else if(ZombieSpawn > commonZombieCooldown)
            {
                ZombieSpawn = 0;
                zombie[CurrentZombie] = new Zombie(this,zombieType);
                CurrentZombie++;
                //System.out.println("New Zombie " + CurrentZombie + ":Y " + zombie[CurrentZombie].y);
            }
            
            if(CurrentZombie == 40)
            {
                ZombieSpawn = -100000;//stops spawning
            }
        }
    }
    
    public void levelThree()
    {
        commonZombieCooldown = 40;
        String zombieType = "A";
        //Spawns special zombies
        switch(CurrentZombie)
        {
            case 5: zombieType = "B";break;
            case 10: zombieType = "B";break;
            case 36: zombieType = "B";break; 
            case 37: zombieType = "B";break;
            case 38: zombieType = "B";break;    
        }
        
        if(!isWonGame && !isGameOver)
        {
            if(CurrentZombie == 40 && zombie[36] == null && zombie[37] == null && zombie[38] == null)
            {
                isWonGame = true;

            }
            else if(ZombieSpawn > commonZombieCooldown)
            {
                ZombieSpawn = 0;
                zombie[CurrentZombie] = new Zombie(this,zombieType);
                CurrentZombie++;
                //System.out.println("New Zombie " + CurrentZombie + ":Y " + zombie[CurrentZombie].y);
            }
            
            if(CurrentZombie == 40)
            {
                ZombieSpawn = -100000;//stops spawning
            }
        }
    }
    
    public void levelFour()
    {
        commonZombieCooldown = 40;
        String zombieType = "A";
        //Spawns special zombies
        switch(CurrentZombie)
        {
            case 5: zombieType = "B";break;
            case 10: zombieType = "B";break;
            case 36: zombieType = "B";break; 
            case 37: zombieType = "B";break;
            case 38: zombieType = "B";break;    
        }
        
        if(!isWonGame && !isGameOver)
        {
            if(CurrentZombie == 40 && zombie[36] == null && zombie[37] == null && zombie[38] == null)
            {
                isWonGame = true;

            }
            else if(ZombieSpawn > commonZombieCooldown)
            {
                ZombieSpawn = 0;
                zombie[CurrentZombie] = new Zombie(this,zombieType);
                CurrentZombie++;
                //System.out.println("New Zombie " + CurrentZombie + ":Y " + zombie[CurrentZombie].y);
            }
            
            if(CurrentZombie == 40)
            {
                ZombieSpawn = -100000;//stops spawning
            }
        }
    }
    
    public void levelFive()
    {
        commonZombieCooldown = 40;
        String zombieType = "A";
        //Spawns special zombies
        switch(CurrentZombie)
        {
            case 5: zombieType = "B";break;
            case 10: zombieType = "B";break;
            case 36: zombieType = "B";break; 
            case 37: zombieType = "B";break;
            case 38: zombieType = "B";break;    
        }
        
        if(!isWonGame && !isGameOver)
        {
            if(CurrentZombie == 40 && zombie[36] == null && zombie[37] == null && zombie[38] == null)
            {
                isWonGame = true;

            }
            else if(ZombieSpawn > commonZombieCooldown)
            {
                ZombieSpawn = 0;
                zombie[CurrentZombie] = new Zombie(this,zombieType);
                CurrentZombie++;
                //System.out.println("New Zombie " + CurrentZombie + ":Y " + zombie[CurrentZombie].y);
            }
            
            if(CurrentZombie == 40)
            {
                ZombieSpawn = -100000;//stops spawning
            }
        }
    }
    
    public void levelTen()
    {
        commonZombieCooldown = 35;
        String zombieType = "C";

        if(!isWonGame && !isGameOver)
        {

            if(ZombieSpawn > commonZombieCooldown)
            {
                ZombieSpawn = 0;
                zombie[CurrentZombie] = new Zombie(this,zombieType);
                CurrentZombie++;
                //System.out.println("New Zombie " + CurrentZombie + ":Y " + zombie[CurrentZombie].y);
            }
            
            if(zombie[CurrentZombie - 1] == null)
            {
                isWonGame = true;

            }
            
            if(CurrentZombie == 1)
            {
                ZombieSpawn = -100000;//stops spawning
            }
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
        SpaceZombies frame = new SpaceZombies();
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
                //if(recBack.contains(mx,my))
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {

    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();
        //System.out.println(keyCode);
        switch( keyCode ) 
        { 
            case KeyEvent.VK_UP:
                isUp = true;
                break;
            
            case KeyEvent.VK_DOWN:
                isDown = true;
                break;
            
            case KeyEvent.VK_LEFT:
                isLeft = true;
                break;
            
            case KeyEvent.VK_RIGHT :
                isRight = true;
                break;
                
            case KeyEvent.VK_SPACE :
                isSpace = true;
                break;
            case KeyEvent.VK_NUMPAD1 :
                fps++;
                break;
            case KeyEvent.VK_NUMPAD2 :
                fps--;
                break;
            case KeyEvent.VK_ESCAPE :
                endGame();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        int keyCode = e.getKeyCode();
        switch(keyCode) 
        { 
            case KeyEvent.VK_UP:
                isUp = false;
                break;
            
            case KeyEvent.VK_DOWN:
                isDown = false;
                break;
            
            case KeyEvent.VK_LEFT:
                isLeft = false;
                break;
            
            case KeyEvent.VK_RIGHT :
                isRight = false;
                break;
                
            case KeyEvent.VK_SPACE :
                isSpace = false;
                break;
        }
    }
    
    public void endGame()
    {
        this.EndProgram = true;
        GameMenu menu = new GameMenu();
        this.dispose();
    }
}
