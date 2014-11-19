package Source;

/**
 * Class to log game events to the console.
 * 
 * @author Michael Abernethy
 * @version v1.0 - 2014.08: Created
 */
public class GameLogger 
{
    private static GameLogger gl = null;
    
    public static GameLogger getInstance()
    {
        if (gl == null)
        {
            gl = new GameLogger();
        }
        
        return (gl);
    }
    
    /**
     * Takes a level, module, and message, and sends the message to the console.
     */
    public void logMessage(LogLevel level, String module, String message)
    {
        System.out.println(level.toString() + " : " + module + " : " + message);
    }    
}
