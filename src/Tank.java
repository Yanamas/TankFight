package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static main.Game.gameState;


public class Tank extends GameObject{
    
    private Handler handler;
    Game game;
    
    
    static BufferedImage[] tank_image = new BufferedImage[4];
    public static BufferedImage movement_image;
   
    //constructor
    public Tank(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        //choosing the tile for each instance
        tank_image[0] = ss.grabImage(5, 1, 48, 48);
        tank_image[1] = ss.grabImage(6, 1, 48, 48);
        tank_image[2] = ss.grabImage(7, 1, 48, 48);
        tank_image[3] = ss.grabImage(8, 1, 48, 48);
        
        movement_image = ss.grabImage(5, 1, 48, 48);
        
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        
        collision();
        
        //movement
        //moving up when key or button are pressed
        if(handler.isUp()){ 
            velY = -8;
            velX = 0;
        //stops moving up or down when up is realesed but down is not pressed    
        }else if(!handler.isDown()) velY = 0;
        //moving down
        if(handler.isDown()){
            velY = 8;
            velX = 0;
        }else if(!handler.isUp()) velY = 0;
        
        if(handler.isRight()) {
            velX = 8;        
            velY = 0;
        }else if(!handler.isLeft()) velX = 0;
        
        if(handler.isLeft()) {
            velX = -8;
            velY = 0;
        }else if(!handler.isRight()) velX = 0;
    }

    //collusion detection
    private void collision() {
    for(int i = 0; i <handler.object.size(); i++) {
    	GameObject tempObject = handler.object.get(i);
        //changing direction when collusion with the walls
    	if(tempObject.getId() == ID.Block || tempObject.getId() == ID.Wall) {
            if(getBounds().intersects(tempObject.getBounds())) {
    		x += velX * -1;
    		y += velY * -1;
            }
    	}
        //score goes down when tank gets caught on mine
        if(tempObject.getId() == ID.Mine){
            if(getBounds().intersects(tempObject.getBounds())){
                game.hp -= 20;
                game.t_hp -= 1;
                handler.removeObject(tempObject);
            }                 
        }
        //collection lives
        if(tempObject.getId() == ID.Life){
            if(getBounds().intersects(tempObject.getBounds())){
                game.hp += 30;
                handler.removeObject(tempObject);
            }            
        }
        //collusion with enemy's bullets
        if(tempObject.getId() == ID.EnemyBullet){
            if(getBounds().intersects(tempObject.getBounds())){
                game.hp -= 20;
                game.t_hp -=1;
                game.e_hp +=1;
                handler.removeObject(tempObject);
            }
        }
        if(tempObject.getId() == ID.Enemy1Bullet){
            if(getBounds().intersects(tempObject.getBounds())){
                handler.removeObject(tempObject);
                game.hp -= 20;
                game.t_hp -=1;
                game.e_hp +=1;
            }
        }
            //loosing the game
            if(game.hp<=0 || game.t_hp<=0) {
                handler.removeObject(this);
                gameState = Game.STATE.Over;
            }
            //winning the game
            if(game.t_hp>=10){
                handler.removeObject(this);
                gameState = Game.STATE.Win;                
            }
            if(game.hp>=100) game.hp=100;
    }
    }
    
    
    //drawing the tank
    public void render(Graphics g) {
        g.drawImage(movement_image, x, y, null);
        //setting up the image depending of which way the tank is facing
        if(velX > 0)
             movement_image = tank_image[0];
        if(velY > 0)
            movement_image = tank_image[1];       
        if(velX < 0)
          movement_image = tank_image[2];       
        if(velY < 0)
           movement_image = tank_image[3];
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 48, 48);
    }
    
}
