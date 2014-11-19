package Source;

import java.util.ArrayList;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public abstract class ParticleEmitter 
{
    protected ArrayList<Particle> m_particles;
    protected Sprite m_particleSprite;
    protected SpriteStore m_spriteStore;
    protected int m_x, m_y;
    
    public ParticleEmitter(int x, int y, SpriteStore spriteStore)
    {
        m_spriteStore = spriteStore;
        m_particles = new ArrayList<>();
        m_x = x;
        m_y = y;
    }
    
    public void processParticles(float deltaTime)
    {
        ArrayList<Particle> deadParticles = new ArrayList<>();
        for (int i=0; i<m_particles.size(); i++)
        {
            Particle p = m_particles.get(i);
            if (p.getAge()>0)
            {
                p.process();
            }
            else
            {
                deadParticles.add(p);
            }
        }
        m_particles.removeAll(deadParticles);
        
    }
    
    public void drawParticles(BackBuffer b)
    {
        for (int i=0; i<m_particles.size(); i++)
        {
            Particle p = m_particles.get(i);
            if (p != null)
            {
                p.draw(m_particleSprite, b);
            }
        }
        
    }
    
    public int getNumParticles()
    {
        return m_particles.size();
    }  
}
