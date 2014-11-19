package Source;

/**
 *
 * @author Michael Abernethy
 */
public class EntityMapPosition 
{
    private int x, y;
    private String entityType;
    
    public EntityMapPosition(char c, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.entityType = Character.toString(c);
    }
    
    public String getEntityType()
    {
        return entityType;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
}
