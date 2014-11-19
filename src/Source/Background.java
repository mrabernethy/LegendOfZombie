package Source;

/**
 *
 * @author Michael Abernethy
 */
public class Background extends Entity
{

    public Background(Sprite sprite, int x, int y) {
        super(sprite, x, y);
    }

    @Override
    public void collidedWith(Entity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
