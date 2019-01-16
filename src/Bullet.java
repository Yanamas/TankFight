package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Bullet extends GameObject{

    private Handler handler;
    Game game;
        
    public Bullet(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        //when tank is fasing up it shoots up
        if(Tank.movement_image == Tank.tank_image[3]){
            velY = -8;
            velX = 0;
        }
        //down
        if(Tank.movement_image == Tank.tank_image[1]){
            velY = 8;
            velX = 0;
        }
        //left
         if(Tank.movement_image == Tank.tank_image[2]){
            velX = -8;
            velY = 0;
        }
        //right 
        if(Tank.movement_image == Tank.tank_image[0]){
            velX = 8;
            velY = 0;
        }    
    }    

    @Override
    public void tick() {
       x += velX;
       y += velY;
       //collusion
       for(int i = 0; i < handler.object.size(); i++){
           GameObject tempObject= handler.object.get(i);
           
           if(tempObject.getId() == ID.Block){
               if(getBounds().intersects(tempObject.getBounds())){
                   handler.removeObject(tempObject);
                   handler.removeObject(this);
               }
           }
           if(tempObject.getId() == ID.Wall){
               if(getBounds().intersects(tempObject.getBounds())){
                   handler.removeObject(this);
               }
           }
           
       }
    
    }

    @Override
    public void render(Graphics g) {
    	g.setColor(Color.red);
		g.fillRect(x, y, 8, 8);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
       
    }
    
}
