import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class servidor {
    public static void main(String[] args) {
        int puerto = 5000;

        // Crear instancia de HiloCliente y ejecutarlo
        HiloCliente hiloCliente = new HiloCliente(puerto);
        hiloCliente.start();
    }
}