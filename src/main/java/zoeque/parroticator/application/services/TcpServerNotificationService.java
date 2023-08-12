package zoeque.parroticator.application.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.TcpConnectionExceptionEvent;
import org.springframework.stereotype.Service;

/**
 * The class to notify the server creation or destroy
 */
@Slf4j
@Service
public class TcpServerNotificationService {
  public TcpServerNotificationService() {
  }

  @EventListener
  public void listenReceiverException(TcpConnectionExceptionEvent event) {
    log.warn("Exception is occurred at the TCP server " + event.getCause());
    // TODO delete
    event.getCause().printStackTrace();
  }
}
