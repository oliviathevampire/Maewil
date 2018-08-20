package coffeecatteam.theultimatetile.entities.player;

import coffeecatteam.theultimatetile.Handler;

import java.net.InetAddress;

public class EntityPlayerMP extends EntityPlayer {

    private InetAddress ipAddress;
    private int port;

    public EntityPlayerMP(Handler handler, String username, InetAddress ipAddress, int port, boolean isLocal) {
        super(handler, username);
        renderName = true;
        this.ipAddress = ipAddress;
        this.port = port;
        this.isLocal = isLocal;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
