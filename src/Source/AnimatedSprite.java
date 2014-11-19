package Source;

import java.util.ArrayList;

/**
 * 
 * 
 */
public class AnimatedSprite extends Sprite
{
    // Add a field, the container for frame coordinates.
    private ArrayList<Integer> m_frameCoordinates;
    
    private float m_frameSpeed;
    private int m_frameWidth;
    
    private float m_timeElapsed;
    private int m_currentFrame;
    
    private boolean m_paused;
    private boolean m_loop;
    private boolean m_animating;
        
    public AnimatedSprite(String filename)
    {
        super(filename);
        
        m_frameWidth = 0;
        m_frameSpeed = 0;
        
        m_loop = false;
        m_paused = false;
        m_animating = true;
        
        startAnimating();
        
        // Instantiate the frame coordinates container.
        m_frameCoordinates = new ArrayList<Integer>();
    }
    
    public void addFrame(int x)
    {
        // Add the x coordinate to the frame coordinate container.
        m_frameCoordinates.add(x);
    }
    
    public void process(float dt)
    {
        // If not paused...
        if(!isPaused() && isAnimating())
        {
            // Count the time elapsed.
            m_timeElapsed += dt;
            // If the time elapsed is greater than the frame speed.
            if (m_timeElapsed > m_frameSpeed)
            {
                // Move to the next frame.
                m_currentFrame++;
                // Reset the time elapsed counter.
                m_timeElapsed = 0;
            }
            // If the current frame is greater than the number 
            // of frames in this animation...
            if (m_currentFrame >= m_frameCoordinates.size())
            {
                // Reset to the first frame.
                m_currentFrame = 0;
            
                // Stop the animation if it is not looping...
                if (!isLooping())
                {
                    //stop the animation
                    m_animating = false;
                }
            }
        }
    }
    
    public void draw(BackBuffer b, int x, int y)
    {
        // Draw the particular frame into the backbuffer.
        b.drawSpriteFrame(x, y, m_frameCoordinates.get(m_currentFrame), 0, m_frameWidth, m_frameWidth, m_image);
    }
    
    public void setFrameSpeed(float f)
    {
        m_frameSpeed = f;
    }
    
    public void setFrameWidth(int w)
    {
        m_frameWidth = w;
    }
    
    public void pause()
    {
        m_paused = !m_paused;
    }
       
    public boolean isPaused()
    {
        return (m_paused);
    }
    
    public boolean isAnimating()
    {
        return (m_animating);
    }
        
    public void startAnimating()
    {
        m_animating = true;
        
        m_timeElapsed = 0;
        m_currentFrame = 0;
    }
    
    public boolean isLooping()
    {
        return (m_loop);
    }

    public void setLooping(boolean b)
    {
        m_loop = b;
    }
}
