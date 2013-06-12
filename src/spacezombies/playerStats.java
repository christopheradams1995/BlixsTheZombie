
package spacezombies;

import java.io.*;

/**
 * @author Chris
 * Holds all the saved player stats. This class will load and save the user's stats
 */
public class playerStats 
{
    //player stats -----------------
    // [0] - Ship
    // [1] - Power
    // [2] - Speed
    // [3] - Luck
    // [4] - Level
    // [5] - Kills
    // [6] - LevelPoints
    // [7] - Current level unclocked
    public static int [] playerStats = new int[8];
    
    public static boolean isSaving = false;
    public static boolean isLoading = false;
    //------------------------------
    public static void LevelCheck()
    {

        if(playerStats[5] >= 10 && playerStats[4] == 0)
        {
            playerStats[4] = 1;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 25 && playerStats[4] == 1)
        {
            playerStats[4] = 2;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 50 && playerStats[4] == 2)
        {
            playerStats[4] = 3;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 75 && playerStats[4] == 3)
        {
            playerStats[4] = 4;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 100 && playerStats[4] == 4)
        {
            playerStats[4] = 5;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 150 && playerStats[4] == 5)
        {
            playerStats[4] = 6;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 200 && playerStats[4] == 6)
        {
            playerStats[4] = 7;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 250 && playerStats[4] == 7)
        {
            playerStats[4] = 8;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 300 && playerStats[4] == 8)
        {
            playerStats[4] = 9;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 350 && playerStats[4] == 9)
        {
            playerStats[4] = 10;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 400 && playerStats[4] == 10)
        {
            playerStats[4] = 11;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 500 && playerStats[4] == 11)
        {
            playerStats[4] = 12;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 600 && playerStats[4] == 12)
        {
            playerStats[4] = 13;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 700 && playerStats[4] == 13)
        {
            playerStats[4] = 14;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 800 && playerStats[4] == 14)
        {
            playerStats[4] = 15;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 900 && playerStats[4] == 15)
        {
            playerStats[4] = 16;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 1000 && playerStats[4] == 16)
        {
            playerStats[4] = 17;
            playerStats[6]++;
        }
        else if(playerStats[5] >= 2000 && playerStats[4] == 16)
        {
            playerStats[4] = 18;
            playerStats[6] += 20;
        }
    }
    // Method to save the player stats
    public static void saveStats()
    {
        try
        {
            if(!new File("Save.dat").exists())
            {
                File file = new File("Save.dat");
                file.createNewFile();
                System.out.println("New File has been created");
            }
            System.out.println("Saving");
            FileOutputStream fos = new FileOutputStream("Save.dat");
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(playerStats);
            }
            isSaving = false;
            System.out.println("Saving Complete");
        }catch(Exception er) { er.printStackTrace(); isSaving = false;}
    }
    //loads the player stats
    public static void loadStats()
    {
        try
        {
            
            if(new File("Save.dat").exists())
            {
                System.out.println("Loading");
                FileInputStream fis = new FileInputStream("Save.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                playerStats = (int[]) ois.readObject();
                ois.close();
                System.out.println("Loading Complete");
                
            }
            
        }catch(Exception er) { er.printStackTrace(); }
    }
    
}
