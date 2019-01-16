package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
    
    Handler handler;
    SpriteSheet ss;
    
    public KeyInput(Handler handler){
        this.handler = handler;
        this.ss = ss;
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        for(int i = 0; i < handler.object.size(); i++){
            //looping all of the objects until finds the player
            GameObject tempObject = handler.object.get(i);
            //move when key is pressed
            if(tempObject.getId() == ID.Player){
                if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) handler.setUp(true);
                if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) handler.setDown(true);
                if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) handler.setLeft(true);
                if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) handler.setRight(true);
                //shooting on space key
                if(key == KeyEvent.VK_SPACE) handler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+24, ID.Bullet, handler,  ss));
            }            
        }
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.Player){
                //when key is released player stops moving
                if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) handler.setUp(false);
                if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) handler.setDown(false);
                if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) handler.setLeft(false);
                if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) handler.setRight(false);
            }            
        }
        
        
    }
}
