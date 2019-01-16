package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
    
    private BufferedImage image;
    //Method used to load images into the game
    public BufferedImage loadImage(String path){
        try { 
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
           e.printStackTrace();
        }
    
       return image;
    }
        
}
