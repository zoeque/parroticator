package zoeque.parroticator.application.services;

import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.integration.ip.tcp.connection.SocketInfo;
import org.springframework.integration.ip.tcp.connection.TcpConnection;
import org.springframework.integration.ip.tcp.connection.TcpConnectionCloseEvent;
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

  /**
   * The action when the socket connection is closed.
   *
   * @param event {@link TcpConnectionCloseEvent} is published
   *              when socket communication is finished.
   */
  @EventListener
  public void processConnectionCloseEvent(TcpConnectionCloseEvent event) {
    SocketInfo socketInfo = ((TcpConnection) event.getSource()).getSocketInfo();
    log.info("The socket connection is closed : {}", event.getConnectionId());
    Optional.ofNullable(socketInfo.getRemoteSocketAddress())
            .map(Objects::toString)
            .orElse("-");
  }
}
