package zoeq.parroticator.standard.application.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@MessageEndpoint
public class ReceiverUiService {
  private ApplicationEventPublisher publisher;

  public ReceiverUiService(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  private static final byte STX = 0x02;
  private static final byte ETX = 0x03;
  @Value("${zoeque.parroticator.message.length:1024}")
  int messageLength;

  @ServiceActivator(inputChannel = "inputChannel", async = "true")
  public void receiveMessage(byte[] fullMessage) throws IllegalStateException {
    if(fullMessage.length == 0){
      log.warn("Received message has no text");
      return;
    }
    log.info("Handle the message to echo");
  }
}
