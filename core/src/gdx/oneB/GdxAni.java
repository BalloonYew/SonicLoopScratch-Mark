package gdx.oneB;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GdxAni implements Screen {

    Game game;
    SpriteBatch batch;
    TextureAtlas textureAtlas;
    Animation aniLoop;
    float elapsedTime = 0;

    public GdxAni(Game _game) {
        game = _game;
        batch = new SpriteBatch();

        textureAtlas = new TextureAtlas(Gdx.files.internal("loop.atlas"));
        aniLoop = new Animation(1 / 15f, textureAtlas.getRegions());

//        textureAtlas1 = new TextureAtlas(Gdx.files.internal("duckUp.atlas"));
//        aniBird[2] = new Animation(1 / 10f, textureAtlas1.getRegions());



    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();

    }
    //@Override

    public void show() {
    }

    //@Override
    public void render(float delta) {



        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //idle animation


        batch.begin();

        elapsedTime += Gdx.graphics.getDeltaTime();

        batch.draw(aniLoop.getKeyFrame(elapsedTime, true), 400, 400);


        batch.end();
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

    @Override
    public void hide() {
    }
}