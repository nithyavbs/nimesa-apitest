package com.weather.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.InputMismatchException;
import java.util.Scanner;

public class WeatherApi {

	private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int option;

		while (true) {
			System.out.println("1. Get weather\n2. Get Wind Speed\n3. Get Pressure\n0. Exit");
			System.out.print("Enter your option: ");

			try {
				option = sc.nextInt();
				sc.nextLine(); // Consume the newline character
				if (option < 0 || option > 3) {

					System.out.printf("You have not entered a number between 1 and 3. " + "Try again.\n");
					System.out.printf("Enter your option between 0 and 3 only: \n");
//					option = sc.nextInt();

				}
				switch (option) {
				case 1:
					getWeather(sc);
					break;
				case 2:
					getWindSpeed(sc);
					break;
				case 3:
					getPressure(sc);
					break;
				case 0:
					System.out.println("Exiting the program. Goodbye!");
					sc.close();
					 return;
				default:
					System.out.println("Invalid option. Please try again.");
				}
			} catch (InputMismatchException ex) {
				System.out.println("You have entered an invalid choice. Try again.  ");
			}
		}

//        sc.close();
	}

	private static Response fetchDataFromAPI() {
		return RestAssured.get(API_URL);
	}
	
	// Getting Temperature from the input date
	private static void getWeather(Scanner sc) {
		Response response = fetchDataFromAPI();
		JSONObject data = null;

		try {
			data = new JSONObject(response.getBody().asString());
		} catch (JSONException e) {
			System.out.println("Error parsing API response.");
			return;
		}

		System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
		String date = sc.nextLine();

		JSONArray list = data.getJSONArray("list");
		for (int i = 0; i < list.length(); i++) {
			JSONObject item = list.getJSONObject(i);
			String dateTime = item.getString("dt_txt");

			if (dateTime.equals(date)) {
				JSONObject main = item.getJSONObject("main");
				double temperature = main.getDouble("temp");
				System.out.println("Temperature on " + date + ": " + temperature + "°C");
				return;
			}
		}

		System.out.println("Weather data not found for the specified date.");
	}

	// Getting Windspeed from the input date
	private static void getWindSpeed(Scanner sc) {
		Response response = fetchDataFromAPI();
		JSONObject data = null;

		try {
			data = new JSONObject(response.getBody().asString());
		} catch (JSONException e) {
			System.out.println("Error parsing API response.");
			return;
		}

		System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
		String date = sc.nextLine();

		JSONArray list = data.getJSONArray("list");
		for (int i = 0; i < list.length(); i++) {
			JSONObject item = list.getJSONObject(i);
			String dateTime = item.getString("dt_txt");

			if (dateTime.equals(date)) {
				JSONObject wind = item.getJSONObject("wind");
				double windSpeed = wind.getDouble("speed");
				System.out.println("Wind Speed on " + date + ": " + windSpeed + " m/s");
				return;
			}
		}

		System.out.println("Wind Speed data not found for the specified date.");
	}
	//Getting pressure from the input date
	private static void getPressure(Scanner sc) {
		Response response = fetchDataFromAPI();
		JSONObject data = null;

		try {
			data = new JSONObject(response.getBody().asString());
		} catch (JSONException e) {
			System.out.println("Error parsing API response.");
			return;
		}

		System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
		String date = sc.nextLine();

		JSONArray list = data.getJSONArray("list");
		for (int i = 0; i < list.length(); i++) {
			JSONObject item = list.getJSONObject(i);
			String dateTime = item.getString("dt_txt");

			if (dateTime.equals(date)) {
				JSONObject main = item.getJSONObject("main");
				double pressure = main.getDouble("pressure");
				System.out.println("Pressure on " + date + ": " + pressure + " hPa");
				return;
			}
		}

		System.out.println("Pressure data not found for the specified date.");
	}
}
