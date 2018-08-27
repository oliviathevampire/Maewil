package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIManager;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
import coffeecatteam.theultimatetile.utils.Logger;

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

    public StateMenuMultiplayer(Handler handlerIn, String username) {
        super(handlerIn);
        uiManager = new UIManager(handler);
        init();
        this.username = username;

        uiManager.addObject(new UIButton(x, ipBtnY, ipBtnWidth, ipBtnHeight, "Server IP", () -> {
            ip = JOptionPane.showInputDialog("Enter server ip:", ip);
            handler.getGame().getClient().setIpAddress(ip);
            Logger.print("IP set to [" + ip + "]");
        }));

        uiManager.addObject(new UIButton(x, handler.getHeight() - joinBtnHeight - 50, joinBtnWidth, joinBtnHeight, "Join Server", () -> {
            if (!ip.equalsIgnoreCase("")) {
//                    handler.getGame().getClient().sendData("ping");

                Logger.print("Joining Server [" + ip + "] as [" + this.username + "]");
                State.setState(new StateGame(handler));
                Packet00Login loginPacket = new Packet00Login(handler.getEntityManager().getPlayer().getUsername());
                if (handler.getGame().getServer() != null)
                    handler.getGame().getServer().addConnection((EntityPlayerMP) handler.getEntityManager().getPlayer(), loginPacket);
                loginPacket.writeData(handler.getGame().getClient());
            }
        }));

        uiManager.addObject(new UIButton(handler.getWidth() - backBtnWidth - x, handler.getHeight() - backBtnHeight - 50, backBtnWidth, backBtnHeight, "Back", () -> {
            State.setState(handler.getGame().stateMenu);
        }));
    }

    @Override
    public void init() {
        handler.getMouseManager().setUiManager(uiManager);
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
        Text.drawString(g, "Username: " + username, x, nameBtnY + nameBtnHeight + Text.getHeight(g, font), false, false, Color.white, font);
        Text.drawString(g, "IP: " + ip, x, ipBtnY + ipBtnHeight + Text.getHeight(g, font), false, false, Color.white, font);
    }
}
