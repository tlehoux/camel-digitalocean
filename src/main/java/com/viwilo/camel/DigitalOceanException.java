package com.viwilo.camel;

/**
 * Created by thomas on 20/03/2017.
 */
public class DigitalOceanException extends Exception {

    private static final long serialVersionUID = 1L;

    public DigitalOceanException(Throwable e) {
        super(e);
    }

    public DigitalOceanException(String message) {
        super(message);
    }
}