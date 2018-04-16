package cn.szcloudtech.seclvlite.entity;

import java.io.Serializable;

public class SecLevelBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2416621246800815445L;
	private String year;
	private String month;
	private String day;
	private String hour;
	private String min;
	private String sec;
	private String stationName;
	private int windDirection;
	private float windSpeed;
	private float relativeHumidity;
	private float temperature;
	private float pressure;
	private float pressure1;
	private float pressure2;
	private float pressure3;
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getSec() {
		return sec;
	}
	public void setSec(String sec) {
		this.sec = sec;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public float getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}
	public int getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(int windDirection) {
		this.windDirection = windDirection;
	}
	public float getRelativeHumidity() {
		return relativeHumidity;
	}
	public void setRelativeHumidity(float relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	public float getPressure() {
		return pressure;
	}
	public void setPressure(float pressure) {
		this.pressure = pressure;
	}
	public float getPressure1() {
		return pressure1;
	}
	public void setPressure1(float pressure1) {
		this.pressure1 = pressure1;
	}
	public float getPressure2() {
		return pressure2;
	}
	public void setPressure2(float pressure2) {
		this.pressure2 = pressure2;
	}
	public float getPressure3() {
		return pressure3;
	}
	public void setPressure3(float pressure3) {
		this.pressure3 = pressure3;
	}
}
