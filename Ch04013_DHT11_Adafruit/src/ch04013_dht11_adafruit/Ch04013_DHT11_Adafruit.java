/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch04013_dht11_adafruit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

//...http://pi4j.com/apidocs/index.html?com/pi4j/system/SystemInfo.html
import com.pi4j.system.SystemInfo;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author tudoi
 */
public class Ch04013_DHT11_Adafruit {

    private static String line;
    private static String[] data;
    static int humidity = 0;
    static int temperature = 0;
    static String strHumidity = null;
    static String strTemperature = null;

    public static void main(String[] args) {

        LED led = new LED();

        try {
            //...Make sure to avoid trailing backslashes(/), as it will result in a compilation error.
            MqttClient client = new MqttClient(
                    "tcp://io.adafruit.com:1883",
                    MqttClient.generateClientId(),
                    new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("joywins");
            options.setPassword("09990ab022b6480e854d663443e130dd".toCharArray());

            /*code block for subscribe*/
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker 
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println(topic + ": " + message);
                    //if (message.toString() == "OFF") {
                    if (message.toString().equals("OFF")) {  
                        System.out.println("LED OFF...");
                        led.off();                        
                    }
                    //if (message.toString() == "ON") {
                    if (message.toString().equals("ON")) {  
                        System.out.println("LED ON...");
                        led.on();
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete 
                }
            });
            /*end block */

            client.connect(options);

            while (true) {
                try {
                    int[] arrInt = new int[2];
                    arrInt = runDht11();

                    client.publish("joywins/feeds/temperarture",
                            Integer.toString(arrInt[0]).getBytes("UTF8"), 0, true);

                    client.publish("joywins/feeds/humidity",
                            Integer.toString(arrInt[1]).getBytes("UTF8"), 0, true);

                    //...ref: http://pi4j.com/apidocs/index.html?com/pi4j/system/SystemInfo.html
                    client.publish("joywins/feeds/CpuTemperature",
                            Float.toString((float) (SystemInfo.getCpuTemperature())).getBytes("UTF8"), 0, true);

                    Thread.sleep(60 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //client.disconnect();
                /*subscribe*/
                client.subscribe("joywins/feeds/button", 0);
            }

        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    public static int[] runDht11() throws IOException, InterruptedException {
        int[] arrInt = new int[2];
        // TODO Auto-generated method stub
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("python /home/pi/Lec_JavaRasPi3_0122/exam0132_adafruit_dht11.py");
        BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
        if ((line = bri.readLine()) != null) {
            if (!(line.contains("ERR_CRC") || line.contains("ERR_RNG"))) {
                System.out.print("line :");
                System.out.println(line.toString());
                System.out.println("length: " + line.length());
                String strTemp = null;
                strTemp = line.substring(1, line.length() - 1);
                strTemp = strTemp.replace(",", "");
                strTemp = strTemp.replace("'", "");
                int nSplit = strTemp.indexOf("|");
                System.out.println("strTemp: " + strTemp);
                System.out.println("nSplit: " + nSplit);
                strHumidity = (strTemp.substring(0, nSplit - 1)).trim();
                strTemperature = (strTemp.substring(nSplit + 1, strTemp.length())).trim();
                System.out.println("strHumidity: " + strHumidity);
                System.out.println("strTemperature: " + strTemperature);
                if (strTemperature == null || strTemperature.trim().equals("")) {
                    strTemperature = "0";
                }
                if (strHumidity == null || strHumidity.trim().equals("")) {
                    strHumidity = "0";
                }
                //...https://stackoverflow.com/questions/4043579/converting-bigdecimal-to-integer
                BigDecimal t = new BigDecimal(strTemperature);
                BigDecimal h = new BigDecimal(strHumidity);
                temperature = t.intValue();
                humidity = h.intValue();
                arrInt[0] = temperature;
                arrInt[1] = humidity;
                //System.out.println("temperature: " + temperature);
                //System.out.println("humidity: " + humidity);
            } else {
                System.out.println("Data Error");
            }
        }

        bri.close();
        p.waitFor();
        System.out.println("Temperature is : " + temperature + " 'C Humidity is :" + humidity + " %RH");
        System.out.println("Done.");
        return arrInt;
    }
}
