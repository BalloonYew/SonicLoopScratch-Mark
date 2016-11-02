package gdx.oneB;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SonicLoop implements ApplicationListener {

    int SAMPLE_POINTS = 100;
    float SAMPLE_POINT_DISTANCE = 1f / SAMPLE_POINTS;
    SpriteBatch Batch;
    ImmediateModeRenderer20 renderer;
    Sprite obj;
    Array<Path<Vector2>> paths = new Array<Path<Vector2>>();
    int currentPath = 0;
    float t;
    Vector2 vSonic = new Vector2();
    float x, y = 50, dDy, fSY, fSX, fBX = 50, fBY = 30, fSx, fDy;
    float speed = 0.3f;
    Vector2 vChar = new Vector2();

    @Override
    public void create() {
        vSonic.add(x, y);
        renderer = new ImmediateModeRenderer20(false, false, 0);
        Batch = new SpriteBatch();
        obj = new Sprite(new Texture(Gdx.files.internal("5.png")));
        obj.setSize(40, 40);
        obj.setOriginCenter();

        //draws the points on the path
        Vector2 cp[] = new Vector2[]{new Vector2(100, 50), new Vector2(100, 50), new Vector2(400, 50), new Vector2(400, 250),
            new Vector2(200, 250), new Vector2(200, 50), new Vector2(500, 50), new Vector2(500, 50)};


        paths.add(new CatmullRomSpline<Vector2>(cp, false));

        pathLength = paths.get(currentPath).approxLength(500);
        avg_speed = speed * pathLength;

    }
    final Vector2 tmpV = new Vector2();
    final Vector2 tmpV2 = new Vector2();
    // final Vector2 tmpV3 = new Vector2();
    float pathLength;
    float avg_speed;

    @Override
    public void render() {
       
        fSY = vChar.y;
        fSX = vChar.x;

        vSonic.add(fSx, fDy);
        obj.setPosition(vSonic.x, vSonic.y);
        GL20 gl = Gdx.gl20;
         gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        obj.setRotation(0);

        //movement
        if (fSx > 0) {
            fSx -= 0.1;
        }
        if (fSx < 0) {
            fSx += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (fSx < 6) {
                fSx += 0.2;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (fSx > -6) {
                fSx -= 0.2;
            }
        }

//if sonic hits the start of the loop start the loop animation
        if (vSonic.x >= 100 && vSonic.x <= 150) {

//controls the speed that Sonic runs around the loop
            // t += (fSx/10) * Gdx.graphics.getDeltaTime();
            t += speed * Gdx.graphics.getDeltaTime();

            paths.get(currentPath).valueAt(tmpV, t);
            //rotation
            paths.get(currentPath).derivativeAt(tmpV2, t);
            obj.setRotation(tmpV2.angle());

            if (t > 1) {
                vSonic.x = 500;
                obj.setPosition(vSonic.x, vSonic.y);
            } else {
                vSonic.x = 100;
                obj.setPosition(tmpV.x, tmpV.y);

            }
        } else {
            t = 0;
        }



        System.out.println(t + " T-Value");
        System.out.println(" X= " + tmpV.x);
        System.out.println(" Y= " + tmpV.y);

        System.out.println(" Speed " + fSx);

        System.out.println(" X2= " + vSonic.x);
        System.out.println(" Y2= " + vSonic.y);


//draws the Path

        renderer.begin(Batch.getProjectionMatrix(), GL20.GL_LINE_STRIP);
        float val = 0f;
        while (val <= 1f) {
            renderer.color(0f, 0f, 0f, 1f);
            paths.get(currentPath).valueAt(/* out: */tmpV, val);
            renderer.vertex(tmpV.x, tmpV.y, 0);
            val += SAMPLE_POINT_DISTANCE;
        }
        renderer.end();

//draws Sonic
        Batch.begin();
        obj.draw(Batch);
        Batch.end();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
