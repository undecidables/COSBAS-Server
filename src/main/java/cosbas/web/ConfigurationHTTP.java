package cosbas.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Szymon
 */
@Configuration
@EnableAutoConfiguration
public class ConfigurationHTTP {

    @Bean
    public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof JettyEmbeddedServletContainerFactory) {
                    configureJetty((JettyEmbeddedServletContainerFactory) container);
                }
            }

            private void configureJetty(JettyEmbeddedServletContainerFactory jettyFactory) {
                jettyFactory.addServerCustomizers(new JettyServerCustomizer() {

                    @Override
                    public void customize(Server server) {
                        ServerConnector serverConnector = new ServerConnector(server);
                        serverConnector.setPort(8080);
                        server.addConnector(serverConnector);
                    }
                });
            }
        };
    }
}
