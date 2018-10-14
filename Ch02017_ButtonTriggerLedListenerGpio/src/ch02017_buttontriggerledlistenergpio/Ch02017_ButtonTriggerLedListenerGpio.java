/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch02017_buttontriggerledlistenergpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.trigger.GpioBlinkStateTrigger;
import com.pi4j.io.gpio.trigger.GpioBlinkStopStateTrigger;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioInverseSyncStateTrigger;
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;
import com.pi4j.io.gpio.trigger.GpioToggleStateTrigger;
import java.util.concurrent.Callable;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/*
...Download jansi-1.4.jar :
   http://www.java2s.com/Code/Jar/j/Downloadjansi14jar.htm
 */
public class Ch02017_ButtonTriggerLedListenerGpio {

    // create GPIO controller
    private static final GpioController gpio = GpioFactory.getInstance();


    public static void main(String args[]) {

        // init JANSI
        // (using jansi for color console output)
        AnsiConsole.systemInstall();

        // display welcome message
        AnsiConsole.out().println(Ansi.ansi().fg(Ansi.Color.CYAN).a(Ansi.Attribute.INTENSITY_BOLD)
                .a("-----------------------------------------------\n")
                .a("\n")
                .a("Welcome to JavaOne 2013 - [[ CON7968 ]]\n")
                .a("\n")
                .a("  Let's Get Physical: I/O Programming with\n")
                .a("  Java on the Raspberry Pi with Pi4J\n")
                .a("\n")
                .a("-----------------------------------------------\n")
                .a("<--Pi4J--> GPIO Triggers Example ... started.\n")
                .a("-----------------------------------------------\n").reset());


        // ******************************************************************
        // INITIALIZE
        // ******************************************************************

        // momentary push-button switch; activates when button is pushed
        final GpioPinDigitalInput buttonPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_UP);

        // led; illuminates when GPIO is HI
        final GpioPinDigitalOutput ledPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, PinState.LOW);

        // make sure the LED is turned off when program shuts down
        gpio.setShutdownOptions(true, PinState.LOW, ledPin);

        // ******************************************************************
        // GPIO TRIGGER
        // ******************************************************************
        buttonPin.addTrigger(new GpioToggleStateTrigger(PinState.LOW, ledPin));
        //buttonPin.addTrigger(new GpioBlinkStateTrigger(PinState.LOW, ledPin, 100));
        //buttonPin.addTrigger(new GpioBlinkStopStateTrigger(PinState.HIGH, ledPin));
        //buttonPin.addTrigger(new GpioSyncStateTrigger(ledPin));
        //buttonPin.addTrigger(new GpioInverseSyncStateTrigger(ledPin));
        //buttonPin.addTrigger(new GpioPulseStateTrigger(PinState.LOW, ledPin, 4000));

        buttonPin.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                // display console message
                AnsiConsole.out().println(Ansi.ansi().fg(Ansi.Color.WHITE)
                        .a("Button callback fired!").reset());
                return null;
            }
        }));

        // for a complete list of implementation triggers, please see:
        // https://github.com/Pi4J/pi4j/tree/master/pi4j-core/src/main/java/com/pi4j/io/gpio/trigger


        // display console message when LED pin state changes
        ledPin.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if(event.getState().isHigh()){
                    AnsiConsole.out().println(Ansi.ansi().fg(Ansi.Color.GREEN).a("LED is ON").reset());
                }
                else{
                    AnsiConsole.out().println(Ansi.ansi().fg(Ansi.Color.RED).a("LED is OFF").reset());
                }
            }
        });


        // ******************************************************************
        // PROGRAM TERMINATION
        // ******************************************************************

        // wait for user input to terminate program
        AnsiConsole.out().println(Ansi.ansi()
                .fg(Ansi.Color.BLACK)
                .bg(Ansi.Color.CYAN)
                .a("PRESS [ENTER] TO EXIT")
                .reset());
        System.console().readLine();

        // shutdown GPIO controller
        gpio.shutdown();
    }
}