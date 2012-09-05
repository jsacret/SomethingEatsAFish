package com.me.mygdxgame;

import java.util.LinkedList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

public class AwkwardUrinal implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private Player player;
	private Sprite left;
	private Sprite right;
	private Sprite up;
	private Astroid astroid1;
	private Astroid astroid2;
	private Astroid astroid3;
	private Sprite shoot;
	private LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	private LinkedList<Astroid> astroids = new LinkedList<Astroid>();
	int count = 5;
	
	@Override
	public void create() {		

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		camera.setToOrtho(true, w, h);
		batch = new SpriteBatch();
		
		
		texture = new Texture(Gdx.files.internal("data/bg.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 512);
		
		
		sprite = new Sprite(region);
		sprite.setSize(w, h);
		sprite.setOrigin(0,0);
		sprite.setPosition(0,0);
		
		player = new Player();
		player.setSize(32, 32);
		player.setOrigin(player.getWidth()/2, player.getHeight()/2);
		player.setPosition(w/2, h/2);
		
		Texture t1, t2, t3, t4;
		t1 = new Texture(Gdx.files.internal("data/left.png"));
		t2 = new Texture(Gdx.files.internal("data/right.png"));
		t3 = new Texture(Gdx.files.internal("data/up.png"));
		t4 = new Texture(Gdx.files.internal("data/shoot.png"));
		
		TextureRegion reg1 = new TextureRegion(t1, 0, 0, 64, 64);
		TextureRegion reg2 = new TextureRegion(t2, 0, 0, 64, 64);
		TextureRegion reg3 = new TextureRegion(t3, 0, 0, 64, 64);
		TextureRegion reg4 = new TextureRegion(t4, 0, 0, 64, 64);
		
		left = new Sprite(reg1);
		left.setSize(64, 64);
		left.setOrigin(left.getWidth()/2, left.getHeight()/2);
		left.setPosition(0, h - left.getHeight());

		right = new Sprite(reg2);
		right.setSize(64, 64);
		right.setOrigin(right.getWidth()/2, right.getHeight()/2);
		right.setPosition(right.getWidth(),  h - right.getHeight());
		
		up = new Sprite(reg3);
		up.setSize(64, 64);
		up.setOrigin(up.getWidth()/2, up.getHeight()/2);
		up.setPosition(up.getWidth()/2, h - up.getHeight()*2);
		up.flip(false, true);
		
		for(int i = 0; i < 5; i++)
			astroids.add(new Astroid());
		shoot = new Sprite(reg4);
		shoot.setSize(64, 64);
		shoot.setOrigin(shoot.getWidth()/2, shoot.getHeight()/2);
		shoot.setPosition(w - shoot.getWidth(),  h - shoot.getHeight());
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		for(int i = 0; i < 3; i++)
		if(Gdx.input.isTouched(i))
		{
			int x = Gdx.input.getX(i);
			int y = Gdx.input.getY(i);
			System.out.println(x + " " + y);
			if(left.getBoundingRectangle().contains(x, y))
			{
				player.rotateLeft();
			}
			if(right.getBoundingRectangle().contains(x, y))
			{
				player.rotateRight();
			}
			if(up.getBoundingRectangle().contains(x, y))
			{
				player.forward();
			}
			if(shoot.getBoundingRectangle().contains(Gdx.input.getX(i), Gdx.input.getY(i)) && (TimeUtils.millis() % 3) == 0)
			{
				new Bullet(player.rotation, player.getX() + player.getWidth()/2, player.getY() + player.getWidth()/2, bullets);
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			player.rotateLeft();
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			player.rotateRight();
		}
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			player.forward();
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			player.backwards();
		}
		if((Gdx.input.isKeyPressed(Keys.SPACE)) && (TimeUtils.millis() % 3) == 0)
		{
			new Bullet(player.rotation, player.getX() + player.getWidth()/2, player.getY() + player.getWidth()/2, bullets);
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
		left.draw(batch);
		right.draw(batch);
		up.draw(batch);
		shoot.draw(batch);
		LinkedList<Bullet> dead = new LinkedList<Bullet>();
		LinkedList<Astroid> deadA = new LinkedList<Astroid>();
		for(int i = 0; i < astroids.size(); i++)
		{
			astroids.get(i).draw(batch);
			if(astroids.get(i).getBoundingRectangle().contains(player.getBoundingRectangle()))
			{
				gameOver();
			}
		}
		for(int i = 0; i < bullets.size(); i++)
		{
			bullets.get(i).draw(batch);
		}
		for(int i = 0; i < bullets.size(); i++)
		for(int j = 0; j < astroids.size(); j++)
		{
			if(bullets.get(i) != null)
				if(bullets.get(i).getBoundingRectangle().overlaps(astroids.get(j).getBoundingRectangle()))
				{
					dead.add(bullets.get(i));
					astroids.get(j).takeDamage(5);
					if(astroids.get(j).life <= 0)
						deadA.add(astroids.get(j));
				}
		}
		for(int i = 0; i < dead.size(); i++)
		{
			bullets.remove(dead.get(i));
		}
		for(int i = 0; i < deadA.size(); i++)
		{
			astroids.remove(deadA.get(i));
		}
		player.draw(batch);
		batch.end();
		
		if(astroids.size() == 0)
		{
			count += 2;
			for(int i = 0; i < count; i++)
			{
				astroids.add(new Astroid());
			}
		}
	}
	
	public void gameOver()
	{
		astroids = new LinkedList<Astroid>();
		bullets = new LinkedList<Bullet>();
		player.setScale(10);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public void checkKeyboard()
	{
		
	}
}
