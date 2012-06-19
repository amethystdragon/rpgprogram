/**
 * File Name: BotClient.java
 * Author:    ggzaery@gmail.com
 * Date:      May 16, 2011
 * Project:
 *
**/

package chat.bot;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Maximilian Thomas Witte
 * @version 1.00
 */
public class BotClient {

    private static InetAddress ip;
    private static final int port = 4000;

    /**
     * @param args
     */
    public static void main(String[] args) {

            //Prompt the user for the address of the server
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter an address to connect to:");
            String ipString = scan.nextLine();

            //Parse that to an InetAddress
            try {
                    ip = InetAddress.getByName(ipString);
                    new Bot("Bot1",ip,port).runMe();
                    new Bot("Bot2",ip,port).runMe();
                    System.out.println("Successfully infiltrated");
            } catch (UnknownHostException e) {
                    System.out.println("Could not parse address " + ipString + " into a valid host");
                    System.exit(0);
            }


            
    }
}
