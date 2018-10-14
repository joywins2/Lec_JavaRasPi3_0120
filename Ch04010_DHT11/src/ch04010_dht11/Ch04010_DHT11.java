/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch04010_dht11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 *
 * @author tudoi
 */
public class Ch04010_DHT11 {

    private static String line;
    private static String[] data;
    static int humidity = 0;
    static int temperature = 0;
    static String strHumidity=null;
    static String strTemperature=null;

    /*
     ...ref : https://raw.githubusercontent.com/oksbwn/DHT11_Raspberry-Pi/master/DHT11_JavaPy/src/MainClass.java
    
     */
    public static void main(String[] args) throws Exception {
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
                strTemp = line.substring(1, line.length()-1);
                strTemp = strTemp.replace(",", "");
                strTemp = strTemp.replace("'", "");
                int nSplit = strTemp.indexOf("|");
                System.out.println("strTemp: " + strTemp);
                System.out.println("nSplit: " + nSplit);
                strHumidity = (strTemp.substring(0, nSplit-1)).trim();
                strTemperature = (strTemp.substring(nSplit+1, strTemp.length())).trim();
                System.out.println("strHumidity: " + strHumidity);
                System.out.println("strTemperature: " + strTemperature);
                if(strTemperature == null || strTemperature.trim().equals("")){
                    strTemperature = "0";
                }
                if(strHumidity == null || strHumidity.trim().equals("")){
                    strHumidity = "0";
                }
                //...https://stackoverflow.com/questions/4043579/converting-bigdecimal-to-integer
                BigDecimal t = new BigDecimal(strTemperature);
                BigDecimal h = new BigDecimal(strHumidity);
                temperature = t.intValue();
                humidity = h.intValue();                
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
    }

}
