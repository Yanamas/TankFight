package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;



public class EnemyBullet extends GameObject{

    private Handler handler;
   Enemy enemy;
    
   
    
    public EnemyBullet(int x, int y, ID id, Handler handler, int e_velX, int e_velY, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
     
  if(e_velX > 0)
         velX = 4;
     
      if(e_velX < 0)
         velX = -4;
      
       if(e_velY > 0)
         velY = 4;
       
        if(e_velY < 0)
         velY = -4; 
    }

   

    

    @Override
    public void tick() {
       x += velX;
       y += velY;
       
       //choose = r.nextInt(15);
       
       for(int i = 0; i < handler.object.size(); i++){
           GameObject tempObject= handler.object.get(i);
           
           if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Wall){
               if(getBounds().intersects(tempObject.getBounds())){
                   handler.removeObject(this);
               }
           } 
       }
    }

    @Override
    public void render(Graphics g) {
       g.setColor(Color.red);
       g.fillOval(x, y, 8, 8);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
       
    }
    
}
