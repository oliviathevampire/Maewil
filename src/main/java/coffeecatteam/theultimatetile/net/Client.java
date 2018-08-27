package coffeecatteam.theultimatetile.net;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.net.packet.Packet;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
import coffeecatteam.theultimatetile.net.packet.Packet01Disconnect;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Logger;

import java.io.IOException;
import java.net.*;

public class Client extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Handler handler;

    public Client(Handler handler) {
        this.handler = handler;
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());

//            String message = new String(packet.getData());
//            Logger.print("SERVER > " + message);
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet;
        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                Logger.print("[" + address.getHostAddress() + ":" + port + "] " + ((Packet00Login) packet).getUsername() + " has joined the game!");
                EntityPlayerMP player = new EntityPlayerMP(handler, ((Packet00Login) packet).getUsername(), address, port, false);
                player.setX(handler.getWorld().getSpawnX() * Tile.TILE_WIDTH);
                player.setY(handler.getWorld().getSpawnY() * Tile.TILE_HEIGHT);
                handler.getEntityManager().addEntity(player);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                Logger.print("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect) packet).getUsername() + " has disconnected!");
                handler.getEntityManager().removePlayerMP(((Packet01Disconnect) packet).getUsername());
                break;
        }
    }

    public void sendData(String data) {
        sendData(data.getBytes());
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 25565);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIpAddress(String ipAddress) {
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
