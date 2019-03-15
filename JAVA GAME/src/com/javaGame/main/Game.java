package com.javaGame.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable
{
    private static final long serialVersionUID = 2807496409684602144L;
    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    public boolean running = false;

    private Random r;
    private Handler handler;
    private HUD hud;
    private Spawn spawner;


    public Game() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);

        hud = new HUD();

        spawner = new Spawn(handler,hud);

        r = new Random();

        handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler));
        handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy,handler));
    }
    public static void main(String[] args)
    {
        new Game();
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            while (delta >= 1)
            {
                lastTime = now;
                tick();
                delta--;
            }
        if (running)
            render();
        frames++;

        if (System.currentTimeMillis() - timer > 1000) {
            timer += 1000;
            //System.out.println("FPS: " + frames);
            frames = 0;
        }
    }
    stop();
    }
    private void tick()
    {
        handler.tick();
        hud.tick();
        spawner.tick();
    }
    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        handler.render(g);

        hud.render(g);

        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max)
    {
        if (var >= max)
        {
            return var = max;
        }
        else if (var <= min)
        {
            return var = min;
        }
        else
            return var;
    }

    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public synchronized void stop()
    {
        try{
            thread.join();
            running = false;
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
