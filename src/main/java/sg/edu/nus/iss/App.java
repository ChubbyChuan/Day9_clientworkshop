package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    public static void main(String[] args) throws UnknownHostException, IOException {

        // opening a socket to connect to the server on port 1234
        Socket socket = new Socket("localhost",1234);
        socket.getOutputStream();

        // variable to store keyboard input
        String keyInput = "";
        String msgRecv = "";

        // using console to receive input from the keyboard
        Console console = System.console();

        try (OutputStream os = socket.getOutputStream()) { // preparing sending data out using socket to client.
                                                           // try function due to IO error is expected.
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            try(InputStream is = socket.getInputStream()){   // preparing input coming in form the socket
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);
                // from here, we have finish set up the data input and output. We can move off to wrte
                // the actual program
                
                while (!keyInput.equalsIgnoreCase("quit")){
                    keyInput = console.readLine("Enter guess XX"); //user to key in input
                    dos.writeUTF (keyInput);    // to send the data over to server 
                    dos.flush();                // flush the output

                    msgRecv = dis.readUTF();
                    System.out.println("From server: " + msgRecv);
                }
                dos.close();
                bos.close();
                socket.close();

            }catch (EOFException ex) {
                ex.printStackTrace();
            }

        } catch (EOFException ex) {
            ex.printStackTrace();
            socket.close(); // close off the socket once its completed.
        }
     
    }
}
