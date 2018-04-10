package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion imgBall;
	TextureRegion imgBackGround;
	TextureRegion imgPlayer;

	float gravity = -0.2f;

	Array<Ball> balls = new Array<Ball>();
	Player player;
	float newBallTimer;


	@Override
	public void create () {
		batch = new SpriteBatch();
		TextureAtlas textureAtlas = new TextureAtlas("superpang.txt");
		imgBall = textureAtlas.findRegion("ball0");
		imgBackGround = textureAtlas.findRegion("background");
		imgPlayer = textureAtlas.findRegion("shoot");

		player = new Player();
		balls.add(new Ball (10,10,1,12));
		balls.add(new Ball (100,10,2,6));
		balls.add(new Ball (10,100,3,14));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update();

		batch.begin();
		batch.draw(imgBackGround, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		for(Ball ball : balls) {
			batch.draw(imgBall, ball.position.x, ball.position.y);
		}
		batch.draw(imgPlayer, player.position.x, player.position.y);
		batch.end();

	}

	void update(){
		updateTimers();
		addNewBall();
		updateBalls();
		updatePlayer();
	}

	void updateTimers(){
		newBallTimer += Gdx.graphics.getDeltaTime();
	}

	void addNewBall(){
		if(newBallTimer >2){
			balls.add(new Ball (10,100,3,14));
			newBallTimer = 0;
		}
	}


	void updateBalls(){
		for(Ball ball : balls) {

			ball.velocity.y += gravity;

			ball.position.y += ball.velocity.y;
			ball.position.x += ball.velocity.x;

			if (ball.position.y < 0) {
				ball.velocity.y = ball.reboundSpeed;
			}

			if (ball.position.x < 0 || ball.position.x > Gdx.graphics.getWidth() - imgBall.getRegionWidth()) {
				ball.velocity.x *= -1;
			}
		}
	}

	void updatePlayer(){
		if(Controls.isLeftPressed()){
			player.position.x -= player.velocity.x;
		}

		if(Controls.isRightPressed()){
			player.position.x += player.velocity.x;
		}
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}
