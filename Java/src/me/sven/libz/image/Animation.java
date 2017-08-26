package me.sven.libz.image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Animation {

    private int spriteCount;                 // Counts ticks for change
    private int spriteDelay;                 // sprite delay 1-12 (You will have to play around with this)
    private int currentSprite;               // animations current sprite
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalsprites;                // total amount of sprites for your animation

    private boolean stopped;                // has animations stopped

    private List<Sprite> sprites = new ArrayList<Sprite>();    // Arraylist of sprites 

    public Animation(BufferedImage[] sprites, int spriteDelay) {
        this.spriteDelay = spriteDelay;
        this.stopped = true;

        for (int i = 0; i < sprites.length; i++) {
            addSprite(sprites[i], spriteDelay);
        }

        this.spriteCount = 0;
        this.spriteDelay = spriteDelay;
        this.currentSprite = 0;
        this.animationDirection = 1;
        this.totalsprites = this.sprites.size();

    }
    
    public Animation(ArrayList<BufferedImage> sprites, int spriteDelay) {
    	this.spriteDelay = spriteDelay;
        this.stopped = true;

        for (int i = 0; i < sprites.size(); i++) {
            addSprite(sprites.get(i), spriteDelay);
        }

        this.spriteCount = 0;
        this.spriteDelay = spriteDelay;
        this.currentSprite = 0;
        this.animationDirection = 1;
        this.totalsprites = this.sprites.size();
    }
    
    public void addSprite(Sprite sprite) {
    	sprites.add(sprite);
    	this.totalsprites = this.sprites.size();
    }

    public void start() {
        if (!stopped) {
            return;
        }

        if (sprites.size() == 0) {
            return;
        }

        stopped = false;
    }

    public void stop() {
        if (sprites.size() == 0) {
            return;
        }

        stopped = true;
    }

    public void restart() {
        if (sprites.size() == 0) {
            return;
        }

        stopped = false;
        currentSprite = 0;
    }

    public void reset() {
        this.stopped = true;
        this.spriteCount = 0;
        this.currentSprite = 0;
    }

    private void addSprite(BufferedImage sprite, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        sprites.add(new Sprite(sprite, duration));
        currentSprite = 0;
    }

    public Sprite getSprite() {
        return sprites.get(currentSprite);
    }

    public void update() {
        if (!stopped) {
            spriteCount++;

            if (spriteCount > spriteDelay) {
                spriteCount = 0;
                currentSprite += animationDirection;

                if (currentSprite > totalsprites - 1) {
                    currentSprite = 0;
                }
                else if (currentSprite < 0) {
                    currentSprite = totalsprites - 1;
                }
            }
        }

    }

}