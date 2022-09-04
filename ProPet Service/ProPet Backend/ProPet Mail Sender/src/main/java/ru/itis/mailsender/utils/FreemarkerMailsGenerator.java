package ru.itis.mailsender.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.mailsender.dto.ActivationMailForm;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class FreemarkerMailsGenerator implements MailsGenerator {

    protected final Configuration configuration;
    protected final String confirmTemplate;

    @Autowired
    public FreemarkerMailsGenerator(
            Configuration configuration,
            @Value("${mail.confirm_template}") String confirmTemplate
    ){
        this.configuration = configuration;
        this.confirmTemplate = confirmTemplate;
    }

    @Override
    public String getMailForConfirm(ActivationMailForm form, String activationUrl) {
        Template confirmMailTemplate;
        try {
            confirmMailTemplate = configuration.getTemplate(confirmTemplate);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        Map<String, String> attributes = new HashMap<>();
        attributes.put("login", form.getLogin());
        attributes.put("activation_url", activationUrl);
        attributes.put("activation_link", form.getActivationLink());

        StringWriter writer = new StringWriter();
        try {
            confirmMailTemplate.process(attributes, writer);
        } catch (TemplateException | IOException e) {
            throw new IllegalStateException(e);
        }
        return writer.toString();
    }

}
