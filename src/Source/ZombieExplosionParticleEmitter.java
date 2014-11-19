package Source;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class ZombieExplosionParticleEmitter extends ParticleEmitter
{
    public ZombieExplosionParticleEmitter(int x, int y, SpriteStore spriteStore)
    {
        super(x, y, spriteStore);
        m_particleSprite = m_spriteStore.getSprite("Assets/alienfragment.png");
    }
    
    public void spawnParticles()
    {
        m_particles.add(new ZombieExplosionParticle(m_x, m_y, 2.5f, 0, 0, 0.5f, 15.0f));
        m_particles.add(new ZombieExplosionParticle(m_x, m_y, -2.5f, 0, 0, 0.5f, 15.0f));
        m_particles.add(new ZombieExplosionParticle(m_x, m_y, 0, 2.5f, 0, 0.5f, 15.0f));
        m_particles.add(new ZombieExplosionParticle(m_x, m_y, 0, -2.5f, 0, 0.5f, 15.0f));
        
        m_particles.add(new ZombieExplosionParticle(m_x, m_y, 2, 2, 0, 0.5f, 10.0f));
        m_particles.add(new ZombieExplosionParticle(m_x, m_y, -2, 2, 0, 0.5f, 10.0f));
        m_particles.add(new ZombieExplosionParticle(m_x, m_y, 2, -2, 0, 0.5f, 10.0f));
        m_particles.add(new ZombieExplosionParticle(m_x, m_y, -2, -2, 0, 0.5f, 10.0f));
        
    }
}
