package Source;

import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Steffan Hooper
 * @version v1.0 - 2014?: Created
 * 
 * Modified by Michael Abernethy
 * Version v1.1 - 2014.08.07: Modified for Assignment 1
 */
public class Game 
{
    // Singleton Design Pattern:
    private static Game sm_game = null;
    
    // Framework:
    private BackBuffer m_backBuffer;
    private SpriteStore m_spriteStore;
    
    // Simulation Counters:
    private float m_elapsedSeconds;
    private float m_executionTime;
    private int m_frameCount;
    private int m_FPS;
    private int m_numUpdates;
    private boolean m_drawDebugInfo;
    private boolean releaseHorde;
    private float m_zombieReleaseTimer1, m_zombieReleaseTimer2;
    
    // Game Entities...
    private ArrayList<EntityMapPosition> m_map;
    private ArrayList<Background> m_background;
    private Player m_player;
    private ArrayList<Zombie> m_zombies;
    private ArrayList<Projectile> m_projectiles;
    private ArrayList<Tile> m_tiles;
    private ArrayList<Explosion> m_explosion;
    private ArrayList<ParticleEmitter> m_particleEmitters;
    
    private GameState m_gameState;
    
    // Singleton Design Pattern:
    public synchronized static Game getInstance()
    {
        if (sm_game == null)
        {
            sm_game = new Game();
        }
        
        return (sm_game);
    }
    
    /**
     * The game constructor.
     * 
     * Sets debugging values to zero.
     * Sets up the game framework.
     * 
     */
    private Game()
    {
        m_executionTime = 0;
        m_elapsedSeconds = 0;
        m_frameCount = 0;
        m_FPS = 0;
        m_numUpdates = 0;
        
        // Set game state to playing
        m_gameState = GameState.MENU;
    
        // Setup game framework.
        m_backBuffer = new BackBuffer();
        m_spriteStore = new SpriteStore();
        m_backBuffer.initialise(1640, 960); // Frame width, height.
        
        // Load the background
        Sprite background = m_spriteStore.getSprite("Assets/bgd1.png");
        // Create instance of background
        m_background = new ArrayList<>();
        m_background.add(new Background(background, 0, 0));
        m_background.add(new Background(background, 800, 0));
        m_background.add(new Background(background, 0, 600));
        m_background.add(new Background(background, 800, 600));
        m_background.add(new Background(background, 1600, 0));
        m_background.add(new Background(background, 1600, 600));
        // Create the tile container
        m_tiles = new ArrayList<>();
        // Load the player ship sprite. 
        Sprite playersprite = m_spriteStore.getSprite("Assets/human.png");
        // Create the player ship instance.
        m_player = new Player(playersprite, 1504, 864);
        // Create empty zombie container.
        m_zombies = new ArrayList<Zombie>();
        // Create empty projectile container.
        m_projectiles = new ArrayList<Projectile>();
        // Create empty explosion container.
        m_explosion = new ArrayList<Explosion>();
        // Create an empty particle emitter container
        m_particleEmitters = new ArrayList<>();
        // Create the world map container
        m_map = new ArrayList<>();
        // Get world map
        getMap();
        // Add entities to the world
        createMap();
    }
    
    /**
     * The game loop.
     */
    public void gameLoop()
    {
           
            final float stepSize = 16.6666666f;

            long lastTime = System.currentTimeMillis();
            long lag = 0;

            while (true)
            {
                long current = System.currentTimeMillis();
                long deltaTime = current - lastTime;
                lastTime = current;

                m_executionTime += deltaTime;

                lag += deltaTime;

                while (lag >= stepSize)
                {
                    process(stepSize / 1000.0f);

                    lag -= stepSize;

                    ++m_numUpdates;
                }

                draw();
            }
            
            
    }
    
    
    /**
     * Updates the game.
     *  
     * @param deltaTime the duration of one frame???
     */
    public void process(float deltaTime)
    {
        if (m_gameState == GameState.PLAYING)
        {
            // Count total simulation time elapsed:
            m_elapsedSeconds += deltaTime;

            // Frame Counter:
            if (m_elapsedSeconds > 1)
            {
                m_elapsedSeconds -= 1;
                m_FPS = m_frameCount;
                m_frameCount = 0;
            }

            // Update the game world simulation:

            if (releaseHorde)
            {
                // Zombie spawner
                m_zombieReleaseTimer1 += deltaTime;

                if (m_zombieReleaseTimer1 > 2 && m_zombies.size() < 100)
                {
                    m_zombieReleaseTimer1 -= 2;
                    SpawnZombie(1504, 864, AIState.CHASE, true);
                }
            }

            // Process each tile in the container
            for (int i=0; i<m_tiles.size(); i++)
            {
                Tile t = m_tiles.get(i);
                t.process(deltaTime);
            }

            // Process each zombie in the container.
            for (int i=0; i<m_zombies.size(); i++)
            {
                Zombie z = m_zombies.get(i);
                z.process(deltaTime);
                // create a new particle emitter
                BloodParticleEmitter bloodParticleEmitter = 
                        new BloodParticleEmitter((int)m_zombies.get(i).getVector().getX(), 
                        (int)m_zombies.get(i).getVector().getY(), m_spriteStore, m_zombies.get(i).getRotation());
                // spawn the particles
                bloodParticleEmitter.spawnParticles();
                // add the emitter to the container
                m_particleEmitters.add(bloodParticleEmitter);
            }
            // Process each explosion in the container.
            for (Explosion explosion : m_explosion)
            {
                explosion.process(deltaTime);
            }
            // Process each projectile in the container.
            for (int i=0; i<m_projectiles.size(); i++)
            {
                Projectile p = m_projectiles.get(i);
                p.process(deltaTime);
                if (p.getElapsedTime() >= 1.5 && p instanceof Dynamite && !p.hasExploded())
                {
                    p.setExploded();
                    SpawnExplosion((int)p.getPositionX(), (int)p.getPositionY());
                }
            }
            // process all the particle effects
            for (int i=0; i<m_particleEmitters.size(); i++)
            {
                if (m_particleEmitters.get(i) != null)
                {
                    m_particleEmitters.get(i).processParticles(deltaTime);
                }
            }
            // Update player...
            m_player.process(deltaTime);

            // Check for collisions
            // For each explosion
            for (int i=0; i<m_explosion.size(); i++)
            {
                // With the player
                if (m_explosion.get(i).isCollidingWith(m_player))
                {
                    m_player.collidedWith(m_explosion.get(i));
                }
                // For the tiles
                for (int j=0; j<m_tiles.size(); j++)
                {
                    if (m_explosion.get(i).isCollidingWith(m_tiles.get(j)) && m_tiles.get(j).getTileType() == TileType.TREE)
                    {
                        m_explosion.get(i).collidedWith(m_tiles.get(j));
                    }
                }
                // For the zombies
                for (int j=0; j<m_zombies.size(); j++)
                {
                    if (m_explosion.get(i).isCollidingWith(m_zombies.get(j)))
                    {
                        m_explosion.get(i).collidedWith(m_zombies.get(j));
                        // create a new particle emitter
                        ZombieExplosionParticleEmitter zombieExplosionParticleEmitter = 
                                new ZombieExplosionParticleEmitter((int)m_zombies.get(j).getVector().getX(), 
                                (int)m_zombies.get(j).getVector().getY(), m_spriteStore);
                        // spawn the particles
                        zombieExplosionParticleEmitter.spawnParticles();
                        // add the emitter to the container
                        m_particleEmitters.add(zombieExplosionParticleEmitter);
                    }
                }
            }

            // collide zombies
                // For each tile
            for (int i=0; i<m_tiles.size(); i++)
            {
                // With the player
                if (m_tiles.get(i).isCollidingWith(m_player))
                {
                    // might need to assign tile to new variable???
                    m_player.collidedWith(m_tiles.get(i));
                }

                // For each zombie
                for (int j=0; j<m_zombies.size(); j++)
                {
                    if (m_tiles.get(i).isCollidingWith(m_zombies.get(j)))
                    {
                        m_zombies.get(j).collidedWith(m_tiles.get(i));
                    }
                }
            }
            // For each zombie
            for (int i=0; i<m_zombies.size(); i++)
                {
                    // With the player
                    if (m_zombies.get(i).isCollidingWith(m_player))
                    {
                        m_player.collidedWith(m_zombies.get(i));
                        // create a new particle emitter
                        BloodParticleEmitter bloodParticleEmitter = 
                                new BloodParticleEmitter((int)m_player.getVector().getX(), 
                                (int)m_player.getVector().getY(), m_spriteStore, m_player.getRotation());
                        // spawn the particles
                        bloodParticleEmitter.spawnParticles();
                        // add the emitter to the container
                        m_particleEmitters.add(bloodParticleEmitter);
                    }
                    // For each other zombie
                    for (int j=0; j<m_zombies.size(); j++)
                    {
                        if (i!=j && m_zombies.get(i).isCollidingWith(m_zombies.get(j)))
                        {
                            m_zombies.get(j).collidedWith(m_zombies.get(j));
                        }
                    }
                    // For each rock
                    for (int j=0; j<m_projectiles.size(); j++)
                    {
                        if (m_projectiles.get(j) instanceof Rock && m_projectiles.get(j).isCollidingWith(m_zombies.get(i)))
                        {
                            Rock r = (Rock) m_projectiles.get(j);
                            r.collidedWith(m_zombies.get(i));
                            // create a new particle emitter
                            ZombieExplosionParticleEmitter zombieExplosionParticleEmitter 
                                    = new ZombieExplosionParticleEmitter((int)m_zombies.get(i).getVector().getX(), 
                                    (int)m_zombies.get(i).getVector().getY(), m_spriteStore);
                            // spawn the particles
                            zombieExplosionParticleEmitter.spawnParticles();
                            // add the emitter to the container
                            m_particleEmitters.add(zombieExplosionParticleEmitter);
                        }
                    }
                } 

            // Remove any dead projectiles from the container...
            ArrayList<Projectile> deadProjectiles = new ArrayList<>();
            for (int i=0; i<m_projectiles.size(); i++)
            {
                Projectile p = m_projectiles.get(i);
                if (p.isDead())
                {
                    deadProjectiles.add(p);
                }
            }
            m_projectiles.removeAll(deadProjectiles);

            // Remove any dead zombies from the container...
            ArrayList<Zombie> deadZombies = new ArrayList<>();
            for (int i=0; i<m_zombies.size(); i++)
            {
                Zombie z = m_zombies.get(i);
                if (z.isDead())
                {
                    deadZombies.add(z);
                }
            }
            m_zombies.removeAll(deadZombies);

            // Remove any dead explosions from the container...
            ArrayList<Explosion> deadExplosions = new ArrayList<>();
            for (Explosion explosion : m_explosion)
            {
                if (explosion.isDead())
                {
                    deadExplosions.add(explosion);
                }
            }
            m_explosion.removeAll(deadExplosions);

            // Remove any dead Tiles
            ArrayList<Tile> deadTiles = new ArrayList<>();
            for (Tile t : m_tiles)
            {
                if (t.isDead())
                {
                    deadTiles.add(t);
                }
            }
            m_tiles.removeAll(deadTiles);
        }
    }
    

    /**
     * Renders the game.
     * ...
     */
    public void draw()
    {
        if (m_gameState == GameState.PLAYING)
        {
            ++m_frameCount;

            // Prepare to draw a new frame:
            m_backBuffer.clear();

            // Draw the background
            for (Background b : m_background)
            {
                b.draw(m_backBuffer);
            }
            
            // draw all the particle effects
            for (int i=0; i<m_particleEmitters.size(); i++)
            {
                if (m_particleEmitters.get(i) != null)
                {
                    m_particleEmitters.get(i).drawParticles(m_backBuffer);
                }
            }
 
            // Draw all tiles in the container
            for (int i=0; i<m_tiles.size(); i++)
            {
                Tile t = m_tiles.get(i);
                t.draw(m_backBuffer);
            }

            // Draw all zombies in container...
            for (int i=0; i<m_zombies.size(); i++)
            {
                Zombie z = m_zombies.get(i);
                z.draw(m_backBuffer);
            }

            // Draw all projectiles in container...
            for (int i=0; i<m_projectiles.size(); i++)
            {
                Projectile p = m_projectiles.get(i);

                if (p != null)
                {
                    p.draw(m_backBuffer);
                }
            }

            // Draw all explosions in container...
            for (Explosion explosion : m_explosion)
            {
                explosion.draw(m_backBuffer);
            }
            
            // Draw the player sprite
            m_player.draw(m_backBuffer);
            // Draw the safehouse last
            for (int i=0; i<m_tiles.size(); i++)
            {
                Tile t = m_tiles.get(i);
                if (t.getTileType() == TileType.SAFEHOUSE)
                {
                    t.draw(m_backBuffer);
                }
            }
            
            // Draw heads up display
            for (int i=0; i<m_player.getLives(); i++)
            {
                AffineTransform t = new AffineTransform();
                t.scale(1, 1);
                
                t.translate(1604, i*32);

                m_player.getSprite().draw(m_backBuffer, t);
            }
            for (int i=10; i<m_player.getNumDynamite()+10; i++)
            {
                AffineTransform t = new AffineTransform();
                t.scale(1, 1);
                
                t.translate(1604, i*32);

                Sprite dynamite = m_spriteStore.getSprite("Assets/dynamite.png");
                dynamite.draw(m_backBuffer, t);
            }
            for (int i=20; i<m_player.getNumRocks()+20; i++)
            {
                AffineTransform t = new AffineTransform();
                t.scale(1, 1);
                
                t.translate(1604, i*32);

                Sprite rock = m_spriteStore.getSprite("Assets/rock.png");
                rock.draw(m_backBuffer, t);
            }
            
            //Draw Test Text:
            if (m_drawDebugInfo)
            {
                m_backBuffer.drawText(10, 40, "FPS: " + String.valueOf(m_FPS));

                // Draw debug text for number of projectiles in container. 
                m_backBuffer.drawText(10, 30, "Projectiles: " + String.valueOf(m_projectiles.size()));
                // draw debug text for number of particles in the emitters
                int particleCount = 0;
                for (ParticleEmitter pe : m_particleEmitters)
                {
                    particleCount += pe.getNumParticles();
                }
                m_backBuffer.drawText(10, 20, "Particles: " + String.valueOf(particleCount));
                // Draw debug text for number of zombies in container. 
                m_backBuffer.drawText(10, 10, "Zombies: " + String.valueOf(m_zombies.size()));
            }
            // Flip frame buffers:
            m_backBuffer.present();
        }
        else if (m_gameState == GameState.MENU)
        {
            m_backBuffer.clear();
            m_backBuffer.drawText(700, 300, "The Legend of Zombie");
            m_backBuffer.drawText(700, 350, "Menu:");
            m_backBuffer.drawText(700, 362, "Enter 1 to start");
            m_backBuffer.drawText(700, 374, "Enter 2 for the controls");
            m_backBuffer.drawText(700, 386, "Click the X to exit");
            m_backBuffer.drawText(700, 420, "Hurry up and get to the safe house");
            m_backBuffer.present();
        }
        else if (m_gameState == GameState.CONTROLS)
        {
            m_backBuffer.clear();
            m_backBuffer.drawText(700, 300, "The Legend of Zombie");
            m_backBuffer.drawText(700, 350, "Controls:");
            m_backBuffer.drawText(700, 362, "Use arrow keys to run");
            m_backBuffer.drawText(700, 374, "Push 1 to select a rock");
            m_backBuffer.drawText(700, 386, "Push 2 to select dynamite");
            m_backBuffer.drawText(700, 398, "Push space to throw projectile");
            m_backBuffer.drawText(700, 420, "Hit return to go back to the menu");
            m_backBuffer.present();
        }
        else if (m_gameState == GameState.GAMEOVER)
        {
            m_backBuffer.clear();
            m_backBuffer.drawText(700, 300, "Game Over!!!");
            m_backBuffer.present();
        }
        else if (m_gameState == GameState.WON)
        {
            m_backBuffer.clear();
            m_backBuffer.drawText(700, 300, "You Win!!!");
            m_backBuffer.present();
        }
    }
    
    public void MovePlayerSprite(PlayerDirection direction)
    {
        if (direction != null)
        {
            
            if (direction.getCurrentDirection() == null)
            {
                m_player.setHorizontalVelocity(0);
                m_player.setVerticalVelocity(0);
            }
            else if (direction.getCurrentDirection().equals(Direction.LEFT))
            {
                m_player.setDirection(Direction.LEFT);
                m_player.setHorizontalVelocity(-1.5f);
                m_player.setVerticalVelocity(0);
            }
            else if (direction.getCurrentDirection().equals(Direction.RIGHT))
            {
                m_player.setDirection(Direction.RIGHT);
                m_player.setHorizontalVelocity(1.5f);
                m_player.setVerticalVelocity(0);
            }
            else if (direction.getCurrentDirection().equals(Direction.UP))
            {
                m_player.setDirection(Direction.UP);
                m_player.setHorizontalVelocity(0);
                m_player.setVerticalVelocity(-1.5f);
            }
            else if (direction.getCurrentDirection().equals(Direction.DOWN))
            {
                m_player.setDirection(Direction.DOWN);
                m_player.setHorizontalVelocity(0);
                m_player.setVerticalVelocity(1.5f);
            }
        }
    }
    

    
    
    // Space a projectile in game.
    public void FireProjectile(int selectedProjectile, float distance) 
    {
        Projectile p = null;
        if (selectedProjectile == 1 && m_player.getNumRocks()>0)
        {
            // Get the player rock sprite.   
            Sprite rock = m_spriteStore.getSprite("Assets/rock.png");
        
            // Create a new rock object.
            p = new Rock(rock, (int) m_player.getPositionX()+8, (int) m_player.getPositionY()+8);
            
            m_player.setNumRocks(m_player.getNumRocks()-1);
        }
        else if (selectedProjectile == 2 && m_player.getNumDynamite()>0)
        {
            // Get the player dynamite sprite.   
            Sprite dynamite = m_spriteStore.getSprite("Assets/dynamite.png");
        
            // Create a new dynamite object.
            p = new Dynamite(dynamite, (int) m_player.getPositionX()+8, (int) m_player.getPositionY()+8);
            
            m_player.setNumDynamite(m_player.getNumDynamite()-1);

        }
        if (p != null)
        {
            // Set the projectile direction
            p.setDirection(m_player.getDirection());
            // Set the distance the projectile will travel
            p.setDistance(distance);
            // Add the new projectile to the container.
            m_projectiles.add(p);
        }
    }
    

    
    // Spawn a tile in the game
    private void SpawnTile(int x, int y, TileType type)
    {
        Sprite s = null;
        // Load the tile
        if (type == TileType.TREE)
        {
            s = m_spriteStore.getSprite("Assets/tree.png");
        }
        else if (type == TileType.WATER)
        {
            s = m_spriteStore.getSprite("Assets/squarewater.png");
        }
        else if (type == TileType.SAFEHOUSE)
        {
            s = m_spriteStore.getSprite("Assets/safehouse.png");
        }
        if (s != null)
        {
            // Create a new tile object
            Tile t = new Tile(s, x, y, type);
            // Set the tile scale size
            t.setScale(1);
            // Add the tile to the container
            m_tiles.add(t);
        }
    }
    
    // Spawn a Enemy in game.
    private void SpawnZombie(int x, int y, AIState state, boolean isHorde)
    {
        Sprite zombiesprite1 = m_spriteStore.getSprite("Assets/zombienomouth.png");
        Sprite zombiesprite2 = m_spriteStore.getSprite("Assets/zombie.png");
        
        // Create a new Enemy object. 
        Zombie z = new Zombie(zombiesprite1, zombiesprite2, x, y);
        // Set the initial AI state
        z.setState(state);
        // Set if zombie is part of the zombie horde
        z.setIsHorde(isHorde);
        // Set the player to watch
        z.setPlayer(m_player);
        // Add the new Enemy to the enemy container.
        m_zombies.add(z);
    }
    
    // Spawn a Explosion in game.
    public void SpawnExplosion(int x, int y)
    {
        // Get the explosion sprite.
        AnimatedSprite explosionSprite = getAnimatedSprite("Assets/explosion.png");
        // Setup frame speed
        explosionSprite.setFrameSpeed(0.08f);
        // Setup the frame width.
        explosionSprite.setFrameWidth(64);
        // Add coordinates for each frame.
        explosionSprite.addFrame(0);
        explosionSprite.addFrame(64);
        explosionSprite.addFrame(128);
        explosionSprite.addFrame(192);
        explosionSprite.addFrame(256);
        // Create a new explosion object.
        Explosion explosion = new Explosion(explosionSprite, x-32, y-32);
        // Add the new explosion to the explosion container.
        m_explosion.add(explosion);
    }
    
    // Add a method to load and setup Animated Explosion Sprite
    private AnimatedSprite getAnimatedSprite(String filename)
    {
        // Create a new AnimatedSprite object.
        AnimatedSprite animatedSprite = new AnimatedSprite(filename);

        return animatedSprite;
    }
      
    // Bind a key to toggle debug text on and off...
    public void ToggleDebugInfo()
    {
        m_drawDebugInfo = !m_drawDebugInfo;
    }
    
    public GameState getGameState()
    {
        return m_gameState;
    }
    
    public void setGameState(GameState state)
    {
        m_gameState = state;
    }
    
    public float getExecutionTime()
    {
        return m_executionTime;
    }
    

    
    public void toggleHorde()
    {
        releaseHorde = !releaseHorde;
    }
    
    
    /**
     * Read the world map to a collection.
     * Code adapted from PDC lab4 solution.
     */
    private void getMap()
    {
        try {
            // Create a buffered read and writer.
            BufferedReader br = new BufferedReader(new FileReader("worldmap.txt"));
            // line counter
            int lineNum = 0;
            // Create a variable to store a line
            String line = "";
            while((line = br.readLine()) != null) {
                // Cycle through the array in reverse order
                for(int i = line.length()-1; i >= 0; i--) {
                    char currentChar = line.charAt(i);
                    // Check if  the current character is a letter
                    if(Character.isLetter(currentChar)) {
                        m_map.add(new EntityMapPosition(line.charAt(i), i, lineNum));
                    }
                }
                lineNum++;
            }
        } catch(IOException ex) {
            System.err.println("IOException Error: " + ex.getMessage());
        }
    }
    
    private void createMap()
    {
        for (EntityMapPosition emp : m_map)
        {
            String str = emp.getEntityType();
            
            if (str.equals("z"))
            {
                SpawnZombie(emp.getX()*32, emp.getY()*32, AIState.GUARD, false);
            }
            else if (str.equals("t"))
            {
                SpawnTile(emp.getX()*32, emp.getY()*32, TileType.TREE);
            }
            else if (str.equals("w"))
            {
                SpawnTile(emp.getX()*32, emp.getY()*32, TileType.WATER);
            }
            else if (str.equals("s"))
            {
                SpawnTile(emp.getX()*32, emp.getY()*32, TileType.SAFEHOUSE);
            }
        }
    }
        
    // Application Entrypoint:
    public static void main(String[] args)
    {
        Game.getInstance().gameLoop();
    }
}

