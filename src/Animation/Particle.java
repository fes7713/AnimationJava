/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animation;

import Animation.Interface.Drawable;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

/**
 *
 * @author fes77
 */
public abstract class Particle extends Animation implements Drawable{

    protected static Random rand = new Random();
    
    public Particle(Vector2 position, Vector2 velocity, Vector2 acceleration, int size, float lifespan) {
        super(position, velocity, acceleration, size, lifespan);
    }
    
    public Particle(Vector2 position, Vector2 velocity, int size, float lifespan) {
        super(position, velocity, size, lifespan);
    }

    public Particle(Vector2 position, Vector2 velocity, int size) {
        super(position, velocity, size);
    }
    
}
