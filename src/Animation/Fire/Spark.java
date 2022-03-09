/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animation.Fire;

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
public class Spark extends AnimationGroup{
    public Spark(ParticleSystem system, Vector2 position, Vector2 velocity, int size, int density) {
        this(system, position, velocity, DEFAULT_LIFESPAN, size, density);
    }

    public Spark(ParticleSystem system, Vector2 position, Vector2 velocity, int size, float lifespan) {
        this(system, position, velocity, size, lifespan, DEFAULT_DENSITY);
    }

    public Spark(ParticleSystem system, Vector2 position, Vector2 velocity, int size) {
        this(system, position, velocity, DEFAULT_LIFESPAN, DEFAULT_DENSITY, size);
    }
    
    public Spark(ParticleSystem system, Vector2 position, Vector2 velocity, int size, float lifespan, int density) {
        this(system, position, velocity, size, lifespan, density, size << 5);
    }
    
    public Spark(ParticleSystem system, Vector2 position, Vector2 velocity, int size, float lifespan, int density, int speed) {
        super(system, position, velocity, size, lifespan, density);
        for(int i = 0; i < density; i++){
            system.addParticle(
                    new FireParticle(
                            new Vector2(position), 
                            new Vector2(speed, 0).rotate(rand.nextInt(360)).add(velocity),
                            size, 1));
        }
    }
    
    public static void main(String[] args) throws InterruptedException
    {
        ParticleSystem system = new ParticleSystem();
        
        System.setProperty("sun.java2d.opengl", "true");
        Spark spk = new Spark(system, new Vector2(500, 300), new Vector2(-200, 0), 50, 1f, 20, 1200);
        
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
            Thread.sleep(10);
            frame.repaint();
        }
    }
}
