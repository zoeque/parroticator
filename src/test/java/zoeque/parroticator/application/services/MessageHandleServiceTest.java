package zoeque.parroticator.application.services;

import java.nio.charset.Charset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.test.context.SpringIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import zoeque.parroticator.configuration.TcpServerConfiguration;

@SpringBootTest
@SpringIntegrationTest
@DirtiesContext
public class MessageHandleServiceTest {
  @Autowired
  private MessageHandleService handleService;

  @Test
  public void givenMessage_whenSend_thenReceiveSameMessage() throws InterruptedException {
    byte[] byteMessage = "test".getBytes(Charset.forName("SJIS"));
    byte[] bytes = handleService.handleMessage(byteMessage);
    Assertions.assertEquals(byteMessage, bytes);
  }
}
