/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch02011_ledonoff;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
/**
 *
 * @author tudoi
 */
public class Ch02011_LedOnOff {
    
    //...https://stackoverflow.com/questions/6271417/java-get-the-current-class-name
    String className = this.getClass().getSimpleName();
    GpioController gpio = GpioFactory.getInstance();        
    GpioPinDigitalOutput pin18;
    int delayTime = 500;        
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        Ch02011_LedOnOff me = new Ch02011_LedOnOff();

        me.setup();
        me.forloop(5);
        me.shutdown();
    }

    private void setup() {
        System.out.println(className + "setup...");
        pin18 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW);
        
    }

    private void forloop(int n) throws InterruptedException {
        for(int i=0; i < n; i++ ){
            System.out.println(className + "LED On...");
            pin18.high();
            Thread.sleep(delayTime);
            
            System.out.println(className + "LED Off...");            
            pin18.low();
            Thread.sleep(delayTime);
            
        }
    }

    private void shutdown() {
        gpio.shutdown();
    }
    
}
