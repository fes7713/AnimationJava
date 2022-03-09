/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animation.Circle;

import Animation.AnimationGroup;
import Animation.ParticleSystem;
import com.badlogic.gdx.math.Vector2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author fes77
 */
public class Smoke extends AnimationGroup{
    protected int spread;
    
    protected float[] rgbaDifusion;
    protected boolean deceleration = DEFAULT_DECELERATION;
    protected boolean active;
    
    protected Color color;
    protected Color whiteSmoke = Color.WHITE;
    protected Color graySmoke = Color.LIGHT_GRAY;
    
    public final static boolean DEFAULT_DECELERATION = true;
    
    public final static int DEFAULT_DENSITY = 10;
    public final static int DEFAULT_SPREAD = 20;
    public final static int DENSITY_RAND = 30;
    public final static int DENSITY_DIVIDER = 11;

    public Smoke(ParticleSystem system, Vector2 position, Vector2 velocity, int size) {
        this(system, position, velocity, size, DEFAULT_LIFESPAN, DEFAULT_DENSITY, DEFAULT_SPREAD, Color.WHITE, CircleParticle.DEFAULT_RGBA_DEFUSION);
    }
    
    public Smoke(ParticleSystem system, Vector2 position, Vector2 velocity, int size, int density) {
        this(system, position, velocity, size, DEFAULT_LIFESPAN, density, DEFAULT_SPREAD, Color.WHITE, CircleParticle.DEFAULT_RGBA_DEFUSION);
    }
    
    public Smoke(ParticleSystem system, Vector2 position, Vector2 velocity, int size, int density, int spread) {
        this(system, position, velocity, size, DEFAULT_LIFESPAN, density, spread, Color.WHITE, CircleParticle.DEFAULT_RGBA_DEFUSION);
    }
    
    public Smoke(ParticleSystem system, Vector2 position, Vector2 velocity, int size, float lifespan, int density, int spread) {
        this(system, position, velocity, size, lifespan, density, spread, Color.WHITE, CircleParticle.DEFAULT_RGBA_DEFUSION);
    }
    
    public Smoke(ParticleSystem system, Vector2 position, Vector2 velocity, int size, int density, int spread, Color color, float[] rgbaDifusion) {
        this(system, position, velocity, size, DEFAULT_LIFESPAN, density, spread, color, rgbaDifusion);
    }
    
    public Smoke(ParticleSystem system, Vector2 position, Vector2 velocity, int size, float lifespan, int density, int spread, Color color, float[] rgbaDifusion) {
        super(system, position, velocity, size, lifespan, density);
        setVelosity(velocity);
        this.spread = spread;
        this.color = color;
        this.rgbaDifusion = rgbaDifusion;
//        particles = new LinkedList();
        
        active = true;
    }
    
    public Smoke(ParticleSystem system, Vector2 position, Vector2 velocity, Vector2 acceleration, int size, int density, int spread) {
        this(system, position, velocity, acceleration, size, DEFAULT_LIFESPAN, density, spread, Color.WHITE, CircleParticle.DEFAULT_RGBA_DEFUSION);
    }
    
    public Smoke(ParticleSystem system, Vector2 position, Vector2 velocity, Vector2 acceleration, int size, int density, int spread, Color color, float[] rgbaDifusion) {
        this(system, position, velocity, acceleration, size, DEFAULT_LIFESPAN, density, spread, color, rgbaDifusion);
    }
    
    public Smoke(ParticleSystem system, Vector2 position, Vector2 velocity, Vector2 acceleration, int size, float lifespan, int density, int spread, Color color, float[] rgbaDifusion) {
        super(system, position, velocity, size, lifespan, density);
        this.spread = spread;
        this.color = color;
        this.rgbaDifusion = rgbaDifusion;
        active = true;
    }
    
    public Smoke(ParticleSystem system, int size, int density, int spread) {
        this(system, new Vector2(200, 600), new Vector2(0, size), size, density, spread);
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void disable() {
        this.active = false;
    }
    
    public void enable()
    {
        this.active = true;
    }
    
    public void setCenterPosition(Vector2 position) {
        this.position = position;
    }

    public void setVelosity(Vector2 velocity) {
        this.velocity = velocity;
        if(!deceleration)
            acceleration = new Vector2(velocity).setLength(size);
        else{
            acceleration = new Vector2(velocity).scl(-1f/lifespan);
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRgbaDifusion(float[] rgbaDifusion) {
        this.rgbaDifusion = rgbaDifusion;
    }

    public void setDeceleration(boolean deceleration) {
        this.deceleration = deceleration;
    }
    
//    @Override
//    public void draw(Graphics2D g2d, int shiftX, int shiftY, float zoom) {
//        for(CircleParticle p: particles)
//        {
//            p.draw(g2d, shiftX, shiftY, zoom);
//        }      
//    }

    @Override
    public void update(float deltatime) {
        if(active)
        {
            for(int i = 0; i < density / DENSITY_DIVIDER + 1; i++)
            {
                if(rand.nextInt(DENSITY_RAND/density + 1) == 0)
                    system.addParticle(
                        new CircleParticle(
                            new Vector2(position),
                            new Vector2(velocity).rotate90(rand.nextInt(2) - 1).setLength(rand.nextFloat() * velocity.len()*spread/50).add(velocity),
                            acceleration,
                            (int)size,
                            lifespan,
                            4,
                            color,
                            rgbaDifusion
                        )
                    );
            }
        }
            
        if(!deceleration)
            acceleration = new Vector2(velocity).setLength(size);
        else
            acceleration = new Vector2(velocity).scl(1f/lifespan);
    }

    public static void main(String[] argv) throws InterruptedException
    {
        System.setProperty("sun.java2d.opengl", "true");
        ParticleSystem system = new ParticleSystem();
        Smoke smoke = new Smoke(system, new Vector2(200, 700), new Vector2(0, -200), 75, 15, 20, Color.WHITE, new float[]{4, 12, 25, 0.3f});

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 900));
        JPanel panel = new JPanel()
        {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                system.draw(g2d, 0, 0, 1);
            }
        };
        panel.setBackground(Color.GRAY);
        frame.add(panel);
        frame.setVisible(true);

        while(true)
        {
            smoke.update(10/1000f);
            system.update(10/1000f);
            Thread.sleep(10);
            frame.repaint();
        }
    }
}