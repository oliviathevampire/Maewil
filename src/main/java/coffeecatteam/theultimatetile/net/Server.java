package coffeecatteam.theultimatetile.net;

import coffeecatteam.theultimatetile.Game;
import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayerMP;
import coffeecatteam.theultimatetile.net.packet.Packet;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
import coffeecatteam.theultimatetile.utils.Logger;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private DatagramSocket socket;

    private Handler handler;
    private Game game;
    private List<EntityPlayerMP> connectedPlayers = new ArrayList<>();

    public Server(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
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
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
//            String message = new String(packet.getData());
//            Logger.print("Client [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] > " + message);
//            if (message.trim().equalsIgnoreCase("ping"))
//                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        switch (type) {
            case INVALID:
                break;
            case LOGIN:
                Packet00Login packet = new Packet00Login(data);
                Logger.print("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has connected!");
                EntityPlayerMP player;
                if (address.getHostAddress().equalsIgnoreCase("127.0.0.1"))
                    player = new EntityPlayerMP(handler, "player", packet.getUsername(), address, port, true);
                else
                    player = new EntityPlayerMP(handler, "player", packet.getUsername(), address, port, false);
//                player.setX(handler.getWorld().getSpawnX() + 1);
//                player.setY(handler.getWorld().getSpawnY());
                this.connectedPlayers.add(player);
                game.setPlayer(player);
                break;
            case DISCONECT:
                break;
        }
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
