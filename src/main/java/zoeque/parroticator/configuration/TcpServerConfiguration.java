package zoeque.parroticator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;
import zoeque.parroticator.configuration.serializer.ParroticatorCustomizedSerializer;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class TcpServerConfiguration {
  @Value("${zoeque.parroticator.server.port:2000}")
  int portNumber;

  ParroticatorCustomizedSerializer parroticatorCustomizedSerializer;

  @Autowired
  public TcpServerConfiguration(ParroticatorCustomizedSerializer parroticatorCustomizedSerializer) {
    this.parroticatorCustomizedSerializer = parroticatorCustomizedSerializer;
  }

  @Bean
  public MessageChannel inputChannel() {
    return new DirectChannel();
  }

  @Bean
  public MessageChannel outputChannel() {
    return new DirectChannel();
  }

  @Bean
  public AbstractServerConnectionFactory serverConnectionFactory() {
    TcpNetServerConnectionFactory factory = new TcpNetServerConnectionFactory(portNumber);
    factory.setDeserializer(parroticatorCustomizedSerializer);
    factory.setSerializer(parroticatorCustomizedSerializer);
    factory.setSoTimeout(0);
    factory.setSingleUse(true);
    return factory;
  }

  @Bean
  public TcpInboundGateway tcpInboundGateway(AbstractServerConnectionFactory serverConnectionFactory) {
    TcpInboundGateway tcpInboundGateway = new TcpInboundGateway();
    tcpInboundGateway.setConnectionFactory(serverConnectionFactory);
    tcpInboundGateway.setRequestChannel(inputChannel());
    tcpInboundGateway.setReplyChannel(outputChannel());
    return tcpInboundGateway;
  }

  @Bean
  public AbstractClientConnectionFactory clientConnectionFactory() {
    TcpNetClientConnectionFactory factory
            = new TcpNetClientConnectionFactory("localhost", portNumber);
    factory.setSerializer(parroticatorCustomizedSerializer);
    factory.setDeserializer(parroticatorCustomizedSerializer);
    factory.setSingleUse(true);
    return factory;
  }
  @Bean
  public TcpOutboundGateway tcpOutboundGateway(AbstractClientConnectionFactory clientConnectionFactory) {
    TcpOutboundGateway tcpOutboundGateway = new TcpOutboundGateway();
    tcpOutboundGateway.setReplyChannel(outputChannel());
    return tcpOutboundGateway;
  }
}
