package ch04013_dht11_adafruit;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tudoi
 */
public class LED {
    
    final GpioController gpio;
    final GpioPinDigitalOutput pin;
    
    public LED()
    {
        gpio = GpioFactory.getInstance();
        pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
        pin.setShutdownOptions(true, PinState.LOW);
    }
    
    public void on()
    {
        pin.high();
        System.out.print("pin.high...");
    }
    
     public void off()
    {
        pin.low();
        System.out.print("pin.low...");
    }
}

