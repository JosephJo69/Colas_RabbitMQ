Sistema de Procesamiento de Transacciones Bancarias (UMG)
Este proyecto implementa una arquitectura desacoplada basada en microservicios utilizando RabbitMQ como intermediario de mensajería, desarrollado para la asignatura de Estructuras de Datos.

Arquitectura del Sistema
El sistema se compone de dos módulos principales que interactúan de forma asíncrona:

Producer (Productor): * Consume un lote de transacciones desde un API REST externo (AWS).

Deserializa los datos y clasifica cada transacción según el bancoDestino.

Publica los mensajes en colas específicas de RabbitMQ (BANRURAL, BAC, BI, PROMERICA).

Consumer (Consumidor): * Escucha de forma simultánea las colas bancarias.

Enriquece el JSON con los datos del estudiante (Nombre y Carnet).

Persiste la información enviándola a una base de datos centralizada mediante un método POST.

Especificaciones Técnicas y Resiliencia
1. Garantía de No Pérdida de Datos (ACK Manual)
Para asegurar que ninguna transacción se pierda en caso de una falla eléctrica o de red, se implementó el Manual Acknowledgement. El mensaje solo se elimina de la cola de RabbitMQ una vez que el API de destino confirma con un status 200 o 201.

2. Política de Reintentos
El consumidor cuenta con una lógica de resiliencia ante fallos del servidor:

Intentos: 2 intentos por transacción.

Delay: Pausa de 2 segundos entre reintentos para permitir la recuperación del servicio.

Nack: Si los reintentos fallan, el mensaje se devuelve a la cola (basicNack con requeue=true).

3. Trazabilidad (Log4j2)
Se utiliza el framework Log4j2 para generar registros detallados. Los logs incluyen:

Timestamps (Fecha y hora exacta).

Nivel de severidad (INFO, WARN, ERROR).

Instrucciones de Ejecución
Asegurarse de que el servidor RabbitMQ esté en ejecución.

Ejecutar el proyecto Consumer para iniciar la escucha de las colas.

Ejecutar el proyecto Producer para cargar el lote de transacciones.

Verificar la persistencia en el archivo de log generado en la carpeta del proyecto.

Link del video: https://drive.google.com/file/d/12v21BNOJ-omELDx9Ghzvo38ElcLXZEmz/view?usp=drive_link
