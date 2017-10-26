package com.mathu.validator;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLValidator {
    private static final Logger LOGGER = Logger.getLogger(XMLValidator.class.getName());

    public XMLValidator() {
        LOGGER.setLevel(Level.ALL);
    }

    public boolean validate(String filePath) {
        if (filePath != null && filePath != "") {
            File fileObj = new File(filePath);
            System.out.println(fileObj.getAbsolutePath());
            boolean exists = fileObj.exists();
            if (exists && fileObj.isFile()) {
                if (fileObj.isDirectory()) {
                    LOGGER.severe("File " + filePath + " is a directory. Please provide a file");
                    return false;
                } else {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setValidating(false);
                    factory.setNamespaceAware(true);
                    DocumentBuilder builder = null;

                    try {
                        builder = factory.newDocumentBuilder();
                    } catch (ParserConfigurationException var9) {
                        LOGGER.severe(var9.toString());
                        return false;
                    }

                    builder.setErrorHandler(new XMLValidator.SimpleErrorHandler());

                    try {
                        builder.parse(new InputSource(filePath));
                        return true;
                    } catch (SAXException var7) {
                        LOGGER.severe(var7.toString());
                        return false;
                    } catch (IOException var8) {
                        LOGGER.severe(var8.toString());
                        return false;
                    }
                }
            } else {
                LOGGER.severe("File " + filePath + " does not exists or is not a file");
                return false;
            }
        } else {
            LOGGER.severe("Invalid file path provided. Please give the entire absolute file path eg: /a/b/c.xml");
            return false;
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            LOGGER.severe("Input file argument required. Please give the entire absolute file path eg: /a/b/c.xml");
            System.exit(1);
        }

        String filePath = args[0].trim();
        XMLValidator validator = new XMLValidator();
        if (validator.validate(filePath)) {
            LOGGER.info("File " + filePath + " is valid");
        } else {
            LOGGER.info("File " + filePath + " is NOT valid");
        }

    }

    class SimpleErrorHandler implements ErrorHandler {
        SimpleErrorHandler() {
        }

        public void warning(SAXParseException e) throws SAXException {
            XMLValidator.LOGGER.severe(e.getMessage());
        }

        public void error(SAXParseException e) throws SAXException {
            XMLValidator.LOGGER.severe(e.getMessage());
        }

        public void fatalError(SAXParseException e) throws SAXException {
            XMLValidator.LOGGER.severe(e.getMessage());
        }
    }
}
