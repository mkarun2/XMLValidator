package com.mathu.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class XMLValidatorTest {
    private XMLValidator validator = new XMLValidator();

    @Test
    public void testXmlValidator() {
        boolean result = validator.validate("src/test/resources/xml/books.xml");
        Assert.assertEquals(result, true);
    }

    @Test
    public void testXmlValidatorInvalid() {
        boolean result = validator.validate("src/test/resources/xml/books_bad.xml");
        Assert.assertEquals(result, false);
    }
}
