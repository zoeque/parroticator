package zoeq.parroticator.standard.application.services;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
public class ParroticatorCustomizedSerializer
        implements Serializer<byte[]>, Deserializer<byte[]> {

  @Value("${zoeque.parroticator.message.length:1024}")
  int maxMessageLength;

  int STX = 0x02;
  int ETX = 0x03;

  @NotNull
  @Override
  public byte[] deserialize(InputStream inputStream) throws IOException {
    try {
      log.info("Message received");
      byte[] fullMessage = new byte[maxMessageLength];
      boolean receiveFlag = false;
      int idx = 0;
      int messageLength = 0;
      while (true) {
        byte messageText = (byte) inputStream.read();
        if (messageText == STX) {
          fullMessage[idx] = messageText;
          receiveFlag = true;
        }
        if (receiveFlag) {
          if (messageText == ETX) {
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
      // delete unnecessary 0 parts from fullMessage
      byte[] receivedFullMessage = new byte[messageLength];
      System.arraycopy(fullMessage, 0, receivedFullMessage, 0, messageLength);
      log.info("Received message : " + receivedFullMessage);
      return receivedFullMessage;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void serialize(@NotNull byte[] message,
                        OutputStream outputStream) throws IOException {
    outputStream.write(message);
    outputStream.flush();
  }
}
