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
    Sprite SprSonic, Sprloop;
    Array<Path<Vector2>> paths = new Array<Path<Vector2>>();
    int currentPath = 0;
    float t;
    int mouseY;
    Vector2 vSonic = new Vector2();
    float x, y = 120, dDy, fSY, fSX, fBX = 50, fBY = 30, fSx, fDy, path;
    float speed = 15f;
    Vector2 vChar = new Vector2();

    @Override
    public void create() {
        vSonic.add(x, y);
        renderer = new ImmediateModeRenderer20(false, false, 0);
        Batch = new SpriteBatch();
        Sprloop = new Sprite(new Texture(Gdx.files.internal("loopdeloop.png")));
        Sprloop.setSize(510, 500);
        Sprloop.setOriginCenter();

        SprSonic = new Sprite(new Texture(Gdx.files.internal("5.png")));
        SprSonic.setSize(40, 40);
        SprSonic.setOriginCenter();

        //draws the points on the path
        Vector2 cpF[] = new Vector2[]{new Vector2(200, 120), new Vector2(200, 120), new Vector2(425, 130), new Vector2(523, 182), new Vector2(560, 290), new Vector2(529, 374), new Vector2(430, 420), new Vector2(324, 379), new Vector2(285, 275),
            new Vector2(330, 178), new Vector2(425, 130), new Vector2(650, 120), new Vector2(650, 120)};

        Vector2 cpB[] = new Vector2[]{new Vector2(650, 120), new Vector2(650, 120), new Vector2(425, 130), new Vector2(330, 178), new Vector2(285, 275), new Vector2(324, 379), new Vector2(430, 420), new Vector2(529, 374), new Vector2(560, 290),
            new Vector2(523, 182), new Vector2(425, 130), new Vector2(200, 120), new Vector2(200, 120),};


        paths.add(new CatmullRomSpline<Vector2>(cpF, false));
        paths.add(new CatmullRomSpline<Vector2>(cpB, false));

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

        if (vSonic.x >= 600) {
            currentPath = 1;
        } else if (vSonic.x <= 100) {
            currentPath = 0;
        }
        mouseY = -Gdx.input.getY() + 600;

        Sprloop.setPosition(190, -10);
        fSY = vChar.y;
        fSX = vChar.x;

        vSonic.add(fSx, fDy);
        SprSonic.setPosition(vSonic.x, vSonic.y);
        GL20 gl = Gdx.gl20;
        gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SprSonic.setRotation(0);

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

//if sonic hits the start of the loop start the loop animation forwards
        if (vSonic.x >= 100 && vSonic.x <= 150) {

//controls the speed that Sonic runs around the loop
            // t += (fSx/10) * Gdx.graphics.getDeltaTime();
            paths.get(currentPath).derivativeAt(tmpV, t);
            //t += speed * Gdx.graphics.getDeltaTime();
            t += (Gdx.graphics.getDeltaTime() * speed) / tmpV.len();


//             myCatmull.derivativeAt(out, current);
//    current += (Gdx.graphics.getDeltaTime() * speed) / out.len();

            paths.get(currentPath).valueAt(tmpV, t);
            //rotation
            paths.get(currentPath).derivativeAt(tmpV2, t);
            SprSonic.setRotation(tmpV2.angle());

            if (t > 1) {
                vSonic.x = 650;
                SprSonic.setPosition(vSonic.x, 120);
            } else {
                vSonic.x = 100;
                SprSonic.setPosition(tmpV.x, tmpV.y);

            }
            //entering loop backwards
        } else if (vSonic.x >= 300 && vSonic.x <= 600) {

//controls the speed that Sonic runs around the loop
            // t += (fSx/10) * Gdx.graphics.getDeltaTime();
            paths.get(currentPath).derivativeAt(tmpV, t);
            //t += speed * Gdx.graphics.getDeltaTime();
            t += (Gdx.graphics.getDeltaTime() * speed) / tmpV.len();

            paths.get(currentPath).valueAt(tmpV, t);
            //rotation
            paths.get(currentPath).derivativeAt(tmpV2, t);
            SprSonic.setRotation(tmpV2.angle()-180);

            if (t > 1) {
                vSonic.x = 100;
                SprSonic.setPosition(vSonic.x, 120);
            } else {
                vSonic.x = 600;
                SprSonic.setPosition(tmpV.x, tmpV.y);

            }
        } else {
            t = 0;
        }


        //checking all the variables
//        System.out.println(" mX " + Gdx.input.getX());
//        System.out.println(" mY " + mouseY);
//
        System.out.println("current path " + currentPath);
        System.out.println(t + " T-Value");
//        System.out.println(" X= " + tmpV.x);
//        System.out.println(" Y= " + tmpV.y);
//
//        System.out.println(" Speed " + fSx);
//
//        System.out.println(" X2= " + vSonic.x);
//        System.out.println(" Y2= " + vSonic.y);


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
        SprSonic.draw(Batch);
        Sprloop.draw(Batch);
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