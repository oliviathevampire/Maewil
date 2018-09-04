package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.manager.UIManager;
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
    private int ipBtnY = theUltimateTile.getHeight() / 2 - ipBtnHeight / 2;

    private int joinBtnWidth = 64 * 6;
    private int joinBtnHeight = 64;

    private int backBtnWidth = 64 * 3;
    private int backBtnHeight = 64;

    public StateMenuMultiplayer(TheUltimateTile theUltimateTileIn, String username) {
        super(theUltimateTileIn);
        uiManager = new UIManager(theUltimateTile);
        init();
        this.username = username;

        uiManager.addObject(new UIButton(x, ipBtnY, ipBtnWidth, ipBtnHeight, "Server IP", () -> {
            ip = JOptionPane.showInputDialog("Enter server ip:", ip);
            theUltimateTile.getClient().setIpAddress(ip);
            Logger.print("IP set to [" + ip + "]");
        }));

        String btnServerText = (theUltimateTile.isHosting()) ? "Host" : "Join";
        uiManager.addObject(new UIButton(x, theUltimateTile.getHeight() - joinBtnHeight - 50, joinBtnWidth, joinBtnHeight, btnServerText + " Server", () -> {
            if (!ip.equalsIgnoreCase("")) {
//                    theUltimateTile.getGame().getClient().sendData("ping");

                Logger.print("Joining Server [" + ip + "] as [" + this.username + "]");
                State.setState(new StateGame(theUltimateTile));
                Packet00Login loginPacket = new Packet00Login(theUltimateTile.getEntityManager().getPlayer().getUsername());
                if (theUltimateTile.getServer() != null)
                    theUltimateTile.getServer().addConnection((EntityPlayerMP) theUltimateTile.getEntityManager().getPlayer(), loginPacket);
                loginPacket.writeData(theUltimateTile.getClient());
            }
        }));

        uiManager.addObject(new UIButton(theUltimateTile.getWidth() - backBtnWidth - x, theUltimateTile.getHeight() - backBtnHeight - 50, backBtnWidth, backBtnHeight, "Back", () -> {
            State.setState(theUltimateTile.stateMenu);
        }));
    }

    @Override
    public void init() {
        theUltimateTile.getMouseManager().setUiManager(uiManager);
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight(), null);
        uiManager.render(g);

        Font font = Assets.FONT_30;
        Text.drawString(g, "Username: " + username, x, nameBtnY + nameBtnHeight + Text.getHeight(g, font), false, false, Color.white, font);
        Text.drawString(g, "IP: " + ip, x, ipBtnY + ipBtnHeight + Text.getHeight(g, font), false, false, Color.white, font);
    }
}
