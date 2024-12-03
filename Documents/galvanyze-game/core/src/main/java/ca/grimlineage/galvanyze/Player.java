package ca.grimlineage.galvanyze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private Texture playerSheet;
    private TextureRegion[] walkFrames;
    private Animation<TextureRegion> walkAnimation;
    private float animationTime;
    private float x, y;
    private float speed = 80;

    public Player(String texturePath, float initialX, float initialY) {
        playerSheet = new Texture("ghost_walk_front.png");
        
        // split sprite sheet into frames
        TextureRegion[][] tmpFrames = TextureRegion.split(playerSheet, playerSheet.getWidth() / 4, playerSheet.getHeight());
        walkFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            walkFrames[i] = tmpFrames[0][i];
        }

        walkAnimation = new Animation<>(0.1f, walkFrames);
        animationTime = 0f;

        x = initialX;
        y = initialY;
    }

    public void update(float deltaTime) {
        animationTime += deltaTime;
        
        // movement keys
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) y += speed * deltaTime;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) y -= speed * deltaTime;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) x -= speed * deltaTime;
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) x += speed * deltaTime;
    }

    public void render(SpriteBatch batch) {

        TextureRegion currentFrame = walkFrames[0];
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S) || 
            Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
            currentFrame = walkAnimation.getKeyFrame(animationTime, true);
        }
        
        batch.draw(currentFrame, x, y);
    }

    public void dispose() {
        playerSheet.dispose();
    }
}