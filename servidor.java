import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class servidor {
    public static void main(String[] args) {
        int puerto = 5000;

        Map<String, String> preguntas = new HashMap<>();
        preguntas.put("Pais más grande", "Rusia");
        preguntas.put("Película en que el barco se hunde por un glaciar", "Titanic");
        preguntas.put("Tiburón prehistórico", "Megalodon");
        preguntas.put("Ave que renace de las cenizas", "Fénix");
        preguntas.put("Religión predominante en México", "Catolicismo");

        try {
            DatagramSocket socket = new DatagramSocket(puerto);

            System.out.println("Esperando la conexión del cliente...");

            while (true) {
                byte[] bufferEntrada = new byte[1024];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(paqueteEntrada);

                InetAddress direccionCliente = paqueteEntrada.getAddress();
                int puertoCliente = paqueteEntrada.getPort();
                System.out.println("Conexión establecida con el cliente en el puerto: " + puertoCliente);

                int puntajeTotal = 0;

                for (Map.Entry<String, String> entry : preguntas.entrySet()) {
                    String pregunta = entry.getKey();
                    String respuestaCorrecta = entry.getValue();

                    // Enviar pregunta al cliente
                    byte[] bufferPregunta = pregunta.getBytes();
                    DatagramPacket paquetePregunta = new DatagramPacket(bufferPregunta, bufferPregunta.length, direccionCliente, puertoCliente);
                    socket.send(paquetePregunta);

                    // Recibir respuesta del cliente
                    byte[] bufferRespuesta = new byte[1024];
                    DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
                    socket.receive(paqueteRespuesta);
                    String respuesta = new String(paqueteRespuesta.getData(), 0, paqueteRespuesta.getLength());

                    // Verificar respuesta
                    String mensajeSalida;
                    if (respuesta.trim().equalsIgnoreCase(respuestaCorrecta)) {
                        mensajeSalida = "Respuesta correcta";
                        puntajeTotal += 4;
                    } else {
                        mensajeSalida = "Respuesta incorrecta. La respuesta correcta era: " + respuestaCorrecta;
                    }

                    // Enviar confirmación al cliente
                    byte[] bufferSalida = mensajeSalida.getBytes();
                    DatagramPacket paqueteSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccionCliente, puertoCliente);
                    socket.send(paqueteSalida);
                }

                // Enviar puntaje final al cliente
                String puntajeFinal = "Puntaje final: " + puntajeTotal;
                byte[] bufferPuntaje = puntajeFinal.getBytes();
                DatagramPacket paquetePuntaje = new DatagramPacket(bufferPuntaje, bufferPuntaje.length, direccionCliente, puertoCliente);
                socket.send(paquetePuntaje);

                System.out.println("Puntaje final enviado al cliente: " + puntajeTotal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
