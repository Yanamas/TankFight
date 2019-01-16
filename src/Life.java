package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Life extends GameObject{
    
    private BufferedImage life_image;
    
    public Life(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);
        life_image = ss.grabImage(15, 1, 48, 48);
    }

    @Override
    public void tick() {
     
       
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(life_image, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 48, 48);
        
    }
    
}
