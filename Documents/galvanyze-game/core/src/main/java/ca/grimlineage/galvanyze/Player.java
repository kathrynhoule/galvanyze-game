package ca.grimlineage.galvanyze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private Texture playerSheet;
    private Animation<TextureRegion> walkFront, walkRight, walkLeft, walkBack;
    private float animationTime;
    private float x, y;
    private float speed = 80;
    private TextureRegion currentFrame;

    public Player(String texturePath, float initialX, float initialY) {
        playerSheet = new Texture("ghost_all_sprites.png");
        
        // sprite sheet to 2D array
        int frameCols = 4; // columns
        int frameRows = 4; // rows
        TextureRegion[][] tmpFrames = TextureRegion.split(
            playerSheet, 
            playerSheet.getWidth() / frameCols, 
            playerSheet.getHeight() / frameRows
        );

        // animations for each direction
        walkFront = new Animation<>(0.1f, tmpFrames[0]);
        walkRight = new Animation<>(0.1f, tmpFrames[1]);
        walkLeft = new Animation<>(0.1f, tmpFrames[2]);
        walkBack = new Animation<>(0.1f, tmpFrames[3]);

        animationTime = 0f;
        // default to first frame
        currentFrame = tmpFrames[0][0];

        x = initialX;
        y = initialY;
    }

    public void update(float deltaTime) {
     animationTime += deltaTime;
     boolean isWalking = false;

     // movement and animation
     if (Gdx.input.isKeyPressed(Input.Keys.W)) {
         y += speed * deltaTime;
         currentFrame = walkBack.getKeyFrame(animationTime, true);
         isWalking = true;
     }
     if (Gdx.input.isKeyPressed(Input.Keys.S)) {
         y -= speed * deltaTime;
         currentFrame = walkFront.getKeyFrame(animationTime, true);
         isWalking = true;
     }
     if (Gdx.input.isKeyPressed(Input.Keys.A)) {
         x -= speed * deltaTime;
         currentFrame = walkLeft.getKeyFrame(animationTime, true);
         isWalking = true;
     }
     if (Gdx.input.isKeyPressed(Input.Keys.D)) {
         x += speed * deltaTime;
         currentFrame = walkRight.getKeyFrame(animationTime, true);
         isWalking = true;
     }

     // reset to idle if not walking
     if (!isWalking) {
         animationTime = 0;
         if (currentFrame == walkBack.getKeyFrame(animationTime, true)) {
             currentFrame = walkBack.getKeyFrame(0);
         } else if (currentFrame == walkFront.getKeyFrame(animationTime, true)) {
             currentFrame = walkFront.getKeyFrame(0);
         } else if (currentFrame == walkLeft.getKeyFrame(animationTime, true)) {
             currentFrame = walkLeft.getKeyFrame(0);
         } else if (currentFrame == walkRight.getKeyFrame(animationTime, true)) {
             currentFrame = walkRight.getKeyFrame(0);
         }
     }
 }

 public void render(SpriteBatch batch) {
     batch.draw(currentFrame, x, y);
 }

 public void dispose() {
     playerSheet.dispose();
 }
}