package coffeecatteam.theultimatetile.net;

import coffeecatteam.theultimatetile.Game;
<<<<<<< HEAD
import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.net.packet.Packet;
import coffeecatteam.theultimatetile.net.packet.Packet00Login;
import coffeecatteam.theultimatetile.utils.Logger;
=======
>>>>>>> parent of 8ca733f... Got players load on same server localy

import java.io.IOException;
import java.net.*;

public class Client extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Game game;

    public Client(Game game, String ipAddress) {
        this.game = game;
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException e) {
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
<<<<<<< HEAD
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());

//            String message = new String(packet.getData());
//            Logger.print("SERVER > " + message);
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;

        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                Logger.print("[" + address.getHostAddress() + ":" + port + "] " + ((Packet00Login) packet).getUsername() + " has joined game!");
                EntityPlayerMP player = new EntityPlayerMP(handler, ((Packet00Login) packet).getUsername(), address, port, false);
                handler.getWorld().getEntityManager().addEntity(player);
                break;
            case DISCONNECT:
                break;
=======
            String message = new String(packet.getData());
            System.out.println("SERVER > " + message);
>>>>>>> parent of 8ca733f... Got players load on same server localy
        }
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 25565);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
