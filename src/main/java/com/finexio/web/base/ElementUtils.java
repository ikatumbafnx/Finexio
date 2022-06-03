/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:54:37
 * @modify date 2022-06-03 15:54:37
 * @desc [description]
 */
package com.finexio.web.base;

import com.finexio.pages.AbstractPageObject;

public class ElementUtils extends AbstractPageObject {



    public enum EIdentifier {
        input, checkbox, searchIcon, yesOrNo, isYesOrNoSelected, fieldValue, ratingFieldValueByOperand, ratingFieldValueByInstruction, keyword, childInput, pageLoaded, radioButton, dropdownIcon,
        childDropDownIcon, topMenuTab, topMenuDropdown, addButton, select, dropDownDefaultInput, errorMessage, warningMessage, yes, isYesSelected, no, isNoSelected, switchTab, isSelected, pageTab, dropDownValues, datePicker,
        childFieldValue, fieldEditable, childDropDownDefaultInput, childSelect
    }

    public enum EType {
        equals, contains
    }





}
