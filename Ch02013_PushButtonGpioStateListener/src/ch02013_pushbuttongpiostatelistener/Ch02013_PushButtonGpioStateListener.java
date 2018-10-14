/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch02013_pushbuttongpiostatelistener;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/*
pi@raspberrypi:~ $ cd /home/pi/NetBeansProjects//Ch02013_PushButtonGpioStateListener/dist/
pi@raspberrypi:~/NetBeansProjects/Ch02013_PushButtonGpioStateListener/dist $ sudo java -jar "Ch02013_PushButtonGpioStateListener.jar"
<--Pi4J--> GPIO Listen Example ... started.
 ... complete the GPIO #02 circuit and see the listener feedback here in the console.
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = HIGH
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = LOW
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = HIGH
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = LOW
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = HIGH
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = LOW
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = HIGH
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = LOW
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = HIGH
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = LOW
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = HIGH
 --> GPIO PIN STATE CHANGE: "GPIO 2" <GPIO 2> = LOW
^Cpi@raspberrypi:~/NetBeansProjects/Ch02013_PushButtonGpioStateListener/dist $
 */
public class Ch02013_PushButtonGpioStateListener {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        // set shutdown state for this input pin
        myButton.setShutdownOptions(true);

        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
                System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
            }

        });

        System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");

        // keep program running until user aborts (CTRL-C)
        while(true) {
            Thread.sleep(500);
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }
    
}
