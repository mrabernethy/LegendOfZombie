package Source;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class ZombieExplosionParticle extends Particle
{
    public ZombieExplosionParticle(int x, int y, float age)
    {
        super(x, y, age);
        m_scale = 1;
        m_rotationAngle = 0;
    }
    
    public ZombieExplosionParticle(float px, float py, float vx, float vy, float ax, float ay, float age)
    {
        super(px, py, vx, vy, ax, ay, age);
        m_scale = 1;
        m_rotationAngle = 0;
    }
    
    @Override
    public void process()
    {
        // rotate the particle
        m_rotationAngle += Math.PI/2;
        
        super.process();
    }
}
