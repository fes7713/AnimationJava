/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Animation;

/**
 *
 * @author fes77
 */


import com.badlogic.gdx.math.Vector2;


/**
 *
 * @author fes77
 */
public abstract class Animation{
    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;
    
    protected float lifespan;
    protected float size;
    
    public final static int DEFAULT_LIFESPAN = 4;

    public Animation(Vector2 position, Vector2 velocity, Vector2 acceleration, int size, float lifespan) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.lifespan = lifespan;
        this.size = size;
    }

    public Animation(Vector2 position, Vector2 velocity, int size, float lifespan) {
        this(position, velocity, new Vector2(velocity).scl(-1/lifespan), size, lifespan);
    }

    public Animation(Vector2 position, Vector2 velocity, int size) {
        this(position, velocity, new Vector2(velocity).scl(-1/DEFAULT_LIFESPAN), size, DEFAULT_LIFESPAN);
    }
    
    public void update(float deltatime)
    {
        velocity.add(acceleration.x * deltatime, acceleration.y * deltatime);
        position.add(velocity.x * deltatime, velocity.y * deltatime);
        
        lifespan -= deltatime;
    };
    
    
    public boolean isActive(float deltatime)
    {
        return lifespan > deltatime;
    }
    
}
