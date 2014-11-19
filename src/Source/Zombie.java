package Source;

/**
 *
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class Zombie extends Entity
{
    private Player m_player;
    private AIState m_state;
    private Vector2D m_destination;
    private boolean m_isHorde, m_couldSee;
    private Sprite m_sprite1, m_sprite2;
    private float toggleSpriteTimer;

    public Zombie(Sprite sprite1, Sprite sprite2, int x, int y) 
    {
        super(sprite1, x, y);
        m_isHorde = false;
        m_sprite1 = sprite1;
        m_sprite2 = sprite2;
        // get the angle towards the player
        float rot = m_vector.getAngle(new Vector2D(1504, 864));
        // set the sprite rotation to the angle plus the sprite initial position
        setRotation((float) (rot + Math.PI/2));
        m_state = AIState.GUARD;
    }
    
    
    
    @Override
    public void process(float deltaTime)
    {
        
        
        
        toggleSpriteTimer += deltaTime;
        if (toggleSpriteTimer > 0.080)
        {
            toggleSprite();
            toggleSpriteTimer -= 0.080;
        }
        switch (m_state)
        {
            case GUARD: 
                if (canSeePlayer())
                {
                    setState(AIState.CHASE);
                }
                else
                {
                    setHorizontalVelocity(0);
                    setVerticalVelocity(0);
                }
            break;
            case PATROL: // not implemented
            case CHASE: 
                if (canSeePlayer() || m_isHorde)
                {
                    // set the destination to player vector position
                    setDestination(m_player.getVector());
                    // get the angle towards the player
                    float angle = m_vector.getAngle(m_destination);
                    // set the sprite rotation to the angle plus the sprite initial position
                    setRotation((float) (angle + Math.PI/2));
                    Vector2D v = m_vector.getUnit(m_destination);
                    setHorizontalVelocity(v.getX());
                    setVerticalVelocity(v.getY());
                }
                else
                {
                    setState(AIState.GUARD);  // set up patrol later
                }
        }
        

        super.process(deltaTime);
        
    }
    
    private void toggleSprite()
    {
        if (m_EntitySprite == m_sprite1)
        {
            m_EntitySprite = m_sprite2;
        }
        else if (m_EntitySprite == m_sprite2)
        {
            m_EntitySprite = m_sprite1;
        }
    }
    
    public void draw(BackBuffer b)
    {
        super.draw(b);
    }
    
    public void setPlayer(Player player)
    {
        m_player = player;
    }
    
    public Player getPlayer()
    {
        return m_player;
    }
    
    public void setDestination(Vector2D destination)
    {
        m_destination = destination;
    }
    
    public Vector2D getDestination()
    {
        return m_destination;
    }

    public void setState(AIState state)
    {
        m_state = state;
    }
    
    /**
     * Returns true if zombie can see the player
     * @return 
     */
    private boolean canSeePlayer() 
    {
        boolean canSee = false;
        // get the angle towards the player
        float angle = m_vector.getAngle(m_player.getVector());
        float diff =  (float) Math.abs(angle - m_rotationAngle+Math.PI/2);
        // get the distance to the player
        float distance = m_vector.getDistance(m_player.getVector());
        
        int sight;
        if (m_couldSee)
        {
            sight = 300;
        }
        else
        {
            sight = 200;
        }
        
        if ((diff >= 1.75*Math.PI || diff <= 0.25*Math.PI) && distance < sight)
        {
            canSee = true;
        }
        
        m_couldSee = canSee;
        
        return canSee;
        
    }
    
    public void setIsHorde(boolean isHorde)
    {
        m_isHorde = isHorde;
    }
    
    @Override
    public void collidedWith(Entity e) 
    {
        if (e instanceof Tile)
        {
            Tile t = (Tile) e;
            if (t.getTileType() == TileType.TREE)
            {
                float angle = m_vector.getAngle(e.getVector());
                float l = m_EntitySprite.getWidth()/2 + e.getSprite().getWidth()/2 + 1;
                float x = (float) (l*Math.cos(angle));
                float y = (float) (l*Math.sin(angle));
                m_positionX = e.getVector().getX() - x - m_EntitySprite.getWidth()/2;
                m_positionY = e.getVector().getY() - y - m_EntitySprite.getHeight()/2;
            }

        }
        if (e instanceof Zombie)
        {
//            float angle = m_vector.getAngle(e.getVector());
//            float l = m_EntitySprite.getWidth()/2 + e.getSprite().getWidth()/2 + 1;
//            float x = (float) (l*Math.cos(angle));
//            float y = (float) (l*Math.sin(angle));
//            float z1x = m_vector.getX();
//            float z1y = m_vector.getY();
//            m_positionX = e.getVector().getX() - x - m_EntitySprite.getWidth()/2;
//            m_positionY = e.getVector().getY() - y - m_EntitySprite.getHeight()/2;
//            e.setPositionX(z1x + x - m_EntitySprite.getWidth()/2);
//            e.setPositionY(z1y + y - m_EntitySprite.getHeight()/2);
            
//            if (isCollidingWith(e))
//            {
//                float angle2 = e.getVector().getAngle(m_vector);
//                float l2 = m_EntitySprite.getWidth()/2 + e.getSprite().getWidth()/2 + 1;
//                float x2 = (float) (l2*Math.cos(angle2));
//                float y2 = (float) (l2*Math.sin(angle2));
//                e.setPositionX(m_vector.getX() - x2 - e.getSprite().getWidth()/2);
//                e.setPositionY(m_vector.getY() - y2 - e.getSprite().getHeight()/2);
//            }
        }
    }
}
