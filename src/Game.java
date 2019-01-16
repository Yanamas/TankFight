package main;

//main class

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
        
    //initilising classes in a game
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private SpriteSheet ss;

    private BufferedImage level = null;
    private BufferedImage sprite_sheet = null;
    private BufferedImage floor = null;
    private BufferedImage fire = null;
    private Menu menu;
    
    public int hp = 100;
    public int t_hp = 5;
    public int e_hp = 5;
    
     //initialising states of the game
    public static enum STATE{
      Menu,
      Help,
      Over,
      Game,
      Win
    };
    
    public static STATE gameState = STATE.Menu;    
    //constructor
    public Game(){
        //calling new window
        new Window(1280, 720, "Tank Fight", this);
        //call start method
        start();
        
        handler = new Handler();
        camera = new Camera(0, 0);
        menu = new Menu(this, handler);
        //adding keylistener to enable keys use
        this.addKeyListener(new KeyInput(handler)); 
        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/tank_level1.png");        
        sprite_sheet = loader.loadImage("/sprite_sheet.png");        
        ss = new SpriteSheet(sprite_sheet);
        fire = loader.loadImage("/fire.png");        
        floor = ss.grabImage(9, 1, 48, 48); 
        this.addMouseListener(new MouseInput(handler, camera, this, ss));        
        loadLevel(level);              
    }
    
    public void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    
    private void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    //run method for game loop
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 2000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta+=(now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();            
                delta--;
            }
            //if(isRunning)
            render();
            frames++;
        
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;            
            }
    }
        stop();
    }
    
    public void tick(){
        
        if(gameState == STATE.Game){
         //main tick method = updating everything
        
        for(int i = 0; i < handler.object.size(); i++){
            if(handler.object.get(i).getId() == ID.Player){
                camera.tick(handler.object.get(i));
            }
        }
        
        handler.tick();
          }else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Over || gameState == STATE.Win){
          menu.tick();
          }
        
    }
    
    //all the graphic goes here
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        ///////////// draw everything between these lines         
        if(gameState == STATE.Game){
            g2d.translate(-camera.getX(), -camera.getY());
            for(int xx = 0; xx < 30*72; xx+=48){
                for(int yy = 0; yy < 30*72; yy+=48){
                    g.drawImage(floor, xx, yy, this);
                }
            }
            handler.render(g);
            g2d.translate(camera.getX(), camera.getY());

            //menu button
            Font fnt2 = new Font("stencil", 1, 25);
            g.setFont(fnt2);
            g.setColor(Color.black);
            g.drawRect(50, 50, 100, 50);        
            g.drawString("MENU", 60, 80);

            //score
            Font fnt = new Font("stencil", 1, 35);
            g.setFont(fnt);
            g.setColor(Color.black);
            g.drawString("Score", 600, 50);
            g.drawString(""+t_hp+" : "+e_hp, 620, 100);

            //health bar
            g.setColor(Color.gray);
            g.fillRect(940,30, 200, 32);
            g.setColor(Color.cyan);
            g.fillRect(940,30, hp*2, 32);
            g.setColor(Color.black);
            g.drawRect(940,30, 200, 32);

            //fire and moving button
            g.drawImage(fire, 1000, 400, null);
        
         //when in menu - render the menu
        }else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Over || gameState == STATE.Win){
            menu.render(g);           
        }
        ///////////
        g.dispose();
        bs.show();
    
    }
    
    //loading the level
    void loadLevel(BufferedImage image){
        
     
             
            int w = image.getWidth();
        int h = image.getHeight();
       
        for(int xx = 0; xx < w; xx++){
            for(int yy = 0; yy < h; yy++){
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                //when colour is red draw the walls
                if(red == 255 && blue==0 && green == 0)
                    handler.addObject(new Block(xx*48, yy*48, ID.Block, ss));
                //when colour is dark red draw the wall around the game level
                if(red == 150 && blue==0 && green == 0)
                    handler.addObject(new Wall(xx*48, yy*48, ID.Wall, ss));
                //when colour is blue draw the player
                if(blue == 255 && green == 0  && red ==0)
                    handler.addObject(new Tank(xx*48, yy*48, ID.Player, handler, this, ss));
                //when green color on the level image draw the enemy that moves left and right
                if(green == 255 && blue == 0&& red ==0)
                    handler.addObject(new Enemy(xx*48, yy*48, ID.Enemy, handler, this, ss));
                //when yellow color on the level image draw the enemy that moves up and down
                 if(green == 255 && blue == 0 && red == 255)
                    handler.addObject(new Enemy1(xx*48, yy*48, ID.Enemy1, handler, this, ss));
               //when cyan colour draw the mines
                if(green == 255 && blue == 255 && red ==0)
                    handler.addObject(new Mine(xx*48, yy*48, ID.Mine, ss));
                //when the colour is pink draw lives
                if(red == 255 && blue == 255 && green == 0)
                    handler.addObject(new Life(xx*48, yy*48, ID.Life, ss));
            }
        }
    }
    public static void main (String args[]){
        //set to the constructor
        new Game();
    }
   
}
