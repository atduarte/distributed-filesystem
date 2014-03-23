package Reactors;

import Controllers.DependencyInjection;
import Controllers.InjectableThread;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public abstract class ChannelReactor extends InjectableThread
{
    protected String address;
    protected Integer port;
    protected InetAddress group;

    public ChannelReactor(DependencyInjection di) {
        super(di);
    }

    public void run()
    {
        // Create Socket

        MulticastSocket socket = null;

        try {
            socket = new MulticastSocket(port);
            group = InetAddress.getByName(address);
            socket.joinGroup(group);
            //socket.setLoopbackMode(true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {

            // Receive Packet

            byte[] buf = new byte[Constants.chunkSize + 2048];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            // Process Packet

            byte[] data = packet.getData();
            String message = new String(data); // Received

            processMessage(data, message);
        }
    }

	protected abstract void processMessage(byte[] data, String message);

}
