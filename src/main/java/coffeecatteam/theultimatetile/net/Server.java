package coffeecatteam.theultimatetile.net;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.net.packet.Packet;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
import coffeecatteam.theultimatetile.net.packet.Packet01Disconnect;
import coffeecatteam.theultimatetile.net.packet.Packet02Move;
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
    private TheUltimateTile theUltimateTile;
    private List<EntityPlayerMP> connectedPlayers = new ArrayList<>();

    public Server(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
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
        Packet packet;
        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                Logger.print("[" + address.getHostAddress() + ":" + port + "] " + ((Packet00Login) packet).getUsername() + " has connected!");
                EntityPlayerMP player = new EntityPlayerMP(theUltimateTile, ((Packet00Login) packet).getUsername(), address, port, false);
                this.addConnection(player, (Packet00Login) packet);
                break;
            case DISCONNECT:
                packet = new Packet01Disconnect(data);
                Logger.print("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect) packet).getUsername() + " has disconnected!");
                this.removeConnection((Packet01Disconnect) packet);
                break;
            case MOVE:
                packet = new Packet02Move(data);
                this.handleMove((Packet02Move) packet);
                break;
        }
    }

    private void handleMove(Packet02Move packet) {
        if (getPlayerMP(packet.getUsername()) != null) {
            int index = getPlayerMPIndex(packet.getUsername());
            this.connectedPlayers.get(index).setX(packet.getX());
            this.connectedPlayers.get(index).setY(packet.getY());
            packet.writeData(this);
        }
    }

    public void addConnection(EntityPlayerMP player, Packet00Login packet) {
        boolean connected = false;
        player.setX(theUltimateTile.getWorld().getSpawnX() * Tile.TILE_WIDTH);
        player.setY(theUltimateTile.getWorld().getSpawnY() * Tile.TILE_HEIGHT);
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

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
        packet.writeData(this);
    }

    public EntityPlayerMP getPlayerMP(String username) {
        for (EntityPlayerMP p : this.connectedPlayers)
            if (p.getUsername().equals(username))
                return p;
        return null;
    }

    public int getPlayerMPIndex(String username) {
        int index = 0;
        for (EntityPlayerMP p : this.connectedPlayers) {
            if (p.getUsername().equals(username))
                break;
            index++;
        }
        return index;
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
