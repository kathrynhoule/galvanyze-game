package ca.grimlineage.galvanyze;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GalvanyzeGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    @Override
    public void create() {

        int startTileX = 8;
        int startTileY = 14;
        int tileWidth = 16;
        int tileHeight = 16;

        float startX = startTileX * tileWidth;
        float startY = startTileY * tileHeight;

        batch = new SpriteBatch();
        player = new Player("ghost_all_sprites.png", startX, startY);
    
        tiledMap = new TmxMapLoader().load("maps/ghostHome.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // for camera position to follow player
        camera.position.set(player.getX(), player.getY(), 0);
        camera.zoom = 0.25f;
        camera.update();
        mapRenderer.setView(camera);

        mapRenderer.render();

        float deltaTime = Gdx.graphics.getDeltaTime();
        
        player.update(deltaTime);
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        tiledMap.dispose();
        mapRenderer.dispose();
    }
}