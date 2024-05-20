import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class cliente {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            int puertoServidor = 5000;
            Scanner scanner = new Scanner(System.in);

            // Enviar un mensaje inicial al servidor para iniciar la comunicación
            byte[] bufferInicial = "Hola servidor".getBytes();
            DatagramPacket paqueteInicial = new DatagramPacket(bufferInicial, bufferInicial.length, direccionServidor, puertoServidor);
            socket.send(paqueteInicial);

            for (int i = 0; i < 5; i++) {
                // Recibir pregunta del servidor
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(paqueteEntrada);

                String pregunta = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
                System.out.println("Pregunta: " + pregunta);

                // Enviar respuesta al servidor
                System.out.print("Respuesta: ");
                String respuesta = scanner.nextLine();
                byte[] bufferSalida = respuesta.getBytes();
                DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionServidor, puertoServidor);
                socket.send(paqueteSalida);

                // Recibir confirmación del servidor
                byte[] bufferConfirmacion = new byte[1024];
                DatagramPacket paqueteConfirmacion = new DatagramPacket(bufferConfirmacion, bufferConfirmacion.length);
                socket.receive(paqueteConfirmacion);

                String confirmacion = new String(paqueteConfirmacion.getData(), 0, paqueteConfirmacion.getLength());
                System.out.println("Confirmación: " + confirmacion);
            }

            // Recibir puntaje final del servidor
            byte[] bufferPuntaje = new byte[1024];
            DatagramPacket paquetePuntaje = new DatagramPacket(bufferPuntaje, bufferPuntaje.length);
            socket.receive(paquetePuntaje);

            String puntajeFinal = new String(paquetePuntaje.getData(), 0, paquetePuntaje.getLength());
            System.out.println("Puntaje final: " + puntajeFinal);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
