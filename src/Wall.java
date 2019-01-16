package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Wall extends GameObject{
    
    private BufferedImage wall_image;

    public Wall(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);        
        wall_image = ss.grabImage(9, 1, 48, 48);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(wall_image, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle (x, y, 48,48);
        
    }
    
}
