package com.sky.parentalcontrol;

public class DownStreamSystemException extends Exception {
    public DownStreamSystemException(String message, Exception ex) {
        super(message, ex);
    }
}
