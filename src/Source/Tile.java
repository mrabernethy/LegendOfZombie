package Source;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class Tile extends Entity
{
    private TileType m_type;

    public Tile(Sprite sprite, int x, int y, TileType type)
    {
        super(sprite, x, y);
        m_type = type;
    }
    
    public void process(float deltaTime)
    {
        
    }
    
    public TileType getTileType()
    {
        return m_type;
    }

    @Override
    public void collidedWith(Entity e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
