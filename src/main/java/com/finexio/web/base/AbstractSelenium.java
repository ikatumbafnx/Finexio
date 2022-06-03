/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:53:46
 * @modify date 2022-06-03 15:53:46
 * @desc [description]
 */
package com.finexio.web.base;

import org.openqa.selenium.WebDriver;

public class AbstractSelenium{

    public WebDriver driver;
    public AbstractSelenium(WebDriver driver){
        this.driver = driver;
    }


}
