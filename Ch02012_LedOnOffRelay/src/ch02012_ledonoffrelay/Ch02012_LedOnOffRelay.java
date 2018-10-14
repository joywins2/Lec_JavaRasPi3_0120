/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch02012_ledonoffrelay;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
...run.01 :
    pi@raspberrypi:~ $ cd /home/pi/NetBeansProjects/Ch02012_LedOnOffRelay/dist
    pi@raspberrypi:~/NetBeansProjects/Ch02012_LedOnOffRelay/dist $ sudo java -jar "Ch02012_LedOnOffRelay.jar" 
    GPIO Control - pin 0... started.
    --> GPIO state is Low
    Type Q to quit.
    So? > 1
    Setting pin to high
    --> GPIO state is High
    So? > 0
    Setting pin to low
    --> GPIO state is Low
    So? > Q
    pi@raspberrypi:~/NetBeansProjects/Ch02012_LedOnOffRelay/dist $ 
 */
 /*
...run.02 :
    pi@raspberrypi:~/NetBeansProjects/Ch02012_LedOnOffRelay/dist $ sudo java -jar "Ch02012_LedOnOffRelay.jar"  2
    GPIO Control - pin 2... started.
    --> GPIO state is Low
    Type Q to quit.
    So? > 1
    Setting pin to high
    --> GPIO state is High
    So? > 0
    Setting pin to low
    --> GPIO state is Low
    So? > Q
    pi@raspberrypi:~/NetBeansProjects/Ch02012_LedOnOffRelay/dist $
 */
public class Ch02012_LedOnOffRelay {

    private static Pin relayPin = RaspiPin.GPIO_00;
    private static final BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int pin = 0;
        if (args.length > 0) {
            try {
                pin = Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            switch (pin) {
                case 0:
                    relayPin = RaspiPin.GPIO_00;
                    break;
                case 1:
                    relayPin = RaspiPin.GPIO_01;
                    break;
                case 2:
                    relayPin = RaspiPin.GPIO_02;
                    break;
                case 3:
                    relayPin = RaspiPin.GPIO_03;
                    break;
                case 4:
                    relayPin = RaspiPin.GPIO_04;
                    break;
                case 5:
                    relayPin = RaspiPin.GPIO_05;
                    break;
                case 6:
                    relayPin = RaspiPin.GPIO_06;
                    break;
                case 7:
                    relayPin = RaspiPin.GPIO_07;
                    break;
                case 8:
                    relayPin = RaspiPin.GPIO_08;
                    break;
                case 9:
                    relayPin = RaspiPin.GPIO_09;
                    break;
                case 10:
                    relayPin = RaspiPin.GPIO_10;
                    break;
                case 11:
                    relayPin = RaspiPin.GPIO_11;
                    break;
                case 12:
                    relayPin = RaspiPin.GPIO_12;
                    break;
                case 13:
                    relayPin = RaspiPin.GPIO_13;
                    break;
                case 14:
                    relayPin = RaspiPin.GPIO_14;
                    break;
                case 15:
                    relayPin = RaspiPin.GPIO_15;
                    break;
                case 16:
                    relayPin = RaspiPin.GPIO_16;
                    break;
                case 17:
                    relayPin = RaspiPin.GPIO_17;
                    break;
                case 18:
                    relayPin = RaspiPin.GPIO_18;
                    break;
                case 19:
                    relayPin = RaspiPin.GPIO_19;
                    break;
                case 20:
                    relayPin = RaspiPin.GPIO_20;
                    break;
                default: // Model a+ and B+ go up to 30
                    System.out.println("Unknown GPIO pin [" + pin + "], must be between 0 & 20.");
                    System.out.println("Defaulting GPIO_00");
                    break;
            }

        }

        System.out.println("GPIO Control - pin " + pin + "... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // For a relay it seems that HIGH means NC (Normally Closed)...
        final GpioPinDigitalOutput outputPin = gpio.provisionDigitalOutputPin(relayPin); //, PinState.HIGH);

        System.out.println("--> GPIO state is " + (outputPin.isHigh() ? "High" : "Low"));

        boolean go = true;

        System.out.println("Type Q to quit.");
        while (go) {
            String user = userInput("So? > ");
            if ("Q".equalsIgnoreCase(user)) {
                go = false;
            } else {
                int val = 0;
                try {
                    val = Integer.parseInt(user);
                    if (val != 0 && val != 1) {
                        System.out.println("Only 1 or 0, please");
                    } else {
                        System.out.println("Setting pin to " + (val == 1 ? "high" : "low"));
                        if (val == 0) {
                            outputPin.low();
                        } else {
                            outputPin.high();
                        }
                        System.out.println("--> GPIO state is " + (outputPin.isHigh() ? "High" : "Low"));
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
            }
        }
        gpio.shutdown();

    }

    public static String userInput(String prompt) {
        String retString = "";
        System.err.print(prompt);
        try {
            retString = stdIn.readLine();
        } catch (Exception e) {
            System.out.println(e);
            String s;
            try {
                s = userInput("<Oooch/>");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return retString;
    }

}
