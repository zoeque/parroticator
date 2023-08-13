package zoeque.parroticator.testtool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

public class Sender {
  public static void main(String[] args) {
    String serverAddress = "localhost";
    int serverPort = 2000;

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

      byte[] fullMessage = new byte[1024];
      boolean receiveFlag = false;
      int idx = 0;
      int messageLength = 0;

      boolean loopFlag = true;
      while (loopFlag) {
        byte messageText = (byte) inputStream.read();
        // starts building message when STX received
        if (messageText == 0x02) {
          fullMessage[idx] = messageText;
          receiveFlag = true;
        }
        if (receiveFlag) {
          if (messageText == 0x03) {
            fullMessage[idx++] = messageText;
            messageLength++;
            break;
          } else {
            fullMessage[idx++] = messageText;
            messageLength++;
            continue;
          }
        }
      }
      String receivedMessage = new String(fullMessage, Charset.forName("SJIS"));
      System.out.println("Received message from server: " + receivedMessage);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
