
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
public class Ssl2Way implements MqttCallback {
    public static void main(String[] args) throws MqttException {
        //Este bloque es necesario solo si el IoT-CA.crt no está incluido en la TrustStore del sistema
        System.setProperty("javax.net.ssl.trustStore", "IoT-TrustStore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "iotjks");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");       
        // Este bloque es necesario solo cuando el cliente también debe autenticarse (2-way)
        System.setProperty("javax.net.ssl.keyStore", "diego.pkcs12");
        System.setProperty("javax.net.ssl.keyStorePassword", "diegojks");
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        
        MqttClient client = new MqttClient("ssl://pb:8883", "TestClient");
        client.connect();
        client.subscribe("$SYS/#", 0);
        client.setCallback(new Ssl2Way());
    }
    
    @Override
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        System.out.println("Message received:\n\t" + new String(mm.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("Message delivered:\n\t" + imdt.toString());
    }
}
