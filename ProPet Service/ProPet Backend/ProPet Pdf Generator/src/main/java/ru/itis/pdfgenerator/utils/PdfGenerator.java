package ru.itis.pdfgenerator.utils;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ru.itis.pdfgenerator.dto.PetTransferTreatyData;
import ru.itis.pdfgenerator.exceptions.pdf.PdfGenerateException;

import java.io.*;

@Component
public class PdfGenerator {

    protected final String TEMPLATE_DIRECTORY;
    protected final String TEMPLATE_FILE_NAME;
    protected final Integer SIZE_LIMIT;
    protected final SpringTemplateEngine templateEngine;
    protected final String EXTERNAL_RESOURCES;

    @Autowired
    public PdfGenerator(
            @Value("${pdf.template_directory}") String directory,
            @Value("${pdf.pet_transfer_treaty_file}") String fileName,
            @Value("${pdf.size_limit}") Integer sizeLimit,
            @Value("${pdf.external_resources}") String externalResources,
            SpringTemplateEngine templateEngine
    ){
        TEMPLATE_DIRECTORY = directory;
        TEMPLATE_FILE_NAME = fileName;
        SIZE_LIMIT = sizeLimit;
        EXTERNAL_RESOURCES = externalResources;
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(PetTransferTreatyData reportData){
        try{
            Context context = getContext(reportData);
            String html = loadAndFillTemplate(context);
            return renderPdf(html);
        } catch (Exception ex){
            throw new PdfGenerateException(ex);
        }
    }

    private Context getContext(PetTransferTreatyData treatyData) {
        Context context = new Context();
        context.setVariable("treaty_data", treatyData);
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process(TEMPLATE_FILE_NAME, context);
    }

    private byte[] renderPdf(String html) throws IOException, DocumentException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(SIZE_LIMIT);
        OutputStream outputStream = new BufferedOutputStream(byteStream);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        ITextFontResolver resolver = renderer.getFontResolver();
        final ClassPathResource fonts = new ClassPathResource("fonts/TimesNewRoman.ttc");
        resolver.addFont(fonts.getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.setDocumentFromString(html, new ClassPathResource(EXTERNAL_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        return byteStream.toByteArray();
    }

}
