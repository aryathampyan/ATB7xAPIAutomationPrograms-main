package com.thetestingacademy.ex_22092024.verification;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CoinDeskAPITest {

    @Test
    public void verifyBPIs() {
        // Step 1: Send the GET request
        Response response = RestAssured.get("https://api.coindesk.com/v1/bpi/currentprice.json");

        // Assert the response status code
        Assert.assertEquals(response.getStatusCode(), 200);

        // Step 2a: Verify there are 3 BPIs (USD, GBP, EUR)
        String[] bpis = {"USD", "GBP", "EUR"};
        for (String bpi : bpis) {
            Assert.assertTrue(response.jsonPath().get("bpi." + bpi) != null, bpi + " BPI is not present.");
        }

        // Step 2b: Verify the GBP description equals 'British Pound Sterling'
        String gbpDescription = response.jsonPath().getString("bpi.GBP.description");
        Assert.assertEquals(gbpDescription, "British Pound Sterling", "GBP description is incorrect.");
    }
}