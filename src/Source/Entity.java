package Source;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 * 
 * 
 */
public abstract class Entity
{
    // used with deltaTime processing
    protected float m_elapsedTime;
    private float m_frameSpeed;
    
    protected float m_positionX;
    protected float m_positionY;
    
    protected Vector2D m_vector;
    
    protected float m_velocityX;
    protected float m_velocityY;
    
    protected float m_scale = 1;
    protected float m_rotationAngle;
    
    protected Sprite m_EntitySprite;
    
    protected Direction m_direction = Direction.UP;
    
    protected boolean m_dead;
    
    public Entity(Sprite sprite, int x, int y)
    {
        m_EntitySprite = sprite;
        
        m_positionX = x;
        m_positionY = y;
        
        // Create a vector at the centre of the sprite
        m_vector = new Vector2D(m_positionX+m_EntitySprite.getWidth()/2, m_positionY+m_EntitySprite.getHeight()/2);
        
        m_dead = false;
    }
    
    public void process(float deltatime)
    {
        // Generic position update, based upon velocity (and time).
        m_positionX += m_velocityX;
        m_positionY += m_velocityY;
        // Boundary checking and position capping.   
        if (m_positionX <= 32)
        {
            m_positionX = 34;
            m_velocityX = 0;
        }
        else if (m_positionX >= (1568 - m_EntitySprite.getWidth()))
        {
            m_positionX = (1566 - m_EntitySprite.getWidth());
            m_velocityX = 0;
        }    
        if (m_positionY <= 32)
        {
            m_positionY = 34;
            m_velocityY = 0;
        }
        else if (m_positionY >= (928 - m_EntitySprite.getWidth()))
        {
            m_positionY = (926 - m_EntitySprite.getWidth());
            m_velocityY = 0;
        }
        
        // Update the vector
        m_vector.setX(m_positionX+m_EntitySprite.getWidth()/2);
        m_vector.setY(m_positionY+m_EntitySprite.getHeight()/2);
    }
    
    public void draw(BackBuffer b)
    {
        if (!m_dead)
        {

            AffineTransform t = new AffineTransform();
            t.scale(m_scale, m_scale);
            t.translate(m_positionX, m_positionY);
            // could just use the vector points???
            t.rotate(m_rotationAngle, m_EntitySprite.getWidth()/2,
                    m_EntitySprite.getHeight()/2);
            m_EntitySprite.draw(b, t);
        }
    }
   
    public float getPositionX()
    {
        return (m_positionX);
    }
    
    public void setPositionX(float x)
    {
        m_positionX = x;
    }
    
    public float getPositionY()
    {
        return (m_positionY);
    }
    
    public void setPositionY(float y)
    {
        m_positionY = y;
    }
    
    public Vector2D getVector()
    {
        return m_vector;
    }

    public float getHorizontalVelocity()
    {
        return (m_velocityX);
    }
    
    public float getVerticalVelocity()
    {
        return (m_velocityY);
    }
    
    public void setHorizontalVelocity(float x)
    {
        m_velocityX = x;
    }
    
    public void setVerticalVelocity(float y)
    {
        m_velocityY = y;
    }
    
    public boolean isDead()
    {
        return (m_dead);
    }
    
    public void setDead(boolean b)
    {
        m_dead = b;
    }
    
    public Sprite getSprite()
    {
        return m_EntitySprite;
    }
    
    public boolean isCollidingWith(Entity e)
    {
        // Generic Entity Collision routine.
        boolean colliding = false;
        // Does this object collide with the e object?
        Vector2D vec = new Vector2D(m_vector.getX()-e.getVector().getX(), 
                m_vector.getY()-e.getVector().getY());
        float l = vec.getLength();

        // Call intersects method.
        if (l - (m_EntitySprite.getWidth()/2 + e.getSprite().getWidth()/2) <= 0)              
        {
            colliding = true;
        }
        // Return result of collision.
        return (colliding);
    }
    
    public void setScale(float scale)
    {
        m_scale = scale;
    }
    
    public float getScale()
    {
        return m_scale;
    }
    
    public void setRotation(float rotationAngle)
    {
        m_rotationAngle = rotationAngle;
    }
    
    public float getRotation()
    {
        return m_rotationAngle;
    }
    
    public void setDirection(Direction direction)
    {
        m_direction = direction;
    }
    
    public Direction getDirection()
    {
        return m_direction;
    }
    
    public abstract void collidedWith(Entity e);
}
