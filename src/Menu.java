package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Game.STATE;
import static main.Game.gameState;


public class Menu{
    
    
    private Game game;
    private Handler handler;
   // private Menu menu;
    
    private BufferedImage help = null;
    private BufferedImage win = null;
    private BufferedImage over = null;
    BufferedImageLoader loader = new BufferedImageLoader();
    
    
    public Menu(Game game, Handler handler){
        this.game = game;
        this.handler = handler;
        
    }
    
 
    
    public void tick(){
        
    }    
    
    public void render(Graphics g){
        help = loader.loadImage("/help.png");
        win = loader.loadImage("/win.png");
        over = loader.loadImage("/over.png");
        if(gameState == STATE.Menu){
            //draw new window
            g.setColor(new Color(183, 156, 128));
            g.fillRect(1, 1, 1280, 720);
            Font fnt = new Font("stencil", 1, 80);
            Font fnt2 = new Font("stencil", 1, 50);

            g.setFont(fnt);
            g.setColor(Color.black);
            g.drawString("MAIN MENU", 400, 100);

            g.setFont(fnt2);
            g.drawRect(350, 200, 600, 64);
            g.drawString("PLAY", 580, 250);

            g.drawRect(350, 350, 600, 64);
            g.drawString("HELP", 580, 400);

            g.drawRect(350, 500, 600, 64);        
            g.drawString("QUIT", 580, 550);
        }else if(gameState == STATE.Help){
            //draw new window
            g.drawImage(help, 1, 1, null);
            Font fnt2 = new Font("stencil", 1, 50);
            g.setFont(fnt2);
            g.setColor(Color.black);
            g.drawRect(350, 500, 600, 64);        
            g.drawString("BACK", 580, 550);   
        
        }else if(gameState == STATE.Over){
            g.drawImage(over, 1, 1, null);
            g.setColor(Color.white);
            g.fillRect(40, 500, 250, 100);
            g.fillRect(980, 500, 250, 100);
            Font fnt2 = new Font("stencil", 1, 40);
            g.setFont(fnt2);
            g.setColor(Color.red);
            g.drawString("QUIT", 100, 570);
            g.drawString("TRY", 1050, 540);
            g.drawString("AGAIN", 1040, 590);
        }else if(gameState == STATE.Win){
            //Win display
            g.drawImage(win, 1, 1, null);
            g.setColor(Color.white);
            g.fillRect(40, 500, 250, 100);
            g.fillRect(980, 500, 250, 100);
            Font fnt2 = new Font("stencil", 1, 40);
            g.setFont(fnt2);
            g.setColor(Color.red);
            g.drawString("QUIT", 100, 570);
            g.drawString("PLAY", 1050, 540);
            g.drawString("AGAIN", 1040, 590);
            }    
    }
}