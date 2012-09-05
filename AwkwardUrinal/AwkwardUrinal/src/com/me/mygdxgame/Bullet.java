package com.me.mygdxgame;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Bullet extends Sprite{
	
	float rotation = 0;
	float speed = 5;
	float xVel = 0;
	float yVel = 0;
	long time = 0;
	LinkedList<Bullet> bullets;
	
	public Bullet(float Rotation, float x, float y, LinkedList<Bullet> Bullets)
	{
		super();
		Texture texture = new Texture(Gdx.files.internal("data/bullet.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion tx = new TextureRegion(texture, 0, 0, 8, 2);
		setRegion(tx);
		
		this.setSize(2, 6);
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
		this.setPosition(x, y);
		
		this.setRotation(Rotation - 90);
		xVel = (float) (Math.cos(Math.toRadians(Rotation)) * speed);
		yVel = (float) (Math.sin(Math.toRadians(Rotation)) * speed);
		time = TimeUtils.millis();
		bullets = Bullets;
		bullets.add(this);
	
	}
	@Override
	public void draw(SpriteBatch batch)
	{
		super.draw(batch);
		this.setPosition(this.getX() + xVel, this.getY() + yVel);

		
		if(this.getY() > Gdx.graphics.getHeight())
		{
			this.setY(0);
		}
		if(this.getY() < 0)
		{
			this.setY(Gdx.graphics.getHeight());
		}
		if(this.getX() > Gdx.graphics.getWidth())
		{
			this.setX(0);
		}
		if(this.getX() < 0)
		{
			this.setX(Gdx.graphics.getWidth());
		}
		
		Timer();
		
	}
	
	public void Timer()
	{
		if(TimeUtils.millis() - time > 1000)
		{
			Kill();
		}
	}
	
	public void Kill()
	{
		bullets.remove(this);
	}
	
	
	

}
