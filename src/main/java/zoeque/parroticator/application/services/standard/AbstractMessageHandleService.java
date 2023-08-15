package zoeque.parroticator.application.services.standard;

import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import zoeque.parroticator.configuration.TcpServerConfiguration;

/**
 * The class to handle the message received by {@link TcpInboundGateway}.
 * The configuration of the gateway, connection, and else is defined in
 * {@link TcpServerConfiguration}.
 */
@Slf4j
public abstract class AbstractMessageHandleService {

  @Value("${zoeque.parroticator.message.encoding:SJIS}")
  String messageEncoding;

  /**
   * To handle message with the specified process.
   *
   * @param fullMessage received message includes STX and ETX
   * @return byte array message with no conversion.
   */
  public byte[] handleMessage(byte[] fullMessage) {
    if (fullMessage.length <= 2) {
      // message contains only STX and ETX
      log.warn("Received message has no text");
      return fullMessage;
    }
    log.info("Handle the message to echo : " + new String(fullMessage,
            Charset.forName(messageEncoding)));
    return fullMessage;
  }
}
