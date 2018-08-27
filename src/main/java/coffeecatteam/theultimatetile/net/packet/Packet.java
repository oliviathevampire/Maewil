package coffeecatteam.theultimatetile.net.packet;

import coffeecatteam.theultimatetile.net.Client;
import coffeecatteam.theultimatetile.net.Server;

public abstract class Packet {

    public static enum PacketTypes {
        INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02);

        private int id;

        PacketTypes(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    private byte id;

    public Packet(int id) {
        this.id = (byte) id;
    }

    public abstract void writeData(Client client);

    public abstract void writeData(Server client);

    public abstract byte[] getData();

    public String readData(byte[] data) {
        String message = new String(data).trim();
        return message.substring(2);
    }

    public static PacketTypes lookupPacket(String id) {
        try {
            return lookupPacket(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return PacketTypes.INVALID;
        }
    }

    public static PacketTypes lookupPacket(int id) {
        for (PacketTypes p : PacketTypes.values())
            if (p.getId() == id)
                return p;
        return PacketTypes.INVALID;
    }

    public byte getId() {
        return id;
    }
}
