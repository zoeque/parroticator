package zoeque.parroticator.configuration.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.stereotype.Component;
import zoeque.parroticator.application.services.MessageHandleService;
import zoeque.parroticator.application.services.TcpServerNotificationService;
import zoeque.parroticator.application.services.standard.AbstractMessageHandleService;
import zoeque.parroticator.configuration.TcpServerConfiguration;

/**
 * The serializer class for the message {@link TcpInboundGateway} receives.
 */
@Slf4j
@Component
public class ParroticatorCustomizedSerializer
        implements Serializer<byte[]>, Deserializer<byte[]> {

  @Value("${zoeque.parroticator.message.length:1024}")
  int maxMessageLength;

  int STX = 0x02;
  int ETX = 0x03;

  /**
   * The deserializer process for the message.
   * When this process receives the message, start checking the message text.
   * If the text is STX, this process starts to build the message.
   * When ETX is received, building the message will be stopped.
   * Built message will be published to {@link AbstractMessageHandleService}.
   *
   * @param inputStream the input stream
   * @return built message includes STX, ETX
   * @throws IOException Information about this method
   *                     will be published to {@link TcpServerNotificationService}
   */
  @NotNull
  @Override
  public byte[] deserialize(InputStream inputStream) throws IOException {
    try {
      byte[] fullMessage = new byte[maxMessageLength];
      boolean receiveFlag = false;
      int idx = 0;
      int messageLength = 0;
      while (true) {
        byte messageText = (byte) inputStream.read();
        // starts building message when STX received
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
      log.info("Received message : " + new String(receivedFullMessage,
              Charset.defaultCharset()));
      return receivedFullMessage;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


  /**
   * The serializer process.
   * This method sends the message to the client.
   * The message is handled by the {@link MessageHandleService} and received by this method.
   * The received message will be sent by the {@link OutputStream}.
   * Connection configurations are defined in {@link TcpServerConfiguration}
   *
   * @param message      the object to serialize
   * @param outputStream the output stream
   * @throws IOException is thrown when the message cannot be sent by a stream.
   */
  @Override
  public void serialize(@NotNull byte[] message,
                        OutputStream outputStream) throws IOException {
    outputStream.write(message);
    outputStream.flush();
  }
}
