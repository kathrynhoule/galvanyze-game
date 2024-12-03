package ca.grimlineage.galvanyze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GalvanyzeGame extends ApplicationAdapter {
     private SpriteBatch batch;

     private Texture playerSheet;
     private TextureRegion[] walkFrames;
     private Animation<TextureRegion> walkAnimation;
     private float animationTime;

     private float playerX, playerY;
     private float speed = 80;
 
     @Override
     public void create() {
         batch = new SpriteBatch();

         playerSheet = new Texture("ghost_walk_front.png");
        
          TextureRegion[][] tmpFrames = TextureRegion.split(playerSheet, 
               playerSheet.getWidth() / 4, playerSheet.getHeight());
          walkFrames = new TextureRegion[4];
          for (int i = 0; i < 4; i++) {
               walkFrames[i] = tmpFrames[0][i];
          }

          walkAnimation = new Animation<>(0.1f, walkFrames);
          animationTime = 0f;

          playerX = Gdx.graphics.getWidth() / 2f - walkFrames[0].getRegionWidth() / 2f;
          playerY = Gdx.graphics.getHeight() / 2f - walkFrames[0].getRegionHeight() / 2f;
     }
 
     @Override
     public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 


        animationTime += Gdx.graphics.getDeltaTime();
 
         float deltaTime = Gdx.graphics.getDeltaTime();
         boolean isWalking = false;
         if (Gdx.input.isKeyPressed(Input.Keys.W)) {
             playerY += speed * deltaTime;
             isWalking = true;
         }
         if (Gdx.input.isKeyPressed(Input.Keys.S)) {
             playerY -= speed * deltaTime;
             isWalking = true;
         }
         if (Gdx.input.isKeyPressed(Input.Keys.A)) {
             playerX -= speed * deltaTime;
             isWalking = true;
         }
         if (Gdx.input.isKeyPressed(Input.Keys.D)) {
             playerX += speed * deltaTime;
             isWalking = true;
         }
 
         TextureRegion currentFrame = walkFrames[0]; 
         if (isWalking) {
             currentFrame = walkAnimation.getKeyFrame(animationTime, true);
         }
 
         batch.begin();
         batch.draw(currentFrame, playerX, playerY);
         batch.end();
     }
 
     @Override
     public void dispose() {
         batch.dispose();
         playerSheet.dispose();
     }
}