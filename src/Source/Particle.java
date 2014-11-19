package Source;

import java.awt.geom.AffineTransform;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class Particle 
{
    protected Vector2D m_position, m_velocity, m_acceleration;
    protected float m_age;
    protected float m_scale;
    protected float m_rotationAngle;
           
    public Particle(float x, float y, float age)
    {
        m_position = new Vector2D(x, y);
        m_velocity = new Vector2D();
        m_acceleration = new Vector2D();
        m_age = age;
    }
    
    public Particle(float px, float py, float vx, float vy, float ax, float ay, float age)
    {
        m_position = new Vector2D(px, py);
        m_velocity = new Vector2D(vx, vy);
        m_acceleration = new Vector2D(ax, ay);
        
        m_age = age;
    }
    
    public void process()
    {
        // move the X position by the velocity
        m_position.setX(m_position.getX() + m_velocity.getX());
        // move the Y position by the velocity
        m_position.setY(m_position.getY() + m_velocity.getY());
        // move the X velocity by the acceleration
        m_velocity.setX(m_velocity.getX() + m_acceleration.getX());
        // move the Y velocity by the acceleration
        m_velocity.setY(m_velocity.getY() + m_acceleration.getY());
        // age the particle towards zero
        m_age--;
        
    }
        
    public void draw(Sprite particleSprite, BackBuffer b)
    {
        if (m_age>0)
        {
            AffineTransform t = new AffineTransform();
            t.scale(m_scale, m_scale);
            t.translate(m_position.getX(), m_position.getY());
            t.rotate(m_rotationAngle);
            particleSprite.draw(b, t);
            
        }
    }
    
    public void setAge(float age)
    {
        m_age = age;
    }
    
    public void setPosition(int x, int y)
    {
        m_position.setX(x);
        m_position.setY(y);
    }
    
    public void setVelocity(int x, int y)
    {
        m_velocity.setX(x);
        m_velocity.setY(y);
    }
    
    public void setAcceleration(int x, int y)
    {
        m_acceleration.setX(x);
        m_acceleration.setY(y);
    }
    
    public float getAge()
    {
        return m_age;
    }
    
    public Vector2D getPosition()
    {
        return m_position;
    }
    
    public Vector2D getVelocity()
    {
        return m_velocity;
    }
    
    public Vector2D getAcceleration()
    {
        return m_acceleration;
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
}
