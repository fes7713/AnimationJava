package Animation.Circle;

import Animation.Interface.Drawable;
import com.badlogic.gdx.math.Vector2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.MultipleGradientPaint.CycleMethod.NO_CYCLE;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author fes77
 */
public class AnimeCircle extends CircleParticle implements Drawable{

    public AnimeCircle(Vector2 position, Vector2 velosity, Vector2 acceleration, int size, float lifespan) {
        super(position, velosity, acceleration, size, lifespan);
    }

    public AnimeCircle(Vector2 position, Vector2 velosity, Vector2 acceleration, int size, float lifespan, float sizeDifusion) {
        super(position, velosity, acceleration, size, lifespan, sizeDifusion);
    }
    
    public AnimeCircle(Vector2 position, Vector2 velosity, Vector2 acceleration, int size, float lifespan, float sizeDifusion, float[] rgbaDifusion) {
        super(position, velosity, acceleration, size, lifespan, sizeDifusion, rgbaDifusion);
    }
    
    public AnimeCircle(Vector2 position, Vector2 velosity, Vector2 acceleration, int size, float lifespan, float sizeDifusion, Color color, float[] rgbaDifusion) {
        super(position, velosity, acceleration, size, lifespan, sizeDifusion, color, rgbaDifusion);
    }
    
    @Override
    public void draw(Graphics2D g2d, int shiftX, int shiftY, float zoom)
    {
        Point2D center = new Point2D.Float((int)(position.x * zoom) + shiftX, (int)(position.y * zoom) + shiftY);
        float radius = size * zoom;
        float[] dist = {0.2f, 1.0f};
        Color[] colors = {
            new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha())),
            new Color(0, 0, 0, 0
            )
        };
        
        RadialGradientPaint p =
            new RadialGradientPaint(center, radius, dist, colors, NO_CYCLE);
        g2d.setColor(color);
        g2d.setPaint(p);
        g2d.fillOval((int)((position.x - size/2f) * zoom) + shiftX, (int)((position.y - size/2f) * zoom) + shiftY, (int)(size * zoom), (int)(size * zoom));
    }
    
    public static void main(String[] argv) throws InterruptedException
    {
        System.setProperty("sun.java2d.opengl", "true");
        List<AnimeCircle> particles = new ArrayList<>();
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 700));
        Random rand = new Random();
        JPanel panel = new JPanel()
        {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for(AnimeCircle p: particles)
                {
                    p.draw(g2d);
                }
                
                for(int i = 0; i < particles.size(); i++)
                {
                    if(particles.get(i).isActive(20/1000f))
                        particles.get(i).update(20/1000f);
                    else
                        particles.remove(particles.get(i));
                }

                if(rand.nextInt(4) == 0)
                    for(int i = 0; i < 1; i++)
                    {
                        particles.add(new AnimeCircle(
                                new Vector2(200 + i * 30, 600), 
                                new Vector2(rand.nextFloat() * 50-25, 0),
                                new Vector2(0, -35), 
                                4, 35));
                    }
                System.out.println("Size: " + particles.size());
            }
        };
        panel.setBackground(Color.GRAY);
        frame.add(panel);
        
        frame.setVisible(true);
        while(true)
        {
            Thread.sleep(5);
            frame.repaint();
        }
    }
}