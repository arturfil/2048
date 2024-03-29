package com.arturofilio.a2048.sprites;

import android.graphics.Canvas;
import com.arturofilio.a2048.TileManagerCallback;

public class Tile implements Sprite {

    private int screenWidth, screenHeight, standardSize;
    private TileManagerCallback callback;
    private int count = 1;
    private int currentX, currentY;
    private int destX, destY;
    private boolean moving = false;
    private int speed = 200;
    private boolean increment = false;

    public Tile(int standardSize, int screenWidth, int screenHeight, TileManagerCallback callback, int gridX, int gridY) {
        this.standardSize = standardSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.callback = callback;
        currentX = destX = screenWidth / 2 - 2 * standardSize + gridY * standardSize;
        currentY = destY = screenHeight / 2 - 2 * standardSize + gridX * standardSize;
    }

    public void move(int gridX, int gridY)  {
        moving = true;
        destX = screenWidth / 2 - 2 * standardSize + gridY * standardSize;
        destY = screenHeight / 2 - 2 * standardSize + gridX * standardSize;
    }

    public int getValue() {
        return count;
    }

    public Tile increment() {
        increment = true;
        return this;
    }

    public boolean toIncrement() {
        return increment;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(callback.getBitmap(count),currentX,currentY,null);
        if(moving && currentX == destX && currentY == destY) {
            moving = false;
            if(increment) {
                count++;
                increment = false;
            }
        }
    }

    @Override
    public void update() {
        if (currentX < destX) {
            if (currentX + speed > destX) {
                currentX = destX;
            } else {
                currentX += speed;
            }
        } else if (currentX > destX) {
            if (currentX - speed < destX) {
                currentX = destX;
            } else {
                currentX -= speed;
            }
        }

        if (currentY < destY) {
            if (currentY + speed > destY) {
                currentY = destY;
            } else {
                currentY += speed;
            }
        } else if (currentY > destY) {
            if (currentY - speed < destY) {
                currentY = destY;
            } else {
                currentY -= speed;
            }
        }
    }
}
