import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class servidor {
    public static void main(String[] args) {
        //establecer puerto
        int puerto = 5000;

        // inicializar el HiloCliente
        HiloCliente hiloCliente = new HiloCliente(puerto);
        hiloCliente.start();
    }
}