package coffeecatteam.theultimatetile.net;

import coffeecatteam.theultimatetile.Game;
import coffeecatteam.theultimatetile.Handler;
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
            String message = new String(packet.getData());
            Logger.print("SERVER > " + message);
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
