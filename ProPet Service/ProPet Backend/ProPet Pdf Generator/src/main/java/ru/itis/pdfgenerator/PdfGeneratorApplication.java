package ru.itis.pdfgenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

@SpringBootApplication
public class PdfGeneratorApplication {

    @Bean
    public DirectExchange requestsExchange(@Value("${pdf.reports.requests-exchange-name}") String requestsExchangeName) {
        return new DirectExchange(requestsExchangeName);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable().exclusive().build();
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange,
                           @Value("${pdf.reports.routing-key}") String javaLabRoutingKey) {
        return BindingBuilder.bind(queue).to(directExchange).with(javaLabRoutingKey);
    }

    @Bean
    public MessageConverter jackson2MessageConverter() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver configurer = new ClassLoaderTemplateResolver();
        configurer.setPrefix("templates/");
        configurer.setSuffix(".html");
        configurer.setTemplateMode(TemplateMode.HTML);
        configurer.setCharacterEncoding("UTF-8");
        configurer.setOrder(0);
        configurer.setCheckExistence(true);
        return configurer;
    }

    @Bean
    public FileTemplateResolver fileTemplateResolver(){
        FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
        fileTemplateResolver.setPrefix("templates/");
        fileTemplateResolver.setTemplateMode("HTML");
        fileTemplateResolver.setSuffix(".html");
        fileTemplateResolver.setCharacterEncoding("UTF-8");
        fileTemplateResolver.setOrder(1);
        return fileTemplateResolver;
    }

    public static void main(String[] args) {
        SpringApplication.run(PdfGeneratorApplication.class, args);
    }

}
