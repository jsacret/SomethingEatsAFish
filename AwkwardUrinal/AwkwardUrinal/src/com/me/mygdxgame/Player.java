package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Sprite{
	float rotation = 0;
	float direction = 0;
	float rotationSpeed = 0;
	float speed = 0;
	float deaccel = .005f;

	public Player()
	{
		super();
		Texture texture = new Texture(Gdx.files.internal("data/Player.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion tx = new TextureRegion(texture, 0, 0, 128, 128);
		setRegion(tx);
	}
	
	public void rotateLeft()
	{
		rotationSpeed -= .3f;

	}
	
	public void rotateRight()
	{
		rotationSpeed += .3f;
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		super.draw(batch);
		update();
	}
	
	
	public void forward()
	{
		speed += .03f;
		if(speed > .7f)
		{
			speed = 2.5f;
		}
		direction = rotation;
	}
	
	public void backwards()
	{
		speed -= .001f;
	}
	
	public void update()
	{
		this.setY((float)(this.getY() + speed * Math.sin(Math.toRadians(direction))));
		this.setX((float)(this.getX() + speed * Math.cos(Math.toRadians(direction))));
		if(speed > .0002)
		{
			speed -= deaccel;
		}
		
		rotation += rotationSpeed;
		this.setRotation(rotation);
		
		if(rotationSpeed > 0)
		{
			rotationSpeed -= .1f;
		}
		
		if(rotationSpeed < 0)
		{
			rotationSpeed += .1f;
		}
		
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

	}

}
