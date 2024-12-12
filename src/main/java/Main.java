import serial.Deserializer;
import serial.Serializer;
import util.TrackedInputStream;
import util.TrackedOutputStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

  public static void main(String[] args){
     ServerSocket serverSocket = null;
     Socket clientSocket = null;
     int port = 9092;
     try {
       serverSocket = new ServerSocket(port);
       serverSocket.setReuseAddress(true);

       while (true) {
           final Socket socket = serverSocket.accept();
           final var inputStream = new TrackedInputStream(socket.getInputStream());
           //socket.getOutputStream().write(new byte[] {0, 0, 0, 23});
           final var outputStream = new TrackedOutputStream(socket.getOutputStream());
           outputStream.write(new byte[] {0, 0, 0, 23});
           final var deserializer = new Deserializer(inputStream);
           final var serializer = new Serializer(outputStream);

           inputStream.begin();
           final var request = deserializer.read();

           //outputStream.write(new byte[] {0, 0, 0, 23});
           //serializer.write(request);

          // System.out.println(request);
       }

     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     } finally {
       try {
         if (clientSocket != null) {
           clientSocket.close();
         }
       } catch (IOException e) {
         System.out.println("IOException: " + e.getMessage());
       }
     }
  }
}
