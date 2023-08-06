package zoeq.parroticator.standard.testtool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Sender {
  public static void main(String[] args) {
    String serverAddress = "localhost";
    int serverPort = 1000;

    try (Socket socket = new Socket(serverAddress, serverPort)) {
      System.out.println("Connected to server");

      OutputStream outputStream = socket.getOutputStream();
      String message = "Hello, Server!";
      byte[] byteMessage = new byte[message.getBytes().length + 2];
      byteMessage[0] = 0x02;
      System.arraycopy(message.getBytes(), 0, byteMessage, 1,
              message.getBytes().length);
      byteMessage[message.getBytes().length + 1] = 0x03;

      outputStream.write(byteMessage);

      InputStream inputStream = socket.getInputStream();
      byte[] buffer = new byte[1024];
      int bytesRead = inputStream.read(buffer);

      String receivedMessage = new String(buffer, 0, bytesRead);
      System.out.println("Received message from server: " + receivedMessage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
