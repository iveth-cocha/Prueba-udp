import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.Random;

public class Hilocliente extends Thread {
    private DatagramSocket socket;
    private InetAddress direccionServidor;
    private int puertoServidor;
    private Map<String, String> preguntas;
    private Random random;

    public Hilocliente(DatagramSocket socket, InetAddress direccionServidor, int puertoServidor, Map<String, String> preguntas) {
        this.socket = socket;
        this.direccionServidor = direccionServidor;
        this.puertoServidor = puertoServidor;
        this.preguntas = preguntas;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            for (Map.Entry<String, String> entry : preguntas.entrySet()) {
                String preguntas = entry.getKey();
                String respuestaCorrecta = entry.getValue();

                // Recibir pregunta del servidor
                byte[] bufferPregunta = new byte[1024];
                DatagramPacket paquetePregunta = new DatagramPacket(bufferPregunta, bufferPregunta.length);
                socket.receive(paquetePregunta);
                String preguntaRecibida = new String(paquetePregunta.getData(), 0, paquetePregunta.getLength());
                System.out.println("Pregunta: " + preguntaRecibida);

                // Enviar respuesta al servidor
                String respuesta = obtenerRespuestaAleatoria();
                byte[] bufferRespuesta = respuesta.getBytes();
                DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length, direccionServidor, puertoServidor);
                socket.send(paqueteRespuesta);
                System.out.println("Respuesta enviada: " + respuesta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String obtenerRespuestaAleatoria() {
        String[] respuestasArray = preguntas.values().toArray(new String[0]);
        return respuestasArray[random.nextInt(respuestasArray.length)];
    }
}
