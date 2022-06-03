/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:49:51
 * @modify date 2022-06-03 15:49:51
 * @desc [description]
 */
package com.finexio.exceptions;

public class HeadlessNotSupportedException extends IllegalStateException {

    public HeadlessNotSupportedException(String browser) {
        super(String.format("Headless not supported for %s browser", browser));
    }
}
