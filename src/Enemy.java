package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.image.BufferedImage;
import static main.Game.gameState;

public class Enemy extends GameObject{
    
    private Handler handler;
    Game game;
    int e_velX = 2;
    int e_velY = 0;
    
    private BufferedImage[] enemy_image = new BufferedImage[4];
     private BufferedImage enemy_move;
     
     private int normalSpeed = 1000;
     private long time, lastTime;

    public Enemy(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        
        enemy_image[0] = ss.grabImage(1, 1, 48, 48);
        enemy_image[1] = ss.grabImage(2, 1, 48, 48);
        enemy_image[2] = ss.grabImage(3, 1, 48, 48);
        enemy_image[3] = ss.grabImage(4, 1, 48, 48);
               
        enemy_move = ss.grabImage(1, 1, 48, 48);
        
        time = 0;
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void tick() {
        x += e_velX;
        y += e_velY;
        
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
       
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject =handler.object.get(i);
            
            if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Wall){
                if(getBoundsBig().intersects(tempObject.getBounds())){
                 x += (e_velX*5) * -1;
               y += (e_velY*5) * -1;
                   e_velX *= -1;
                    e_velY *= -1;
                }

            }
          
            if(tempObject.getId() == ID.Bullet){
                if(getBounds().intersects(tempObject.getBounds())){
                    game.e_hp -= 1;
                    handler.removeObject(tempObject);
                    handler.removeObject(this);
                    game.t_hp+=2;
                }
                        
            }
            
            if(game.e_hp <=0){
                handler.removeObject(this);
                gameState = Game.STATE.Win;
            }
        }   
        if(time > normalSpeed){
             for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject =handler.object.get(i);
            
            if(tempObject.getId() == ID.Enemy){
                handler.addObject(new EnemyBullet(tempObject.getX()+16, tempObject.getY()+24, ID.EnemyBullet, handler, e_velX, e_velY, ss));                
                time = 0;            
             }
         }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(enemy_move, x, y, null);
       if(e_velX > 0)
           
       enemy_move = enemy_image[0];
       if(e_velY > 0)
         
       enemy_move = enemy_image[1];
       if(e_velX < 0)
          
       enemy_move = enemy_image[2];
       if(e_velY < 0)
          
       enemy_move = enemy_image[3];
          
        
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 48, 48);       
    }
    public Rectangle getBoundsBig() {
        return new Rectangle(x-24, y-24, 96, 96);       
    }
    
}
