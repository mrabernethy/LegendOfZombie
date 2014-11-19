package Source;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class BloodParticle extends Particle
{
    public BloodParticle(float px, float py, float vx, float vy, float ax, float ay, float age)
    {
        super(px, py, vx, vy, ax, ay, age);
        m_scale = 1;
        m_rotationAngle = 0;
    }
}
