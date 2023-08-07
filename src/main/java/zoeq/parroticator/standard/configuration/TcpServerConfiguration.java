package zoeq.parroticator.standard.configuration;

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
import zoeq.parroticator.standard.configuration.serializer.ParroticatorCustomizedSerializer;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class TcpServerConfiguration {
  @Value("${zoeq.parroticator.server.port:1000}")
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
  MessageChannel outputChannel() {
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

    TcpNetClientConnectionFactory clientConnectionFactory
            = new TcpNetClientConnectionFactory("localhost", portNumber);
    clientConnectionFactory.setSerializer(parroticatorCustomizedSerializer);
    clientConnectionFactory.setDeserializer(parroticatorCustomizedSerializer);
    return clientConnectionFactory;
  }
  @Bean
  public TcpOutboundGateway tcpOutboundGateway() {
    TcpOutboundGateway tcpOutboundGateway = new TcpOutboundGateway();
    tcpOutboundGateway.setConnectionFactory(clientConnectionFactory());
    tcpOutboundGateway.setReplyChannel(outputChannel());
    return tcpOutboundGateway;
  }
}
