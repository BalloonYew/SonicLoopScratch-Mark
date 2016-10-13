/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.oneB;

import com.badlogic.gdx.Game;

public class GamOneB extends Game {

    @Override
    public void create() {
        this.setScreen(new GdxAni(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
