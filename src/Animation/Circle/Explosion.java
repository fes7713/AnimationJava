/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animation.Circle;

import Animation.AnimationGroup;
import Animation.Fire.Spark;
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
public class Explosion extends AnimationGroup{
    protected Color color;
    
    
    protected Color orangeExplosion = new Color(255,149,24, 200);
    protected Color redExplosion = new Color(232,36,17, 200);
    protected Color yellowExplosion = new Color(234,234,16, 200);
    protected Color grayExplosion = new Color(75,75,75, 150);
    protected Color whiteExplosion = new Color(255,255,255, 100);
    
    
    

    public Explosion(ParticleSystem system, Vector2 position, Vector2 velocity, int size) {
        this(system, position, velocity, size, DEFAULT_LIFESPAN, DEFAULT_DENSITY);
    }
    
    public Explosion(ParticleSystem system, int size) {
        this(system, new Vector2(200, 600), new Vector2(0, 0), size, DEFAULT_LIFESPAN, DEFAULT_DENSITY);
    }
    
    public Explosion(ParticleSystem system, Vector2 position, Vector2 velocity, int size, float lifespan) {
        this(system, position, velocity, size, lifespan, DEFAULT_DENSITY);
    }
    
    public Explosion(ParticleSystem system, Vector2 position, Vector2 velocity, int size, float lifespan, int density) {
        super(system, position, velocity, size, lifespan, density);
        
        new Spark(system, position, velocity, size, lifespan, density >> 2, size << 3);
        for(int i = 0; i < density; i++)
        {
            int speed1 = size << 4;
            int speed2 = size  << 3;
            
            float lifespan1 = lifespan * 4;
            float lifespan2 = lifespan * 2;
            
            Vector2 v1 = new Vector2(rand.nextInt(speed1) - speed1 / 2, rand.nextInt(speed1) - speed1 / 2).add(velocity);
            Vector2 a1 = new Vector2(v1).scl(-1/lifespan1);
            Vector2 v2 = new Vector2(rand.nextInt(speed2) - speed2 / 2, rand.nextInt(speed2) - speed2 / 2).add(velocity);
            Vector2 a2 = new Vector2(v2).scl(-1/lifespan2);
            
            
            system.addParticle(
                new AnimeCircle(
                    new Vector2(position), 
                    v1,
                    a1,
                    size,
                    lifespan, 
                    0.5f,
                    new float []{2, 6, 8, 0.3f})
            );
            system.addParticle(
                new AnimeCircle(
                    new Vector2(position), 
                    v2,
                    a2,
                    size,
                    lifespan, 
                    0.5f,
                    new float []{1, 3, 6, 0.3f})
            );
            
        }
        
        int speed3 = size << 2;
        float lifespan3 = lifespan / 16;
        Vector2 v3 = new Vector2(rand.nextInt(speed3) - speed3 / 2, rand.nextInt(speed3) - speed3 / 2).add(velocity);
        Vector2 a3 = new Vector2(v3).scl(-1/lifespan3);
        system.addParticle(
                new AnimeCircle(
                    new Vector2(position), 
                    v3,
                    a3,
                    size * 2,
                    lifespan3, 
                    0.25f,
                    new Color(255, 243, 100),
                    new float []{0, 0, 0, 0.9f}));
        
        
    }

    public static void main(String[] argv) throws InterruptedException
    {
        ParticleSystem system = new ParticleSystem();
        
        System.setProperty("sun.java2d.opengl", "true");
        Explosion exp = new Explosion(system, new Vector2(500, 400), new Vector2(0, 0), 50, 1f);
//        new Explosion(system, new Vector2(200, 500), new Vector2(0, 0), 50, 1f);
        
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1000, 1000));
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
            system.update(10/1000f);
            System.out.println("Particles : " + system.size());
            Thread.sleep(10);
            frame.repaint();
        }
    }
}
//int speed1 = size * 2;
//                int speed2 = size * 2;
//                int speed3 = size * 1;
//                float lifespan1 = lifespan * 4;
//                float lifespan2 = lifespan * 2;
//                float lifespan3 = lifespan;
//                Vector2 v1 = new Vector2(rand.nextInt(speed1) - speed1 / 2, rand.nextInt(speed1) - speed1 / 2);
//                Vector2 a1 = new Vector2(v1).rotate(180).scl(1/lifespan1);
//                Vector2 v2 = new Vector2(rand.nextInt(speed2) - speed2 / 2, rand.nextInt(speed2) - speed2 / 2);
//                Vector2 a2 = new Vector2(v2).rotate(180).scl(1/lifespan2);
//                Vector2 v3 = new Vector2(rand.nextInt(speed3) - speed3 / 2, rand.nextInt(speed3) - speed3 / 2);
//                Vector2 a3 = new Vector2(v3).rotate(180).scl(1/lifespan3);
//                particles.add(
//                    new Particle(
//                        new Vector2(position), 
//                        v1,
//                        a1,
//                        lifespan1, 
//                        size,
//                        2,
//                        new float []{2, 4, 8, 0.3f}));
//                particles.add(
//                    new Particle(
//                        new Vector2(position), 
//                        v2,
//                        a2,
//                        lifespan2, 
//                        size,
//                        2,
//                        new float []{1, 8, 16, 0.3f}));
//                particles.add(
//                    new Particle(
//                        new Vector2(position), 
//                        v3,
//                        a3,
//                        lifespan3, 
//                        size,
//                        2,
//                        new Color(200, 170, 0),
//                        new float []{0, 0, 0, 0.5f}));