
package griinf.mosquitto;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class NoAuthSub implements MqttCallback {
    
    public static void main(String[] argv) throws MqttException {
        String broker = "tcp://pb:1883";
        String clientId = "NoAuthSubscriber_" + MqttClient.generateClientId();
        
        MqttClient client = new MqttClient(broker, clientId);
        client.setCallback(new NoAuthSub());
        client.connect();
        client.subscribe("$SYS/#", 0);
        
        System.out.println(clientId + " Connected to broker: " + broker);
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message received:\n\t" + new String(mqttMessage.getPayload()));
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }
}    
