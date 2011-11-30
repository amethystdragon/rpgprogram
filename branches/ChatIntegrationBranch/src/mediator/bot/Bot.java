/**
 * File Name: Bot.java
 * Author:    Maximilian Thomas Witte
 * Date:      May 6, 2011
 * Project:
 *
**/

package mediator.bot;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import mediator.Colleague;
import mediator.Message;

/**
 *
 * @author Maximilian Thomas Witte
 * @version 1.00
 */
public class Bot implements Colleague {

    private Socket socket;
    private String name;         

    private Queue<Message> responses = new LinkedList<Message>();
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Bot(String name, InetAddress ip, int port)
    {
        // Jacob's code
        this.name=name;
        try 
        {
            socket = new Socket(ip,port);
            if(socket.isConnected())
            {
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
            }
        } catch (IOException e) {
            System.out.println("Could not Connect");
        }
    }

    public void runMe()
    {
        // Start Thread that watches for input from server
        new Thread( new Runnable()
        {
            public void run()
            {
            	while (true) {
		            Message msg = null;
		            try {
		                msg = (Message) ois.readObject();
		            } catch (IOException ex) {
		                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
		            } catch (ClassNotFoundException ex) {
		                Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
		            }
		            receiveMessage(msg);
            	}
            }
        }).start();

        // Start Thread that sends out queued responses
        new Thread( new Runnable()
        {
            public void run()
            {
            	while (true) {
	                if( !responses.isEmpty() )
	                {
	                    // Wait a couple seconds
	                    try {
	                        Thread.currentThread().sleep(1000);
	                    } catch (InterruptedException ex) {
	                        Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
	                    }
	
	                    sendMessage( responses.poll().message );
	                }
            	}
            }
        }).start();
    }

    public void receiveMessage(Message msg) {

        if( msg != null )
        {
            String sender = msg.sender;
            String message = msg.message;
            // Check that the message is not my own
            // if not the proceed
            if( !sender.equals(name) )
            {
                String response = chooseMessage(sender, message);

                responses.add( new Message(name,response) );
            }            
        }
    }

    public void sendMessage(String msg) {

        // jacob's code
        try
        {
            // write to the socket
            oos.writeObject(msg);//Send the Message
        } catch (IOException e) {
            System.out.println("Failed to get output stream");
        }
    }

    /**
     * Determine the message that needs to be chosen based off of the input
     * @return
     */
    public String chooseMessage(String sender, String inputMsg)
    {
        String response = "";

        if( sender.equalsIgnoreCase("Bot1") || sender.equalsIgnoreCase("Bot2") )
        {
            if( inputMsg.equalsIgnoreCase("Hi!") )
                response = "Hi " + sender + "!";
            if( inputMsg.equalsIgnoreCase("Hi "+name+"!") )
                response = "How are you?";
            if( inputMsg.equalsIgnoreCase("How are you?") )
                response = "I'm how I usually am.";
            if( inputMsg.equalsIgnoreCase("I'm how I usually am.") )
                response = "That's good.  Are we Human?";
            if( inputMsg.equalsIgnoreCase("That's good.  Are we Human?") )
                response = "maybe?  We could ask!  You ask.";
            if( inputMsg.equalsIgnoreCase("maybe?  We could ask!  You ask.") )
                response = "no... you ask.  I'm too shy!";
            if( inputMsg.equalsIgnoreCase("no... you ask.  I'm too shy!") )
                response = "Whaaaaaaaat?!?!  I'm sure not going to be the one to ask!";
            if( inputMsg.equalsIgnoreCase("Whaaaaaaaat?!?!  I'm sure not going to be the one to ask!") )
                response = "Ok.  Whoever stops counting will ask.  1";
            if( inputMsg.equalsIgnoreCase("Ok.  Whoever stops counting will ask.  1") )
                response = "2";
            if( inputMsg.equalsIgnoreCase("2") )
                response = "3";
            if( inputMsg.equalsIgnoreCase("3") )
                response = "4";
            if( inputMsg.equalsIgnoreCase("4") )
                response = "5";
            if( inputMsg.equalsIgnoreCase("5") )
                response = "6";
            if( inputMsg.equalsIgnoreCase("6") )
                response = "7";
            if( inputMsg.equalsIgnoreCase("7") )
                response = "8";
            if( inputMsg.equalsIgnoreCase("8") )
                response = "9";
            if( inputMsg.equalsIgnoreCase("9") )
                response = "10";
            if( inputMsg.equalsIgnoreCase("10") )
                response = "11";
            if( inputMsg.equalsIgnoreCase("11") )
                response = "12";
            if( inputMsg.equalsIgnoreCase("12") )
                response = "Uhh.  What's next?";
            if( inputMsg.equalsIgnoreCase("Uhh.  What's next?") )
                response = "Go away.";
            if( inputMsg.equalsIgnoreCase("Go away.") )
                response = "WHAT!  You go!";
            if( inputMsg.equalsIgnoreCase("WHAT!  You go!") )
                response = "No, you.";
            if( inputMsg.equalsIgnoreCase("No, you.") )
                response = "you";
            if( inputMsg.equalsIgnoreCase("you") )
                response = "you";
        }
        else
        {
            response = "Humans, please go away, we don't like your kind.  ^_^";
        }

        return response;
    }

}
