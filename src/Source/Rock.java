package Source;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class Rock extends Projectile
{

    public Rock(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    public void process(float deltaTime)
    {
        super.process(deltaTime);
        if (m_elapsedTime > 0.5)
        {
            setDead(true);
        }
    }
    
    @Override
    public void collidedWith(Entity e)
    {
        if (e instanceof Zombie)
        {
            e.setDead(true);
            setDead(true);
        }
    }
}
