package apiVerifications;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;
import utils.ExtentReportListner;
import utils.Log;

public class APIVerification extends ExtentReportListner {

	public static void responseCodeValiddation(Response response, int statusCode) {

		try {
			Assert.assertEquals(statusCode, response.getStatusCode());
			test.log(LogStatus.PASS,
					"Successfully validdated status code, status code is :: " + response.getStatusCode());
			Log.info("Successfully validdated status code, status code is :: " + response.getStatusCode());
		} catch (AssertionError e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
			test.log(LogStatus.FAIL,
					"Expected status code is :: " + statusCode + " , insted of getting :: " + response.getStatusCode());
			Log.info("Expected status code is :: " + statusCode + " , insted of getting :: " + response.getStatusCode());
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	public static void responseKeyValidationfromArray(Response response, String key) {
		try {
			JSONArray array = new JSONArray(response.getBody().asString());
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				test.log(LogStatus.PASS, "Validetd values are  " + obj.get(key));
			
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
			Log.info("validate value  is equal to the created name is fail ");
		}
	}

	public static String responseKeyValidationFromJsonObject(Response response, String key) {
		String keyvalue = null;
		try {
			JSONObject json = new JSONObject(response.getBody().asString());
			if (json.has(key) && json.get(key) != null) {
				test.log(LogStatus.PASS, "Sucessfully validated value of " + key + " It is " + json.get(key));
				Log.info("Sucessfully validated value of " + key + " It is " + json.get(key));
				keyvalue = (String) json.get(key);
			} else {
				test.log(LogStatus.FAIL, "Key is not availble");
				Log.info("fail validated value of " + key + " It is " + json.get(key));
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

		return keyvalue;
	}


	public static void responseTimeValidation(Response response) {
		try {
			long time = response.time();
			test.log(LogStatus.INFO, "Api response time is :: " + time);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

}
