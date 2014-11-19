package Source;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class BloodParticleEmitter extends ParticleEmitter
{
    private float particleDirection;
    private int count;
    
    public BloodParticleEmitter(int x, int y, SpriteStore spriteStore, float angle)
    {
        super(x, y, spriteStore);
        m_particleSprite = m_spriteStore.getSprite("Assets/bloodparticle2.png");
        particleDirection = (float) (angle + Math.PI);
        count = (int) Math.random() * 100 + 1;
    }
    
    public void spawnParticles()
    {
        if (count > 0)
        {
            count--;
            double r = Math.random() * Math.PI - Math.PI;
            float a = (float) (particleDirection + r);
            float vx = (float) (2 * Math.cos(a));
            float vy = (float) (2 * Math.sin(a));
            m_particles.add(new BloodParticle(m_x, m_y, vx, vy, -vx/10, -vy/10, 15.0f));
            spawnParticles();
        }
    }
}
