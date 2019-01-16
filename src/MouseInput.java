package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static main.Game.gameState;
import main.Game.STATE;

public class MouseInput extends MouseAdapter{
    
    private Handler handler;
    private Camera camera;
    private Game game;
    private SpriteSheet ss;
    
    public MouseInput (Handler handler, Camera camera, Game game, SpriteSheet ss){
        this.handler = handler;
        this.camera = camera;
        this.game = game;
        this.ss = ss;
    }
   
    
     public void mousePressed(MouseEvent e){
     //getting coordinats of the mouse
     int mx = (int) e.getX() ;
     int my = (int) e.getY();
        //using mouse input in the menu
        if(gameState == Game.STATE.Menu){
            //when play button is pressed, new Game starts
            if(mouseOver(mx, my, 350, 200, 600, 64)){
               game.gameState = Game.STATE.Game;   
            }        
             //When help button pressed, Help window opens
            if(mouseOver(mx, my, 350, 350, 600, 64)){
               game.gameState = Game.STATE.Help;   
            }        
             //When exit button pressed, the game is closing
            if(mouseOver(mx, my, 350, 500, 600, 64)){
               System.exit(0);        
            }  
        //In the game
        }
        else if(gameState == Game.STATE.Game){
            //menu button
            if(mouseOver(mx, my, 30, 30, 100, 50)){
               game.gameState = Game.STATE.Menu; 
            }
        //in help section
        } else if(gameState == Game.STATE.Help){
            //back button takes user back tho the menu
            if(mouseOver(mx, my, 350, 500, 600, 64)){
               game.gameState = Game.STATE.Menu;   
            }        
        //when game over
        }else if(gameState == Game.STATE.Over || gameState == Game.STATE.Win){
            //quit
            if(mouseOver(mx, my, 40, 500, 250, 100)){
                System.exit(0);        
            }
            //start again
            if(mouseOver(mx, my, 980, 500, 250, 100)){
                game.gameState = Game.STATE.Game;           
                Game game= new Game();
            }
        }
      
        //moving with mouse pressed
        for(int i = 0; i < handler.object.size(); i++){
        GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Player){
                //up arrow moves player up
                if(mouseOver(mx, my, 1080, 400, 90, 90)){
                    handler.setUp(true);
                //down arrow
                }else if(mouseOver(mx, my, 1080, 570, 90, 90)){
                    handler.setDown(true);
                //left arrow
                }else if(mouseOver(mx, my, 1000, 490, 90, 90)){
                    handler.setLeft(true);
                //right arrow
                }else if(mouseOver(mx, my, 1170, 490, 90, 90)){
                    handler.setRight(true);
                //Fire button makes player shoot
                } else if(mouseOver(mx, my, 1075, 475, 100, 100)){
                    handler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+24, ID.Bullet, handler, ss));
                }
            }
        }
    }
     
    public void mouseReleased(MouseEvent e){
        int mx = (int) e.getX() ;
        int my = (int) e.getY();
            for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
                //when mouse is released player stops moving
                if(tempObject.getId() == ID.Player){
                    if(mouseOver(mx, my, 1080, 400, 90, 90)) handler.setUp(false);
                    if(mouseOver(mx, my, 1080, 570, 90, 90)) handler.setDown(false);
                    if(mouseOver(mx, my, 1000, 490, 90, 90)) handler.setLeft(false);
                    if(mouseOver(mx, my, 1170, 490, 90, 90)) handler.setRight(false);
                }            
            }
    }
     
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }else return false;
        }else return false;
    }
}
