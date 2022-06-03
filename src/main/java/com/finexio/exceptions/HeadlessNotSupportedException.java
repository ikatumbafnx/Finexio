package com.finexio.exceptions;
/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class HeadlessNotSupportedException extends IllegalStateException {

    public HeadlessNotSupportedException(String browser) {
        super(String.format("Headless not supported for %s browser", browser));
    }
}
