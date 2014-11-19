package Source;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class Projectile extends Entity
{
    protected float distance;
    private boolean m_exploded;
    

    public Projectile(Sprite sprite, int x, int y) {
        super(sprite, x, y);
        m_exploded = false;
    }
    
    public void process(float deltaTime)
    {
        if (distance > 0)
        {
            if (m_direction.equals(Direction.LEFT))
            {
                setHorizontalVelocity(-10);
            }
            else if (m_direction.equals(Direction.RIGHT))
            {
                setHorizontalVelocity(10);
            }
            else if (m_direction.equals(Direction.UP))
            {
                setVerticalVelocity(-10);
            }
            else if (m_direction.equals(Direction.DOWN))
            {
                setVerticalVelocity(10);
            }
            super.process(deltaTime);
            distance -= deltaTime;
        }
        else if (distance <=0)
        {
            m_elapsedTime += deltaTime;
        }
        
    }
    
    public void setDistance(float distance)
    {
        this.distance = distance;
    }
    

    
    public float getElapsedTime()
    {
        return m_elapsedTime;
    }
    
    public void setExploded()
    {
        m_exploded = true;
        setDead(true);
    }
    
    public boolean hasExploded()
    {
        return m_exploded;
    }

    @Override
    public void collidedWith(Entity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
