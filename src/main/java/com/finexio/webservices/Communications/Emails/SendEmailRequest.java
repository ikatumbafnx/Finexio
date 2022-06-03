package com.finexio.webservices.Communications.Emails;

import org.json.JSONArray;
import org.json.JSONObject;

import io.qameta.allure.Step;

public class SendEmailRequest {

    @Step("Sending an email to {0}")
    public Object SendEmail(String RecipientEmail, String RecipientName, String CCEmail, String CCName, String BCCEmail,
            String BCCName, String EmailBody, String FromEmail, String EmailSubject, String Compaign, String CompanyID,
            String AttachementData, String AttachementName, String AttachementType, String Meta) {

        JSONObject emailMainObj = new JSONObject();
        JSONArray RecipientsArray = new JSONArray();
        JSONObject recipientArrayElements = new JSONObject();
        recipientArrayElements.put("Address", RecipientName);
        recipientArrayElements.put("Name", RecipientEmail);

        RecipientsArray.put(recipientArrayElements);
        emailMainObj.put("Recipients", RecipientsArray);

        JSONArray CCsArray = new JSONArray();
        JSONObject CCsArrayElements = new JSONObject();
        CCsArrayElements.put("Address", CCEmail);
        CCsArrayElements.put("Name", CCName);

        CCsArray.put(CCsArrayElements);
        emailMainObj.put("CC", CCsArray);

        JSONArray BCCsArray = new JSONArray();
        JSONObject BCCsArrayElements = new JSONObject();
        BCCsArrayElements.put("Address", BCCEmail);
        BCCsArrayElements.put("Name", BCCName);

        BCCsArray.put(BCCsArrayElements);
        emailMainObj.put("BCC", BCCsArray);

        JSONArray AttachmentsArray = new JSONArray();
        JSONObject AttachmentsArrayElements = new JSONObject();
        AttachmentsArrayElements.put("Address", BCCEmail);
        AttachmentsArrayElements.put("Name", BCCName);

        AttachmentsArray.put(AttachmentsArrayElements);
        emailMainObj.put("Attachments", AttachmentsArray);

        emailMainObj.put("Body", EmailBody);
        emailMainObj.put("From", FromEmail);
        emailMainObj.put("Subject", EmailSubject);
        emailMainObj.put("Campaign", Compaign);
        emailMainObj.put("CompanyId", CompanyID);
        emailMainObj.put("Meta", Meta);

        return emailMainObj;

    }

    @Step("Sending an email to {0}")
    public Object SendMail(JSONObject emailJsonObject) {

        JSONObject json;
        json = emailJsonObject;
        return json;

    }

}
