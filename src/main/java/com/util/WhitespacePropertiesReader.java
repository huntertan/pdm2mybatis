package com.util;

import java.io.Reader;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhitespacePropertiesReader extends PropertiesConfiguration.PropertiesReader {
    private static final Logger logger = LoggerFactory.getLogger(WhitespacePropertiesReader.class);

    public WhitespacePropertiesReader(Reader in, char delimiter) {
        super(in, delimiter);
    }

    /**
     * Special algorithm for parsing properties keys with whitespace. This method is called for each non-comment line
     * read from the properties file.
     */
    protected void parseProperty(String line) {
        // simply split the line at the first '=' character
        // (this should be more robust in production code)
        int pos = line.indexOf('=');
        String key = line.substring(0, pos).trim();
        String value = line.substring(pos + 1).trim();

        // now store the key and the value of the property
        initPropertyName(key);
        initPropertyValue(value);
    }

}
