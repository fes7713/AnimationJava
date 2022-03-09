/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animation.Fire;

import Animation.GameColor;
import Animation.Particle;
import Animation.ParticleSystem;
import com.badlogic.gdx.math.Vector2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.awt.MultipleGradientPaint.CycleMethod.NO_CYCLE;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author fes77
 */
public class FireParticle extends Particle{
    protected Color centerColor;
    protected Color middleColor;
    protected Color edgeColor;

    protected float direct;

    public static final float DECELERATE_CONST = 0.9f;
    public static final int JERK_FLUCTUATION_CONST = 100;
    public static final int VIGNETTING_CONST = 4;
    public static final float ALPHA_MULTIPLIER = 0.3f;
    
    private float radialShift = 0.7f;
    
    private static Vector2 jerk = new Vector2(JERK_FLUCTUATION_CONST, 0);
    
    
//    public static final Color DEFAULT_COLOR = new Color(255, 196, 33);
    
    float heightMul = 0.05f;
    
    public FireParticle(Vector2 position, int speed, int size, float lifespan) {
        this(position, new Vector2(speed, 0).rotate(rand.nextInt(360)), size, lifespan);
    }
    
    public FireParticle(Vector2 position, Vector2 velocity, int size, float lifespan) {
        this(position, velocity, new Vector2(velocity).scl(-DECELERATE_CONST/lifespan), size, lifespan);
    }
    
    public FireParticle(Vector2 position, Vector2 velocity, Vector2 acceleration, int size, float lifespan) {
        super(position, velocity, acceleration, size, lifespan);
        
        direct = velocity.angleRad();
        centerColor = GameColor.CENTER_LIGHT;
        middleColor = GameColor.HEAT_WAVE;
        
        edgeColor = new Color(middleColor.getRed(), middleColor.getGreen(), middleColor.getBlue(), 0);
    }
    
    public boolean isActive(float deltatime)
    {
        return lifespan > deltatime;
    }
    
    public void update(float deltatime)
    {
        acceleration.add(jerk.rotate(rand.nextInt(360)));
        middleColor = new Color(
                (int)(middleColor.getRed()),
                (int)(middleColor.getGreen()),
                (int)(middleColor.getBlue()),
                (int)(middleColor.getAlpha() * Math.max(1 - deltatime/lifespan * ALPHA_MULTIPLIER, 0)));

//        size += (targetSize - size) / lifespan * deltatime;
        radialShift = Math.max(radialShift * (1 - deltatime/lifespan * VIGNETTING_CONST), 0.01f);
        direct = velocity.angleRad();
        super.update(deltatime);
        
    }

    
    public void draw(Graphics2D g2d, int shiftX, int shiftY, float zoom)
    {
        AffineTransform at = g2d.getTransform();
        
        at.rotate(direct, position.x * zoom + shiftX, position.y * zoom + shiftY);
        g2d.setTransform(at);
        
        Point2D center = new Point2D.Float((int)(position.x * zoom) + shiftX, (int)(position.y * zoom) + shiftY);
        float radius = size/2 * zoom;
        
        float[] dist = {0.0f, radialShift, 1.0f};
        
        Color[] colors = {
            centerColor,
            middleColor,
            edgeColor
        };
        
        RadialGradientPaint p =
            new RadialGradientPaint(center, radius, dist, colors, NO_CYCLE);

        
        g2d.setPaint(p);
        g2d.fillRoundRect((int)((position.x - size / 2) * zoom) + shiftX, 
                (int)((position.y  - size * heightMul / 2) * zoom) + shiftY, 
                (int)((size) * zoom),
                (int)((size * heightMul) * zoom),
                (int)((size) * zoom),
                (int)((size * heightMul) * zoom));
                // Size Guide -> Uncomment this when you debug this
//        g2d.setColor(Color.BLACK);
//        g2d.drawLine(
//                (int)((position.x - size / 2) * zoom) + shiftX, 
//                (int)((position.y) * zoom) + shiftY, 
//                (int)((position.x + size / 2) * zoom) + shiftX, 
//                (int)((position.y) * zoom) + shiftY);
//        g2d.setColor(Color.BLACK);
//        g2d.drawLine(
//                (int)((position.x) * zoom) + shiftX, 
//                (int)((position.y  - size / 2) * zoom) + shiftY, 
//                (int)((position.x) * zoom) + shiftX, 
//                (int)((position.y  + size / 2) * zoom) + shiftY);

        at.rotate(-direct, position.x * zoom + shiftX, position.y * zoom + shiftY);
        g2d.setTransform(at);
    }
    
    public static void main(String[] argv) throws InterruptedException
    {
        System.setProperty("sun.java2d.opengl", "true");
        ParticleSystem particles = new ParticleSystem();
        
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

                particles.addParticle(new FireParticle(
                        new Vector2(200, 200), 
                        800,
                        35, 1));

                particles.draw(g2d, 10, 0, 2);
//              
                
//
//                if(rand.nextInt(4) == 0)
//                    for(int i = 0; i < 30; i++)
//                    {
//                        particles.add(
//                            new Particle(
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
            Thread.sleep(10);
            particles.update(10/1000f);
            frame.repaint();
        }
    }
}
