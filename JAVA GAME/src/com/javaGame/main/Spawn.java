package com.javaGame.main;

import java.util.Random;

public class Spawn
{
    private Handler handler;
    private HUD hud;
    private float scoreKeep = 0;
    private Random r = new Random();

    public Spawn(Handler handler, HUD hud)
    {
        this.handler = handler;
        this.hud = hud;
    }

    public void tick()
    {
        scoreKeep++;

        if (scoreKeep >= 1000)
        {
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);

            if (hud.getLevel() == 2)
            {
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy,handler));
            }
            else if (hud.getLevel() == 3)
            {
                handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy,handler));
            }
            else if (hud.getLevel() == 4)
            {
                handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy,handler));
            }
        }
    }
}
