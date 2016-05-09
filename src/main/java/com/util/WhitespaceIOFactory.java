package com.util;

import java.io.Reader;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhitespaceIOFactory extends PropertiesConfiguration.DefaultIOFactory {
    private static final Logger logger = LoggerFactory.getLogger(WhitespaceIOFactory.class);

    /**
     * Return our special properties reader.
     */
    public PropertiesReader createPropertiesReader(Reader in, char delimiter) {
        return new WhitespacePropertiesReader(in, delimiter);
    }

}
