package team.abnormals.tut_new.game;

import team.abnormals.tut_new.engine.Engine;
import team.abnormals.tut_new.engine.Game;

public class TheUltimateTile {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            Game gameLogic = new DummyGame();
            Engine gameEng = new Engine("The Ultimate Tile", 600, 480, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }

}
