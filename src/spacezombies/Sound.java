/**
 * This class is designed to play sound by using a AudioClip object
 */
package spacezombies;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class Sound 
{
    private static AudioClip song;
    private static URL songPath;
    
    Sound(String filename)
    {
        try
        {
            songPath = SpaceZombies.class.getResource(filename);
            song = Applet.newAudioClip(songPath);
        }
        catch(Exception er)
        {
            System.out.println("Problem loading the sound: " + filename);
        }
    }
    
    public void Play()
    {
        song.play();
    }
    
}
