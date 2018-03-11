package cl.sii.casaiot.casaiotbackend;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


@Path("/")
public class ControlCasaService {


	@POST
	@Path("/encenderLuces")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public EncenderLucesResponse pub(EncenderLucesRequest request) {

		//String topic = "smarthome/room1/light";
		String topic = request.getJerarquia();
		String content = request.isEncendida() ? "1" : "0";
		int qos = 2;
		//String broker = "tcp://iot.eclipse.org:1883";
		String broker = "tcp://192.168.43.135";
		
		String clientId = "CasaIotBackend-Java";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			System.out.println("Message published");
			sampleClient.disconnect();
			System.out.println("Disconnected");
			
			EncenderLucesResponse response = new EncenderLucesResponse();
			response.setStatus("message published");
			return response;
			
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
			EncenderLucesResponse response = new EncenderLucesResponse();
			response.setStatus(me.getMessage());
			return response; 
		}		
		
	}

	public static void main(String[] args) {

		String topic = "smarthome/room1/led2";
		String content = "1";
		int qos = 2;
		//String broker = "tcp://iot.eclipse.org:1883";
		String broker = "tcp://192.168.43.135";
		
		String clientId = "JavaSample";
		MemoryPersistence persistence = new MemoryPersistence();

		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
			System.out.println("Message published");
			sampleClient.disconnect();
			System.out.println("Disconnected");
			System.exit(0);
		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

}