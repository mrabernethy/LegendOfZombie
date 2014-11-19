package Source;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * 
 * 
 */
public class Sprite
{
    //Member data:
    protected Image m_image;
    
    //Member functions:
    public Sprite(String filename)
    {
        BufferedImage sourceImage = null;
        
        try
        {
            URL url = this.getClass().getClassLoader().getResource(filename);
            
            if (url == null)
            {
                // Log: File not found.
                GameLogger.getInstance().logMessage(LogLevel.ERROR, "Sprite", "File not found.");
                // Throw Exceptions?
                throw new NullPointerException(filename + " not found");
            }
            
            sourceImage = ImageIO.read(url);
        }
        catch (IOException e)
        {
            // TODO: Log: Loading Failed.
            GameLogger.getInstance().logMessage(LogLevel.ERROR, "Sprite", "Loading failed.");
        }
        
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);
        
        image.getGraphics().drawImage(sourceImage, 0, 0, null);
        
        m_image = image;
    }
    
    public int getWidth()
    {
        return (m_image.getWidth(null));
    }
    
    public int getHeight()
    {
        return (m_image.getHeight(null));
    }
    
    public void draw(BackBuffer b, AffineTransform t)
    {
        b.draw(m_image, t);
    }
}
