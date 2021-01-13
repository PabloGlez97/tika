package org.apache.tika.extractor;

import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.parser.ParseContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExtractorTest {
    private  EmbeddedDocumentUtil embeddedDocumentUtil;
    private ParseContext context;

    @Before
    public void setUp() {

        context = new ParseContext();
        embeddedDocumentUtil = new EmbeddedDocumentUtil(context);
    }

    @Test
    public void testSinContentType() {

        InputStream isPNG = getClass().getResourceAsStream("Prueba.png");

        Metadata metadata= new Metadata();
        //String mimeString = metadata.get(Metadata.CONTENT_TYPE);
        TikaInputStream stream = TikaInputStream.get(isPNG);




        String extension = embeddedDocumentUtil.getExtension(stream, metadata);
        Assert.assertEquals(".png", extension);
        Assert.assertEquals("image/png", metadata.get(Metadata.CONTENT_TYPE));

        

    }

    @Test
    public void testConContentType() {

        InputStream isPNG = getClass().getResourceAsStream("Vacio");

        Metadata metadata= new Metadata();
        metadata.add(Metadata.CONTENT_TYPE, "image/jpeg");
        TikaInputStream stream = TikaInputStream.get(isPNG);




        String extension = embeddedDocumentUtil.getExtension(stream, metadata);
        Assert.assertEquals(".jpg", extension);
        Assert.assertEquals("image/jpeg", metadata.get(Metadata.CONTENT_TYPE));



    }

    @Test
    public void testConContentTypeInvalido() {

        InputStream isPNG = getClass().getResourceAsStream("Prueba.png");

        Metadata metadata= new Metadata();
        metadata.add(Metadata.CONTENT_TYPE, "?");
        TikaInputStream stream = TikaInputStream.get(isPNG);




        String extension = embeddedDocumentUtil.getExtension(stream, metadata);
        Assert.assertEquals(".png", extension);
        Assert.assertEquals("image/png", metadata.get(Metadata.CONTENT_TYPE));



    }


    @Test
    public void testConTipoMimeInvalido() {

        context.set(Detector.class, new Detector() {
            @Override
            public MediaType detect(InputStream input, Metadata metadata) throws IOException {
                throw new IOException();
            }
        });

        InputStream isPNG = getClass().getResourceAsStream("Vacio");

        Metadata metadata= new Metadata();
        TikaInputStream stream = TikaInputStream.get(isPNG);

        String extension = embeddedDocumentUtil.getExtension(stream, metadata);
        Assert.assertEquals(".bin", extension);
        Assert.assertEquals(null, metadata.get(Metadata.CONTENT_TYPE));

    }

    @Test
    public void testSinContentTypeBlack() {

        InputStream isPNG = getClass().getResourceAsStream("Prueba.png");

        Metadata metadata= new Metadata();
        TikaInputStream stream = TikaInputStream.get(isPNG);

        String extension = embeddedDocumentUtil.getExtension(stream, metadata);
        Assert.assertEquals(".png", extension);
        Assert.assertEquals("image/png", metadata.get(Metadata.CONTENT_TYPE));



    }

    @Test
    public void testConContentTypeBlack() {

        InputStream isPNG = getClass().getResourceAsStream("Prueba.png");

        Metadata metadata= new Metadata();
        metadata.add(Metadata.CONTENT_TYPE, "image/jpeg");
        TikaInputStream stream = TikaInputStream.get(isPNG);




        String extension = embeddedDocumentUtil.getExtension(stream, metadata);
        Assert.assertEquals(".jpg", extension);
        Assert.assertEquals("image/jpeg", metadata.get(Metadata.CONTENT_TYPE));



    }

}
