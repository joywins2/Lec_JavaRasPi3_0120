/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch02016_listenergpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/*
...https://github.com/savagehomeautomation/pi4j-javaone-demos/blob/master/pi4j-demo-1-gpio/src/main/java/Main.java

...Download jansi-1.4.jar :
   http://www.java2s.com/Code/Jar/j/Downloadjansi14jar.htm
 */
/*
...run.01 :
pi@raspberrypi:~ $ cd /home/pi/NetBeansProjects/Ch02016_ListenerGpio/dist
pi@raspberrypi:~/NetBeansProjects/Ch02016_ListenerGpio/dist $ sudo java -jar "Ch02016_ListenerGpio.jar"
-----------------------------------------------

Welcome to JavaOne 2013 - [[ CON7968 ]]

  Let's Get Physical: I/O Programming with
  Java on the Raspberry Pi with Pi4J

-----------------------------------------------
<--Pi4J--> GPIO Example ... started.
-----------------------------------------------

PRESS [ENTER] TO EXIT
Button pressed: state = LOW
LED is ON
Button pressed: state = HIGH
LED is OFF
Button pressed: state = LOW
LED is ON
Button pressed: state = HIGH
LED is OFF
Button pressed: state = LOW
LED is ON
Button pressed: state = HIGH
LED is OFF

pi@raspberrypi:~/NetBeansProjects/Ch02016_ListenerGpio/dist $
*/
public class Ch02016_ListenerGpio {

    // create GPIO controller
    private static final GpioController gpio = GpioFactory.getInstance();


    public static void main(String args[]) throws InterruptedException {

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
            .a("<--Pi4J--> GPIO Example ... started.\n")
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
        // GPIO EVENT LISTENER(S)
        // ******************************************************************

        // create button event listener
        buttonPin.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

                // display console message
                AnsiConsole.out().println(Ansi.ansi().fg(Ansi.Color.WHITE)
                        .a("Button pressed: state = " + event.getState()).reset());

                if(event.getState().isHigh()){
                    // turn off LED pin
                    ledPin.setState(PinState.LOW);
                }
                else{
                    // turn on LED pin
                    ledPin.setState(PinState.HIGH);
                }
            }
        });

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
