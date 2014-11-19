package Source;

/**
 *
 * @author Michael Abernethy
 */
public class PlayerDirection
{
    private Direction spriteDirection, currentDirection, previousDirection1, previousDirection2;

    
    public PlayerDirection()
    {
        currentDirection = null;
        previousDirection1 = null;
        previousDirection2 = null;
    }
    
    public void setDirection(Direction direction)
    {
        previousDirection2 = previousDirection1;
        previousDirection1 = currentDirection;
        currentDirection = direction;
        spriteDirection = direction;
    }
    
    public void removeDirection(Direction direction)
    {
        if (direction.equals(currentDirection))
        {
            currentDirection = previousDirection1;
            previousDirection1 = previousDirection2;
            previousDirection2 = null;
        }
        else if (direction.equals(previousDirection1))
        {
            previousDirection1 = previousDirection2;
            previousDirection2 = null;
        }
        else if (direction.equals(previousDirection2))
        {
            previousDirection2 = null;
        }
    }
    
    public Direction getCurrentDirection()
    {
        return currentDirection;
    }
    
    public Direction getSpriteDirection()
    {
        return spriteDirection;
    }

}
