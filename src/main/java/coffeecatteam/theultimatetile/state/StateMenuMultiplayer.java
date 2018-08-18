package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIManager;
import coffeecatteam.theultimatetile.net.Client;
import coffeecatteam.theultimatetile.net.Server;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;

import javax.swing.*;
import java.awt.*;

public class StateMenuMultiplayer extends State {

    private UIManager uiManager;

    private String username = "Random_Player";
    private String ip = "localhost";

    private int x = 25;

    private int nameBtnWidth = 64 * 6;
    private int nameBtnHeight = 64;
    private int nameBtnY = 50;

    private int ipBtnWidth = 64 * 5;
    private int ipBtnHeight = 64;
    private int ipBtnY = handler.getHeight() / 2 - ipBtnHeight / 2;

    private int joinBtnWidth = 64 * 6;
    private int joinBtnHeight = 64;

    private int backBtnWidth = 64 * 3;
    private int backBtnHeight = 64;

    private Client client;
    private Server server;

    public StateMenuMultiplayer(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        init();

        uiManager.addObject(new UIButton(x, nameBtnY, nameBtnWidth, nameBtnHeight, "Username", () -> {
            username = Utils.getUsername(username);
            Logger.print("Username set to [" + username + "]");
        }));

        uiManager.addObject(new UIButton(x, ipBtnY, ipBtnWidth, ipBtnHeight, "Server IP", () -> {
            ip = JOptionPane.showInputDialog("Enter server ip:", ip);
            Logger.print("IP set to [" + ip + "]");
        }));

        uiManager.addObject(new UIButton(x, handler.getHeight() - joinBtnHeight - 50, joinBtnWidth, joinBtnHeight, "Join Server", () -> {
            handler.getMouseManager().setUiManager(null);
            if (!username.equalsIgnoreCase("")) {
                if (!ip.equalsIgnoreCase("")) {
                    Logger.print("Joining Server [" + ip + "] as [" + username + "]");
                    start();
                    client.sendData("ping".getBytes());
                }
            }
        }));

        uiManager.addObject(new UIButton(handler.getWidth() - backBtnWidth - x, handler.getHeight() - backBtnHeight - 50, backBtnWidth, backBtnHeight, "Back", () -> {
            handler.getMouseManager().setUiManager(null);
            State.setState(handler.getGame().stateMenu.init());
        }));
    }

    public synchronized void start() {
        if (JOptionPane.showConfirmDialog(handler.getGame(), "Do you want to host?") == 0) {
            server = new Server(handler.getGame());
            server.start();
        }

        client = new Client(handler.getGame(), ip);
        client.start();
    }

    @Override
    public State init() {
        handler.getMouseManager().setUiManager(uiManager);
        return this;
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, handler.getWidth(), handler.getHeight(), null);
        uiManager.render(g);

        Font font = Assets.FONT_30;
        Text.drawString(g, "Username: " + username, x, nameBtnY + nameBtnHeight + Text.getHeight(g, font), Color.white, font);
        Text.drawString(g, "IP: " + ip, x, ipBtnY + ipBtnHeight + Text.getHeight(g, font), Color.white, font);
    }
}
