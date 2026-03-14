package umg.proyecto1.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import umg.proyecto1.Consumer.model.Transaccion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.core.config.Configurator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Main {
	
	private static final Logger logger = LogManager.getLogger(Main.class);
    private final static String[] COLAS_BANCOS = {"BANRURAL", "BAC", "BI", "GYT"}; 
    private final static String RABBIT_HOST = "localhost";
    private final static String API_POST_URL = "https://7e0d9ogwzd.execute-api.us-east-1.amazonaws.com/default/guardarTransacciones";

    public static void main(String[] args) {
        try {
        	Configurator.initialize(null, "src/main/resources/log4j2.xml");
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(RABBIT_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            ObjectMapper mapper = new ObjectMapper();
            HttpClient httpClient = HttpClient.newHttpClient();

            logger.info(" [*] Esperando transacciones. Para salir presiona CTRL+C");

            channel.basicQos(1);
       
            for (String queueName : COLAS_BANCOS) {
                channel.queueDeclare(queueName, false, false, false, null);

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    
                    try {
                        // 1. Deserializar el mensaje
                        Transaccion tx = mapper.readValue(message, Transaccion.class);
                        
                       tx.setNombre("Jose Cruz");
					   tx.setCarnet("0905-24-3576");
					   tx.setCorreo("jcruzf6@miumg.edu.gt");
                        
                        logger.info("[v] Recibido de " + queueName + ": " + tx.getIdTransaccion());

                        // 2. Convertir a JSON para el POST
                        String jsonBody = mapper.writeValueAsString(tx);

                        // --- INICIO DE LA SUGERENCIA: LÓGICA DE REINTENTO ---
                        int intentosMaximos = 2; 
                        int intentoActual = 0;
                        boolean completado = false;

                        while (intentoActual < intentosMaximos && !completado) {
                            intentoActual++;
                            
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(API_POST_URL))
                                    .header("Content-Type", "application/json")
                                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                                    .build();

                            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                            if (response.statusCode() == 200 || response.statusCode() == 201) { 
                                logger.info(" [OK] Transacción guardada (Intento " + intentoActual + ")");
                                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                                completado = true;
                            } else {
                                logger.error(" [!] Intento " + intentoActual + " fallido. Status: " + response.statusCode());
                                if (intentoActual < intentosMaximos) {
                                    logger.warn("     Reintentando en 2 segundos...");
                                    Thread.sleep(2000); 
                                }
                            }
                        }

                        if (!completado) {
                            logger.error(" [X] Agotados los reintentos para: " + tx.getIdTransaccion());
                            // Se devuelve a la cola para no perderlo (Garantía de no pérdida)
                            channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                        }
                        // --- FIN DE LA SUGERENCIA ---

                    } catch (Exception e) {
                        logger.error(" [!] Error procesando mensaje: " + e.getMessage());
                        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                    }
                };

                channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}