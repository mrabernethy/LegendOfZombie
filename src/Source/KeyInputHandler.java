package Source;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * 
 */
public class KeyInputHandler extends KeyAdapter
{
    //temp value for a timer
    private int timer = 2;
    
    private static final int ROCK = 1;
    private static final int DYNAMITE = 2;
    
    private PlayerDirection m_direction;
    
    private boolean leftArrowPressed, rightArrowPressed, downArrowPressed,
            upArrowPressed,spacebarPressed;
    private int selectedProjectile;
    private float spacePressTime, spaceReleaseTime;
    
    /**
     * Constructor for the KeyInputHandler.
     * 
     * Sets all fields to false.
     */
    public KeyInputHandler()
    {
        leftArrowPressed = false;
        rightArrowPressed = false;
        downArrowPressed = false;
        upArrowPressed = false;
        
        m_direction = new PlayerDirection();
        
        spacebarPressed = false;
        selectedProjectile = ROCK;
    }
    
    /**
     * Class to handle key pressed events.
     * 
     * @param e the pressed key 
     */
    public void keyPressed(KeyEvent e)
    {
        if (Game.getInstance().getGameState() == GameState.MENU)
        {
            if (e.getKeyCode() == KeyEvent.VK_1)
            {
                Game.getInstance().setGameState(GameState.PLAYING);
            }
            else if (e.getKeyCode() == KeyEvent.VK_2)
            {
                Game.getInstance().setGameState(GameState.CONTROLS);
            }
        }
        else if (Game.getInstance().getGameState() == GameState.CONTROLS)
        {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                Game.getInstance().setGameState(GameState.MENU);
            }
        }
        else if (Game.getInstance().getGameState() == GameState.PLAYING)
        {
            // Tell the game to move the player sprite left...
            if (e.getKeyCode() == KeyEvent.VK_LEFT)
            {
                // Log State Change: "Left Arrow Pressed"
                if (!leftArrowPressed)
                {
                    GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Left Arrow Pressed");
                    leftArrowPressed = true;
                    m_direction.setDirection(Direction.LEFT);
                }
            }

            // Tell the game to move the player sprite right...
            if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                // Log State Change: "Right Arrow Pressed"
                if (!rightArrowPressed)
                {
                    GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Right Arrow Pressed");
                    rightArrowPressed = true;
                    m_direction.setDirection(Direction.RIGHT);
                }
            }

            // Tell the game to move the player sprite down...
            if (e.getKeyCode() == KeyEvent.VK_DOWN)
            {
                // Log State Change: "Down Arrow Pressed"
                if (!downArrowPressed)
                {
                    GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Down Arrow Pressed");
                    downArrowPressed = true;
                    m_direction.setDirection(Direction.DOWN);
                }
            }

            // Tell the game to move the player sprite up...
            if (e.getKeyCode() == KeyEvent.VK_UP)
            {
                // Log State Change: "Up Arrow Pressed"
                if (!upArrowPressed)
                {
                    GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Up Arrow Pressed");
                    upArrowPressed = true;
                    m_direction.setDirection(Direction.UP);
                }
            }

            // Tell the game to move the player sprite
            Game.getInstance().MovePlayerSprite(m_direction);

            // Tell the game to fire a player projectile...
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                // Log State Change: "Space Bar Pressed"
                if (!spacebarPressed)
                {
                    GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Space Bar Pressed");
                    spacebarPressed = true;
                    spacePressTime = Game.getInstance().getExecutionTime();

                }
            }

            // Tell the game to select rock projectile...
            if (e.getKeyCode() == KeyEvent.VK_1)
            {
                // Log State Change: "Rock Selected"
                if (selectedProjectile != ROCK)
                {
                    GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Rock Selected");
                    selectedProjectile = ROCK;
                }
            }

            // Tell the game to select dynamite projectile...
            if (e.getKeyCode() == KeyEvent.VK_2)
            {
                // Log State Change: "Dynamite Selected"
                if (selectedProjectile != DYNAMITE)
                {
                    GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Dynamite Selected");
                    selectedProjectile = DYNAMITE;
                }
            }

            // Toggle debugging mode
            if (e.getKeyCode() == KeyEvent.VK_D)
            {
                Game.getInstance().ToggleDebugInfo();
                GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Debugging Toggled");
            }

            // Toggle debugging mode
            if (e.getKeyCode() == KeyEvent.VK_R)
            {
                Game.getInstance().toggleHorde();
                GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Horde Release Toggled");
            }
            
            
        }
    }
    /**
     * Class to handle key released events.
     * 
     * @param e the released key 
     */
    public void keyReleased(KeyEvent e)
    {
        // Tell the game to stop moving the player sprite left...
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            // Log State Change: "Left Arrow Released" and check for other keys pressed
            if (leftArrowPressed)
            {
                GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Left Arrow Released");
                leftArrowPressed = false;
                m_direction.removeDirection(Direction.LEFT);
            }
        }
        
        // Tell the game to stop moving the player sprite right...
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            // Log State Change: "Right Arrow Released" and check for other keys pressed
            if (rightArrowPressed)
            {
                GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Right Arrow Released");
                rightArrowPressed = false;
                m_direction.removeDirection(Direction.RIGHT);
            }
            
        }
        
        // Tell the game to stop moving the player sprite down...
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            // Log State Change: "Down Arrow Released" and check for other keys pressed
            if (downArrowPressed)
            {
                GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Down Arrow Released");
                downArrowPressed = false;
                m_direction.removeDirection(Direction.DOWN);
            }
        }
        
        // Tell the game to stop moving the player sprite up...
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            // Log State Change: "Up Arrow Released" and check for other keys pressed
            if (upArrowPressed)
            {
                GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Up Arrow Released");
                upArrowPressed = false;
                m_direction.removeDirection(Direction.UP);
            }
            
        }
        
        Game.getInstance().MovePlayerSprite(m_direction);
        
        // Tell the game to stop firing a player bullet...
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            // Log State Change: "Space Bar Released"
            if (spacebarPressed)
            {
                GameLogger.getInstance().logMessage(LogLevel.INFO, "KeyInputHandler", "Space Bar Released");
                spacebarPressed = false;
                
                // Get the time the space was pressed and reset timers
                spaceReleaseTime = Game.getInstance().getExecutionTime();
                float heldTime = spaceReleaseTime - spacePressTime;
                spacePressTime = 0;
                spaceReleaseTime = 0;
                // Fire projectile a distance based on timer 
                float distance;
                if (heldTime/1000 < 0.06)
                {
                    distance = 0;
                }
                else if (heldTime/1000 > 0.32)
                {
                    distance = 0.32f;
                }
                else
                {
                    distance = heldTime/500;
                }
                Game.getInstance().FireProjectile(selectedProjectile, distance);
            }
        }
    }
    
    public void keyTyped(KeyEvent e)
    {
        
    }
}
