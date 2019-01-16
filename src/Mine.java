package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Mine extends GameObject{
    
    private BufferedImage mine_image;
    
    public Mine(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);
        mine_image = ss.grabImage(11, 1, 48, 48);
    }

    @Override
    public void tick() {
     
       
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(mine_image, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 48, 48);
        
    }
    
}
