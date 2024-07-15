package pegas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class IntegrationConfig {
    @Value("${integration.path}")
    private String path;

    @Bean
    public MessageChannel textInputChannel(){
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fileWriteChannel(){
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "textInputChannel", outputChannel = "fileWriteChannel")
    public GenericTransformer<String, String> myTransformer(){
        return text-> text.toLowerCase().trim();
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriteChannel")
    public FileWritingMessageHandler myMessageHandler(){
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(path));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }
}
