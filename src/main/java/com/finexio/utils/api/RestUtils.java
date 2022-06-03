package com.finexio.utils.api;

import java.util.Base64;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class RestUtils {

    public Response getGETReponse(Object json, String service) {

        RestAssured.baseURI = "https://api.zippopotam.us";
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/json");
        httpRequest.body(json.toString());

        Response response = httpRequest.request(Method.GET, service);

        return response;
    }

    public Response getPOSTReponse(Object json, String service) {

        RestAssured.baseURI = "";
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/json");
        httpRequest.body(json.toString());

        Response response = httpRequest.request(Method.POST, service);

        return response;
    }

    @Step("Retrieving the Response for resquest {0} made to service {1}")
    public Response getStringGETReponse(Object json, String service, String endpoint, String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String servicecall = service + json;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/json");
        httpRequest.header("Authorization", token);
        Response response = httpRequest.request(Method.GET, servicecall);

        return response;
    }

    @Step("Retrieving the Response for resquest {0} made to service {1}")
    public Response getStringGETReponseNoContentType(Object json, String service, String endpoint, String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String servicecall = service + json;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Authorization", token);
        Response response = httpRequest.request(Method.GET, servicecall);

        return response;
    }

    @Step("Retrieving the Response for request made to service call {1}")
    public Response getAuthentPOSTReponse(Object json, String service, String endpoint, String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/json");
        httpRequest.header("Authorization", token);
        httpRequest.body(json.toString());
        Response response = httpRequest.request(Method.POST, service);

        return response;
    }

    @Step("Retrieving the Response for a POST request made to service call {1}")
    public Response getAuthentPOSTReponseWithNoContentType(Object json, String service, String endpoint, String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String servicecall = service + json;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Authorization", token);
        Response response = httpRequest.request(Method.POST, servicecall);

        return response;
    }

    @Step("Retrieving the Response for a POST request made to service call {1}")
    public Response getAuthentPOSTReponseWithNoContentTypeWithBearer(Object json, String service, String endpoint,
            String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String servicecall = service + json;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Authorization", "Bearer " + token);
        Response response = httpRequest.request(Method.POST, servicecall);

        return response;
    }

    @Step("Retrieving the Response for a POST request made to service call {1}")
    public Response getGETReponseWithNoContentType(Object json, String service, String endpoint) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String servicecall = service + json;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.GET, servicecall);

        return response;
    }

    @Step("Retrieving the Response for a Get request made to service call {1}")
    public Response getAuthenticatedGETReponseWithNoContentTypeWithBearer(Object json, String service, String endpoint,
            String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String servicecall = service + json;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Authorization", "Bearer " + token);
        Response response = httpRequest.request(Method.GET, servicecall);

        return response;
    }

    @Step("Retrieving the Response for a Get request made to service call {1}")
    public Response getAuthenticatedGETReponseWithNoContentTypeNoBodyWithBearer(String service, String endpoint,
            String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String servicecall = service;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Authorization", "Bearer " + token);
        Response response = httpRequest.request(Method.GET, servicecall);

        return response;
    }

    @Step("Retrieving the Response for a POST request made to service call {1}")
    public Response getPOSTReponseWithNoContentType(Object json, String service, String endpoint) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String servicecall = service + json;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.POST, servicecall);

        return response;
    }

    @Step("Retrieving the Response for request {1} made")
    public Response getPOSTReponseWithContentTypeText(String json, String service, String endpoint) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/x-www-form-urlencoded");
        httpRequest.body(json);
        Response response = httpRequest.request(Method.POST, service);
        return response;

    }

    @Step("Retrieving the Response for request {1} made")
    public Response getPOSTReponseWithContentTypeTextApplicationJson(String json, String service, String endpoint) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/json");
        // httpRequest.header("Ocp-Apim-Subscription-Key",
        // "fb398be4967949c6bb463311b4b9528f");
        httpRequest.body(json);
        Response response = httpRequest.request(Method.POST, service);
        return response;

    }

    @Step("Retrieving the Response for request made to service call {1}")
    public Response getAuthenticatedGETReponseWithNoBody(String service, String endpoint, String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String authString = "Automation" + ":" + token;
        String base64Credentials = new String(Base64.getEncoder().encode(authString.getBytes()));
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/json");
        httpRequest.header("Authorization", "Basic " + base64Credentials);

        Response response = httpRequest.request(Method.GET, service);

        return response;
    }

    @Step("Retrieving the POST Response for request made to service call {1}")
    public Response getAuthenticatedPOSTReponseBody(Object json, String service, String endpoint, String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String authString = "Automation" + ":" + token;
        String base64Credentials = new String(Base64.getEncoder().encode(authString.getBytes()));
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/json");
        httpRequest.header("Authorization", "Basic " + base64Credentials);
        httpRequest.body(json.toString());
        Response response = httpRequest.request(Method.POST, service);

        return response;
    }

    @Step("Retrieving the Response for Updating TestCase Request made to service call {1}")
    public Response getAuthenticatedPATCHReponseBody(Object json, String service, String endpoint, String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        String authString = "Automation" + ":" + token;
        String base64Credentials = new String(Base64.getEncoder().encode(authString.getBytes()));
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/json");
        httpRequest.header("Authorization", "Basic " + base64Credentials);
        httpRequest.body(json.toString());
        Response response = httpRequest.request(Method.PATCH, service);

        return response;
    }

    @Step("Retrieving the POST Response for request made to service call {1}")
    public Response getAuthenticatedPOSTReponseBodyWithBearer(Object json, String service, String endpoint,
            String token) {

        RestAssured.baseURI = endpoint;
        System.out.println("The Env Tests Running off of is: " + endpoint);
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-type", "application/json");
        httpRequest.header("Authorization", "Bearer " + token);
        httpRequest.body(json.toString());
        Response response = httpRequest.request(Method.POST, service);

        return response;
    }

}
