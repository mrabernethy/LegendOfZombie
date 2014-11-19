package Source;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**
 * 
 * 
 */
public class BackBuffer 
{
    private int m_windowWidth = 800;
    private int m_windowHeight = 600;
        
    private BufferStrategy m_strategy;
    private Canvas m_canvas;
    private Graphics2D m_graphics;
    
    public BackBuffer()
    {
    
    }
    
    public void initialise(int width, int height)
    {
        m_canvas = new Canvas();
        
        m_windowWidth = width;
        m_windowHeight = height;
        
        createWindow();
        
        // Log State Change: "Backbuffer Initialised"
        GameLogger.getInstance().logMessage(LogLevel.INFO, "BackBuffer", "Backbuffer Initiated");
    }
        
    public void clear()
    {
        m_graphics = (Graphics2D) m_strategy.getDrawGraphics();
        m_graphics.setColor(Color.black);
        m_graphics.fillRect(0, 0, m_windowWidth, m_windowHeight);
    }
    
    public void present()
    {
        m_graphics.dispose();
        m_strategy.show();
    }
    
    public void draw(Image i, AffineTransform t)
    {
        m_graphics.drawImage(i, t, null);
    }
    
    public void drawText(int x, int y, String s)
    {
        m_graphics.setColor(Color.white);
        m_graphics.drawString(s, x, y);
    }
    
    public void drawImage(int x, int y, Image i)
    {
        m_graphics.drawImage(i, x, y, null);
    }
    
    public void drawSpriteFrame(int x, int y, int frameX, int frameY, int w, int h, Image i)
    {
        m_graphics.drawImage(i, x, y, x + w, y + h,
                             frameX, frameY, frameX + w, frameY + h, null);
    }
    
    private void createWindow()
    {
        JFrame containerFrame = new JFrame("");
        
        JPanel panel = (JPanel) containerFrame.getContentPane();
        
        panel.setPreferredSize(new Dimension(m_windowWidth, m_windowHeight));
        panel.setLayout(null);
        
        m_canvas.setBounds(0, 0, m_windowWidth, m_windowHeight);
        panel.add(m_canvas);
        
        m_canvas.setIgnoreRepaint(true);
        
        containerFrame.pack();
        containerFrame.setResizable(false);
        containerFrame.setVisible(true);
        
        containerFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        m_canvas.addKeyListener(new KeyInputHandler());
    
        m_canvas.requestFocus();
    
        m_canvas.createBufferStrategy(2);
        
        m_strategy = m_canvas.getBufferStrategy();
    }
}
