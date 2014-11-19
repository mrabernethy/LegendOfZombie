package Source;

import java.util.HashMap;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

/**
 * 
 * 
 */
public class SpriteStore
{
    //Member data:
    private HashMap m_spriteStoreContainer;
    
    //Member functions:
    public SpriteStore()
    {
        m_spriteStoreContainer = new HashMap();
        
        // Log State Change: "SpriteStore Created"
        GameLogger.getInstance().logMessage(LogLevel.INFO, "SpriteStore", "SpriteStore Created");
    }
    
    public Sprite getSprite(String filename)
    {
        Sprite toReturn = null;

        if (m_spriteStoreContainer.get(filename) != null)
        {    
            // Sprite already loaded...
            toReturn = ((Sprite) m_spriteStoreContainer.get(filename));
        }
        else
        {
            // New sprite to load...
            toReturn = new Sprite(filename);

            m_spriteStoreContainer.put(filename, toReturn);
            
            // Log State Change: "Sprite Loaded"
            GameLogger.getInstance().logMessage(LogLevel.INFO, "SpriteStore", "Sprite Loaded");
        }
        
        return (toReturn);
    }
}
