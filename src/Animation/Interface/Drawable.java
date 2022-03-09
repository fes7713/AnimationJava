/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Animation.Interface;

import java.awt.Graphics2D;

/**
 *
 * @author fes77
 */
public interface Drawable {
    public void draw(Graphics2D g2d, int shiftX, int shiftY, float zoom);
    public default void draw(Graphics2D g2d){
        draw(g2d, 0, 0, 1);
    }
}
