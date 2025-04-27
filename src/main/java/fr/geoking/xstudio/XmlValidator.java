package fr.geoking.xstudio;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class XmlValidator  {
    private File xmlFile;
    private File xsdFile;

    public List<String> validateXml() {
        return validateFile(xmlFile.getAbsolutePath(), xsdFile.getAbsolutePath());
    }

    public List<String> validateFile(String xmlFilePath, String xsdFilePath) {
        List<String> errors = new ArrayList<>();

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFilePath));
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new org.xml.sax.ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    errors.add("Warning: " + exception.getMessage());
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    errors.add("Error: " + exception.getMessage());
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    errors.add("Fatal Error: " + exception.getMessage());
                }
            });

            Source source = new StreamSource(new File(xmlFilePath));
            validator.validate(source);

        } catch (SAXException e) {
            errors.add("SAX Exception: " + e.getMessage());
        } catch (IOException e) {
            errors.add("IO Exception: " + e.getMessage());
        }
        return errors;
    }

}