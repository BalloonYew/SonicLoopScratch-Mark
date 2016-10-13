package gdx.ani.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gdx.oneB.GamOneB;
import gdx.oneB.GdxAni;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new GamOneB(), config);
        config.height = 800;
        config.width = 1000;
    }
}
