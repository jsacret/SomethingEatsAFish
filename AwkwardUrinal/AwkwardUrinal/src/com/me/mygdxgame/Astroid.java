package com.me.mygdxgame;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

public class Astroid extends Sprite{
	float rotation = 0;
	int xSpeed = 0;
	int ySpeed = 0;
	int life;
	public Astroid()
	{
		super();
		Random random = new Random();
		random.setSeed(TimeUtils.nanoTime());
		Texture texture = new Texture(Gdx.files.internal("data/astroid.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion tx = new TextureRegion(texture, 0, 0, 128, 128);
		setRegion(tx);
		this.setSize((random.nextInt() % 64) + 100, (random.nextInt() % 64) + 100);
		this.setPosition((random.nextInt() % 800) - 300, (random.nextInt() % 600) - 300);
		xSpeed = (random.nextInt() % 3) - 1;
		ySpeed = (random.nextInt() % 3) - 1;
		System.out.println(xSpeed + " " + ySpeed);
		rotation = (random.nextFloat() % 5f);
		life = 100;
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		super.draw(batch);
		this.setPosition(this.getX() + xSpeed, this.getY() + ySpeed);
		this.rotate(rotation);
		
		if(this.getY() > Gdx.graphics.getHeight() + this.getHeight())
		{
			this.setY(0  - this.getHeight());
		}
		if(this.getY() < 0 - this.getHeight())
		{
			this.setY(Gdx.graphics.getHeight() + this.getHeight());
		}
		if(this.getX() > Gdx.graphics.getWidth() + this.getWidth())
		{
			this.setX(0 - this.getWidth());
		}
		if(this.getX() < 0 - this.getWidth())
		{
			this.setX(Gdx.graphics.getWidth()  + this.getWidth());
		}
	}
	
	public void takeDamage(int damage)
	{
		life -= damage;
	}
	
	public void checkLife()
	{
		if(life < 0)
		{
			
		}
	}
	
}
