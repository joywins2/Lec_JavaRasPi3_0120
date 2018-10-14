/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch04011_dht11_adafruit;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author tudoi
 */
public class Ch04011_DHT11_Adafruit {

    public static void main(String[] args) {
        try {
            //...Make sure to avoid trailing backslashes(/), as it will result in a compilation error.
            MqttClient client = new MqttClient(
                    "tcp://io.adafruit.com:1883",
                    MqttClient.generateClientId(),
                    new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("joywins");
            options.setPassword("09990ab022b6480e854d663443e130dd".toCharArray());
            client.connect(options);

            while (true) {
                try {
                    client.publish("joywins/feeds/temperarture",
                            Integer.toString((int) (Math.random()
                                    * 101)).getBytes("UTF8"), 0, true);
                    Thread.sleep(60 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//client.disconnect();
            }
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }
}
