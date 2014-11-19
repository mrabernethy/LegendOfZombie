package Source;

import java.awt.Rectangle;

/**
 *
 * 
 */
public class Explosion extends Entity
{
    
    public Explosion(Sprite sprite, int x, int y)
    {
        super(sprite, x, y);
    }
    
    @Override
    public void draw(BackBuffer b)
    {
        AnimatedSprite animSprite = (AnimatedSprite) m_EntitySprite;
        animSprite.draw(b, (int)m_positionX, (int)m_positionY);
    }
    
    public void process(float deltaTime)
    {
        AnimatedSprite animSprite = (AnimatedSprite) m_EntitySprite;
        animSprite.process(deltaTime);
        if (!animSprite.isAnimating())
        {
            setDead(true);
        }
    }
    
    public boolean isCollidingWith(Entity e)
    {
        // Generic Entity Collision routine.
        boolean colliding = false;
        // Does this object collide with the e object?
        // Create a Java Rectangle for each entity (this and e).
        Rectangle otherEntityRect = new Rectangle();
        Rectangle thisEntityRect = new Rectangle();
        // Set bounds for each entity.
        otherEntityRect.setBounds((int)e.getPositionX(), (int)e.getPositionY(),
                e.m_EntitySprite.getWidth(), e.m_EntitySprite.getHeight());   
        thisEntityRect.setBounds((int)getPositionX(), (int)getPositionY(),
                64, 64);
        // Call intersects method.
        if (thisEntityRect.intersects(otherEntityRect))
        {
            colliding = true;
        }
        // Return result of collision.
        return (colliding);
    }

    @Override
    public void collidedWith(Entity e) 
    {
        if (e instanceof Tile)
        {
            e.setDead(true);
        }
        if (e instanceof Zombie)
        {
            e.setDead(true);
        }
    }
}
