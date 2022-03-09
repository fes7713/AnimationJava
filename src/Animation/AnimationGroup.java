/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animation;

import com.badlogic.gdx.math.Vector2;
import java.util.Random;

/**
 *
 * @author fes77
 */
public abstract class AnimationGroup extends Animation{
    protected ParticleSystem system;
    protected Random rand;
    protected int density;
    
    public final static int DEFAULT_DENSITY = 20;
    
    public AnimationGroup(ParticleSystem system, Vector2 position, Vector2 velocity, int size, float lifespan, int density) {
        super(position, velocity, size, lifespan);
        this.system = system;
        this.density = density;
        rand = new Random();
    }

    public AnimationGroup(ParticleSystem system, Vector2 position, Vector2 velocity, int size, int density) {
        this(system, position, velocity, size, DEFAULT_LIFESPAN, density);
    }
    
    public AnimationGroup(ParticleSystem system, Vector2 position, Vector2 velocity, int size, float lifespan) {
        this(system, position, velocity, size, lifespan, DEFAULT_DENSITY);
    }

    public AnimationGroup(ParticleSystem system, Vector2 position, Vector2 velocity, int size) {
        this(system, position, velocity, size, DEFAULT_LIFESPAN, DEFAULT_DENSITY);
    }

    
}
