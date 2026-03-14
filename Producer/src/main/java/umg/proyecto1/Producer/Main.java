package umg.proyecto1.Producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import umg.proyecto1.Producer.model.LoteTransacciones;
import umg.proyecto1.Producer.model.Transaccion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
	
	private static final Logger logger = LogManager.getLogger(Main.class);
	
    private final static String API_URL = "https://hly784ig9d.execute-api.us-east-1.amazonaws.com/default/transacciones";
    private final static String RABBIT_HOST = "localhost"; // Cambia si usas un servidor externo

    public static void main(String[] args) {
        try {
        	logger.info("El sistema de logs está activo y funcionando.");
            // 1. Obtener datos del API (GET)
            logger.info("Conectando al API...");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 2. Parsear JSON a Objetos Java
            ObjectMapper mapper = new ObjectMapper();
            LoteTransacciones lote = mapper.readValue(response.body(), LoteTransacciones.class);
            logger.info("Lote recibido: " + lote.loteId + " con " + lote.transacciones.size() + " transacciones.");

            // 3. Configurar RabbitMQ
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(RABBIT_HOST);
            
            try (Connection connection = factory.newConnection(); 
                 Channel channel = connection.createChannel()) {

                for (Transaccion tx : lote.transacciones) {
                    String nombreCola = tx.bancoDestino; // Ejemplo: "BANRURAL", "BAC"
                    
                    // Declarar cola dinámicamente
                    channel.queueDeclare(nombreCola, false, false, false, null);
                    
                    // Convertir transacción individual a JSON string
                    String message = mapper.writeValueAsString(tx);
                    
                    // Enviar a la cola
                    channel.basicPublish("", nombreCola, null, message.getBytes("UTF-8"));
                    logger.info(" [x] Enviado a " + nombreCola + ": " + tx.getIdTransaccion());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}