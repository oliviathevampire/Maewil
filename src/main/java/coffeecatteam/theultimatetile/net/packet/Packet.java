package coffeecatteam.theultimatetile.net.packet;

import coffeecatteam.theultimatetile.net.Client;
import coffeecatteam.theultimatetile.net.Server;

public abstract class Packet {

    public static enum PacketTypes {
        INVALID(-1), LOGIN(00), DISCONECT(01);

        private int id;

        PacketTypes(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public byte packetId;

    public Packet(int packetId) {
        this.packetId = (byte) packetId;
    }

    public abstract void writeData(Client client);
    public abstract void writeData(Server server);
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
}
