package coffeecatteam.theultimatetile.net;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.net.packet.Packet;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private DatagramSocket socket;
    private Handler handler;
    private List<EntityPlayerMP> connectedPlayers = new ArrayList<>();

    public Server(Handler handler) {
        this.handler = handler;
        try {
            this.socket = new DatagramSocket(25565);
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
//            Logger.print("CLIENT [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "]> "
//                    + message);
//            if (message.trim().equalsIgnoreCase("ping")) {
//                sendData("pong", packet.getAddress(), packet.getPort());
//            }
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                Packet00Login packet = new Packet00Login(data);
                Logger.print("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has connected!");
                EntityPlayerMP player = new EntityPlayerMP(handler, packet.getUsername(), address, port, false);
                this.addConnection(player, packet);
                break;
            case DISCONNECT:
                break;
        }
    }

    public void addConnection(EntityPlayerMP player, Packet00Login packet) {
        boolean connected = false;
        player.setX(handler.getWorld().getSpawnX() * Tile.TILE_WIDTH);
        player.setY(handler.getWorld().getSpawnY() * Tile.TILE_HEIGHT);
        for (EntityPlayerMP p : this.connectedPlayers) {
            if (player.getUsername().equals(p.getUsername())) {
                if (p.getIpAddress() == null)
                    p.setIpAddress(player.getIpAddress());
                if (p.getPort() == -1)
                    p.setPort(player.getPort());
                connected = true;
            } else {
                sendData(packet.getData(), p.getIpAddress(), p.getPort());

                packet = new Packet00Login(p.getUsername());
                sendData(packet.getData(), player.getIpAddress(), player.getPort());
            }
        }
        if (!connected) {
            this.connectedPlayers.add(player);
        }
    }

    public void sendData(String data, InetAddress ipAddress, int port) {
        sendData(data.getBytes(), ipAddress, port);
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (EntityPlayerMP p : connectedPlayers) {
            sendData(data, p.getIpAddress(), p.getPort());
        }
    }
}
