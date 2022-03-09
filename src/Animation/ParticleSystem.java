/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animation;

import Animation.Interface.Drawable;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author fes77
 */
public class ParticleSystem implements Drawable{
    List<Particle> particles;

    public ParticleSystem() {
        this.particles = new LinkedList();
    }
    
    public ParticleSystem(List<Particle> particles) {
        this.particles = particles;
    }
    
    public void update(float deltatime)
    {
        for(int i = 0; i < particles.size(); i++)
        {
            if(particles.get(i).isActive(deltatime))
                particles.get(i).update(deltatime);
            else
                particles.remove(particles.get(i));
        }
    }
    
    public void addParticle(Particle p)
    {
        particles.add(p);
    }

    @Override
    public void draw(Graphics2D g2d, int shiftX, int shiftY, float zoom) {
        for(int i = 0; i < particles.size(); i++)
            particles.get(i).draw(g2d, shiftX, shiftY, zoom);
    }

    public int size() {
        return particles.size();
    }
    
        
}
