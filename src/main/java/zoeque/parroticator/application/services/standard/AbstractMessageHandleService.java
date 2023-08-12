package zoeque.parroticator.application.services.standard;

import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractMessageHandleService {
  public byte[] handleMessage(byte[] fullMessage) {
    if (fullMessage.length == 0) {
      log.warn("Received message has no text");
      return fullMessage;
    }
    log.info("Handle the message to echo" + new String(fullMessage,
            Charset.forName("SJIS")));
    return fullMessage;
  }
}
