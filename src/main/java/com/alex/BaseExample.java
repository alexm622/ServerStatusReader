package com.alex;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibasco.agql.core.exceptions.AsyncGameLibUncheckedException;
import com.ibasco.agql.core.utils.*;

abstract public class BaseExample implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(BaseExample.class);
    private static final String EXAMPLE_PROP_FILE = "example.properties";
    private Properties exampleProps = new Properties();

    abstract public void run() throws Exception;

    public BaseExample() {
        loadProps();
    }

    private void loadProps() {
        InputStream is;

        // First try loading from the current directory
        try {
            File f = new File(EXAMPLE_PROP_FILE);
            is = new FileInputStream(f);
        } catch (Exception e) {
            is = null;
        }

        try {
            if (is == null) {
                // Try loading from classpath
                is = getClass().getResourceAsStream(EXAMPLE_PROP_FILE);
            }
            if (is == null) {
                File f = new File(EXAMPLE_PROP_FILE);
                OutputStream out = new FileOutputStream(f);
                exampleProps.store(out, "AUTO GENERATED FILE");
            }
            // Try loading properties from the file (if found)
            exampleProps.load(is);
        } catch (Exception e) {
        }
    }

    public void saveProp(String property, String value) {
        try {
            String tmpValue = value == null ? "null" : value;
            exampleProps.setProperty(property, tmpValue);
            File f = new File(EXAMPLE_PROP_FILE);
            OutputStream out = new FileOutputStream(f);
            exampleProps.store(out, String.format("From '%s'", this.getClass().getSimpleName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getProp(String propertyName) {
        String tmp = exampleProps.getProperty(propertyName);
        return "null".equalsIgnoreCase(tmp) ? null : tmp;
    }

    protected String promptInput(String message, boolean required) {
        return promptInput(message, required, null);
    }

    protected Boolean promptInputBool(String message, boolean required, String defaultReturnValue) {
        return promptInputBool(message, required, defaultReturnValue, null);
    }

    protected Boolean promptInputBool(String message, boolean required, String defaultReturnValue, String defaultProperty) {
        String tmpVal = promptInput(message, required, defaultReturnValue, defaultProperty);
        return tmpVal != null ? BooleanUtils.toBoolean(tmpVal) : null;
    }

    protected String promptInput(String message, boolean required, String defaultReturnValue) {
        return promptInput(message, required, defaultReturnValue, null);
    }

    @SuppressWarnings("unchecked")
    protected String promptInput(String message, boolean required, String defaultReturnValue, String defaultProperty) {
        Scanner userInput = new Scanner(System.in);
        String returnValue;
        //perform some bit of magic to determine if the prompt is a password type
        boolean inputEmpty, isPassword = StringUtils.containsIgnoreCase(message, "password");
        int retryCounter = 0;
        String defaultValue = defaultReturnValue;

        //Get value from file (if available)
        if (!StringUtils.isEmpty(defaultProperty)) {
            if (isPassword) {
                try {
                    String defaultProp = getProp(defaultProperty);
                    if (!StringUtils.isEmpty(defaultProp))
                        defaultValue = EncryptUtils.decrypt(defaultProp);
                } catch (Exception e) {
                    throw new AsyncGameLibUncheckedException(e);
                }
            } else {
                defaultValue = getProp(defaultProperty);
            }
        }

        do {
            if (!StringUtils.isEmpty(defaultValue)) {
                if (isPassword) {
                    System.out.printf("%s [%s]: ", message, StringUtils.replaceAll(defaultValue, ".", "*"));
                } else
                    System.out.printf("%s [%s]: ", message, defaultValue);
            } else {
                System.out.printf("%s: ", message);
            }
            System.out.flush();
            returnValue = StringUtils.defaultIfEmpty(userInput.nextLine(), defaultValue);
            inputEmpty = StringUtils.isEmpty(returnValue);
        } while ((inputEmpty && ++retryCounter < 3) && required);

        //If the token is still empty, throw an error
        if (inputEmpty && required) {
            System.err.println("Required parameter is missing");
        } else if (inputEmpty && !StringUtils.isEmpty(defaultValue)) {
            returnValue = defaultValue;
        }

        //Save to properties file
        if (!StringUtils.isEmpty(defaultProperty)) {
            if (isPassword) {
                try {
                    saveProp(defaultProperty, EncryptUtils.encrypt(returnValue));
                } catch (Exception e) {
                    throw new AsyncGameLibUncheckedException(e);
                }
            } else {
                saveProp(defaultProperty, returnValue);
            }
        }

        return returnValue;
    }


}