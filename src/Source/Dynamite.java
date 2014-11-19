package Source;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class Dynamite extends Projectile
{

    public Dynamite(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }
    
    @Override
    public void collidedWith(Entity e) {
    }
    
}
