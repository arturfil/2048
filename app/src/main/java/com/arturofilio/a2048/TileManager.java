package com.arturofilio.a2048;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.arturofilio.a2048.sprites.Sprite;
import com.arturofilio.a2048.sprites.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TileManager implements TileManagerCallback, Sprite {

    private Resources resources;
    private int standardSize, screenWidth, screenHeight;
    private ArrayList<Integer> drawables = new ArrayList<>();
    private HashMap<Integer, Bitmap> tileBitmaps = new HashMap<>();
    private Tile[][] grid = new Tile[4][4];
    private boolean moving = false;
    private ArrayList<Tile> movingTiles = new ArrayList<>();

    public TileManager(Resources resources, int standardSize, int screenWidth, int screenHeight) {
        this.resources = resources;
        this.standardSize = standardSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        initBitmpas();

        initGame();

    }

    private void initBitmpas() {
        drawables.add(R.drawable.one);
        drawables.add(R.drawable.two);
        drawables.add(R.drawable.three);
        drawables.add(R.drawable.four);
        drawables.add(R.drawable.five);
        drawables.add(R.drawable.six);
        drawables.add(R.drawable.seven);
        drawables.add(R.drawable.eight);
        drawables.add(R.drawable.nine);
        drawables.add(R.drawable.ten);
        drawables.add(R.drawable.eleven);
        drawables.add(R.drawable.twelve);
        drawables.add(R.drawable.thirteen);
        drawables.add(R.drawable.fourteen);
        drawables.add(R.drawable.fifteen);
        drawables.add(R.drawable.sixteen);

        for(int i = 1; i <= 16; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(resources, drawables.get(i-1));
            Bitmap tileBmp = Bitmap.createScaledBitmap(bmp, standardSize,standardSize, false);
            tileBitmaps.put(i, tileBmp);
        }
    }

    private void initGame() {
        grid = new Tile[4][4];
        movingTiles = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            int x = new Random().nextInt(4);
            int y = new Random().nextInt(4);
            if(grid[x][y] == null) {
                Tile tile = new Tile(standardSize, screenWidth, screenHeight, this, x, y);
                grid[x][y] = tile;
            }  else {
                i--;
            }
        }
    }

    @Override
    public Bitmap getBitmap(int count) {
        return tileBitmaps.get(count);
    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(grid[i][j] != null) {
                    grid[i][j].draw(canvas);
                }
            }
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(grid[i][j] != null) {
                    grid[i][j].update();
                }
            }
        }
    }

    public void onSwipe(SwipeCallback.Direction direction) {
        if(!moving) {
            moving = true;
            Tile[][] newGrid = new Tile[4][4];

            switch (direction) {
                case UP:
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (grid[i][j] != null) {
                                newGrid[i][j] = grid[i][j];
                                for (int k = i - 1; k >= 0 ; k--) {
                                    if(newGrid[k][j] != null) {
                                        newGrid[k][j] = grid[i][j];
                                        if(newGrid[k+1][j] == grid[i][j]) {
                                            newGrid[k+1][j] = null;
                                        }
                                    } else if (newGrid[k][j].getValue() == grid[i][j].getValue() &&
                                            !newGrid[k][j].toIncrement()){
                                        newGrid[k][j] = grid[i][j].increment();
                                        if(newGrid[k+1][j]==grid[i][j]) {
                                            newGrid[k+1][j] = null;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            Tile t = grid[i][j];
                            Tile newT = null;
                            int gridX = 0;
                            int gridY = 0;
                            for (int k = 0; k < 4; k++) {
                                for (int l = 0; l < 4; l++) {
                                    if(newGrid[k][l] == t) {
                                        newT = newGrid[k][l];
                                        gridX = k;
                                        gridY = l;
                                        break;
                                    }
                                }
                            }
                            if(newT != null) {
                                movingTiles.add(t);
                                t.move(gridX, gridY);
                            }
                        }
                    }
                    break;
                case DOWN:
                    break;
                case LEFT:
                    break;
                case RIGHT:
                    break;
            }
            grid = newGrid;
        }
    }

}
