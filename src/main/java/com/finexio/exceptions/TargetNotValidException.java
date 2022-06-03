
package com.finexio.exceptions;
/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class TargetNotValidException extends IllegalStateException {

    public TargetNotValidException(String target) {
        super(String.format("Target %s not supported. Use either local or gird", target));
    }

}
