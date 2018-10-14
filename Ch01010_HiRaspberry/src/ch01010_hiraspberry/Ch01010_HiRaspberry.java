/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch01010_hiraspberry;

/*
ant -f D:\\JoyWins2\\Lec_JavaRasPi3_0120\\Ch01010_HiRaspberry -Dnb.internal.action.name=run -Dremote.platform.rp.target=linuxarmvfphflt-15 -Dremote.platform.password=***** -Dremote.platform.rp.filename=linuxarmvfphflt -Dremote.platform.java.spec.ver=18 run-remote
init:
Deleting: D:\JoyWins2\Lec_JavaRasPi3_0120\Ch01010_HiRaspberry\build\built-jar.properties
deps-jar:
Updating property file: D:\JoyWins2\Lec_JavaRasPi3_0120\Ch01010_HiRaspberry\build\built-jar.properties
Compiling 1 source file to D:\JoyWins2\Lec_JavaRasPi3_0120\Ch01010_HiRaspberry\build\classes
compile:
Copying 1 file to D:\JoyWins2\Lec_JavaRasPi3_0120\Ch01010_HiRaspberry\build
Nothing to copy.
Building jar: D:\JoyWins2\Lec_JavaRasPi3_0120\Ch01010_HiRaspberry\dist\Ch01010_HiRaspberry.jar
To run this application from the command line without Ant, try:
java -jar "D:\JoyWins2\Lec_JavaRasPi3_0120\Ch01010_HiRaspberry\dist\Ch01010_HiRaspberry.jar"
jar:
Connecting to 192.168.0.5:22
cmd : mkdir -p '/home/pi/NetBeansProjects//Ch01010_HiRaspberry/dist'
Connecting to 192.168.0.5:22
done.
profile-rp-calibrate-passwd:
Connecting to 192.168.0.5:22
...★ cmd : cd '/home/pi/NetBeansProjects//Ch01010_HiRaspberry'; 'sudo' '/opt/java/jdk1.8.0_181//bin/java'  -Dfile.encoding=UTF-8   -jar /home/pi/NetBeansProjects//Ch01010_HiRaspberry/dist/Ch01010_HiRaspberry.jar 
Hi, RaspberryPi3^_____^!!!
run-remote:
BUILD SUCCESSFUL (total time: 3 seconds)
 */
public class Ch01010_HiRaspberry {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hi, RaspberryPi3^_____^!!!"); 
    }
    
}
