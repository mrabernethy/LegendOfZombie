package Source;

import static Source.Direction.DOWN;
import static Source.Direction.LEFT;
import static Source.Direction.RIGHT;
import static Source.Direction.UP;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class Player extends Entity
{
    private int m_lives, m_numRocks, m_numDynamite;

    public Player(Sprite sprite, int x, int y) {
        super(sprite, x, y);
        
        m_lives = 10;
        m_numDynamite = 10;
        m_numRocks = 10;
    }
    
    @Override
    public void process(float deltaTime)
    {
        // Rotating sprite direction according to movement direction
        switch(m_direction)
        {
            case LEFT: setRotation((float)-Math.PI/2);
                break;
            case RIGHT: setRotation((float)Math.PI/2);
                break;
            case UP: setRotation(0);
                break;
            case DOWN: setRotation((float)Math.PI);
                break;
        }
        
        super.process(deltaTime);
    }

    @Override
    public void collidedWith(Entity e)
    {
        if (e instanceof Tile)
        {
            Tile t = (Tile) e;
            TileType type = t.getTileType();
            if (type == TileType.SAFEHOUSE)
            {
                Game.getInstance().setGameState(GameState.WON);
            }
            else if (type == TileType.TREE)
            {
                float angle = m_vector.getAngle(e.getVector());
                float l = m_EntitySprite.getWidth()/2 + e.getSprite().getWidth()/2 + 2;
                float x = (float) (l*Math.cos(angle));
                float y = (float) (l*Math.sin(angle));
                m_positionX = e.getVector().getX() - x - m_EntitySprite.getWidth()/2;
                m_positionY = e.getVector().getY() - y - m_EntitySprite.getHeight()/2;
            }
            else if (type == TileType.WATER)
            {
                float posX = e.getPositionX();
                float posY = e.getPositionY();
                switch (m_direction)
                {
                    case LEFT: m_positionX = posX+34;
                        break;
                    case RIGHT: m_positionX = posX-34;
                        break;
                    case UP: m_positionY = posY + 34;
                        break;
                    case DOWN: m_positionY = posY - 34;
                        break;
                }
            }
        }
        
        else if (e instanceof Zombie)
        {
            float angle = m_vector.getAngle(e.getVector());
            float l = m_EntitySprite.getWidth()/2 + e.getSprite().getWidth()/2;
            float x = (float) (l*Math.cos(angle))*2;
            float y = (float) (l*Math.sin(angle))*2;
            m_positionX = e.getVector().getX() - x - m_EntitySprite.getWidth()/2;
            m_positionY = e.getVector().getY() - y - m_EntitySprite.getHeight()/2;
            
            if (m_lives <= 1)
            {
                Game.getInstance().setGameState(GameState.GAMEOVER);
            }
            else if (m_lives > 1)
            {
                m_lives -= 1;
            }
        }
        else if (e instanceof Explosion)
        {
            Game.getInstance().setGameState(GameState.GAMEOVER);
        }
    }
    
    public int getLives()
    {
        return m_lives;
    }
    
    public int getNumDynamite()
    {
        return m_numDynamite;
    }
    
    public void setNumDynamite(int numDynamite)
    {
        m_numDynamite = numDynamite;
    }
    
    public int getNumRocks()
    {
        return m_numRocks;
    }
    
    public void setNumRocks(int numRocks)
    {
        m_numRocks = numRocks;
    }
}
