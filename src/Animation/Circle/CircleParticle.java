/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animation.Circle;

import Animation.Particle;
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
public class CircleParticle extends Particle{
    protected Color color;
    
    protected float initSize;
    protected float sizeDifusion = 5;
    protected float[] rgbaDifusion = {2, 4, 8, 0.3f};
    protected final float targetSize;
    private static Vector2 jerk = new Vector2(3, 0);
    
    public static final int DEFAULT_SIZE_DEFUSION = 5;
    public static final float[] DEFAULT_RGBA_DEFUSION = {2, 4, 8, 0.3f};
    public static final Color DEFAULT_COLOR = new Color(255, 255, 255);
    
    public CircleParticle(Vector2 position, Vector2 velocity, Vector2 acceleration, int size, float lifespan) {
        this(position, velocity, acceleration, size, lifespan, DEFAULT_SIZE_DEFUSION, DEFAULT_COLOR, DEFAULT_RGBA_DEFUSION);
    }
    
    public CircleParticle(Vector2 position, Vector2 velocity, Vector2 acceleration, int size, float lifespan, float sizeDifusion) {
        this(position, velocity, acceleration, size, lifespan, sizeDifusion, DEFAULT_COLOR, DEFAULT_RGBA_DEFUSION);
    }
    
    public CircleParticle(Vector2 position, Vector2 velocity, Vector2 acceleration, int size, float lifespan, float sizeDifusion, float[] rgbaDifusion) {
        this(position, velocity, acceleration, size, lifespan, sizeDifusion, DEFAULT_COLOR, rgbaDifusion);
    }
    
    public CircleParticle(Vector2 position, Vector2 velocity, Vector2 acceleration, int size, float lifespan, float sizeDifusion, Color color, float[] rgbaDifusion) {
        super(position, velocity, acceleration, size, lifespan);
        this.sizeDifusion = sizeDifusion;
        this.color = color;
        this.rgbaDifusion = rgbaDifusion;
        
        initSize = size;
        targetSize = size * sizeDifusion;
    }
    
    
    
    public void update(float deltatime)
    {
//        if(!isActive(deltatime))
//            return;
        super.update(deltatime);
        acceleration.add(jerk.rotate(rand.nextInt(360)));
//        if(lifespan < 0.04f)
//            initSize = 0; 
//                
//        if(color.getBlue() * (1 - deltatime/lifespan * rgbaDifusion[2]) < 0)
//            initSize = 0; 
        color = new Color(
                (int)(color.getRed() * Math.max(1 - deltatime/lifespan * rgbaDifusion[0], 0)),
                (int)(color.getGreen() * Math.max(1 - deltatime/lifespan * rgbaDifusion[1], 0)),
                (int)(color.getBlue() * Math.max(1 - deltatime/lifespan * rgbaDifusion[2], 0)),
                (int)(color.getAlpha() * Math.max(1 - deltatime/lifespan * rgbaDifusion[3], 0)));

        size += (targetSize - size) / lifespan * deltatime;
    }
    
    @Override
    public void draw(Graphics2D g2d, int shiftX, int shiftY, float zoom)
    {
        Point2D center = new Point2D.Float((int)(position.x * zoom) + shiftX, (int)(position.y * zoom) + shiftY);
        float radius = size/2 * zoom;
        float[] dist = {0.2f, 1.0f};
        Color[] colors = {
            new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha())),
            new Color(0, 0, 0, 0)
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
        List<CircleParticle> particles = new ArrayList<>();
        
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
                particles.add(new CircleParticle(
                        new Vector2(200, 200), 
                        new Vector2(0, 0),
                        new Vector2(0, 0), 
                        35, 4));
                for(CircleParticle p: particles)
                {
                    p.draw(g2d, 10, 0, 1);
                }
//                
                for(int i = 0; i < particles.size(); i++)
                {
                    if(particles.get(i).isActive(20/1000f))
                        particles.get(i).update(20/1000f);
                    else
                        particles.remove(particles.get(i));
                }
//
//                if(rand.nextInt(4) == 0)
//                    for(int i = 0; i < 30; i++)
//                    {
//                        particles.add(
//                            new CircleParticle(
//                                new Vector2(200 + i * 30, 600), 
//                                new Vector2(rand.nextFloat() * 50-25, 0),
//                                new Vector2(0, -35), 
//                                4, 35));
//                    }
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
