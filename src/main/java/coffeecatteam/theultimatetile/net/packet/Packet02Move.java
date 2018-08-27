package coffeecatteam.theultimatetile.net.packet;

import coffeecatteam.theultimatetile.net.Client;
import coffeecatteam.theultimatetile.net.Server;
import coffeecatteam.theultimatetile.utils.Utils;

public class Packet02Move extends Packet {

    private String username;
    private float x, y;

    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Utils.parseFloat(dataArray[1]);
        this.y = Utils.parseFloat(dataArray[2]);
    }

    public Packet02Move(String username, float x, float y) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
    }

    @Override
    public void writeData(Client client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(Server server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("02" + this.username + "," + this.x + "," + this.y).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
