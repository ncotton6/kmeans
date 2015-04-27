package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import datareader.CSVValue;

/**
 * The {@link Data} class has a very simple of job of holding data that is
 * parsed from a csv file.
 * 
 * @author Nathaniel Cotton
 * 
 */
public class Data {

	private static int idcounter = 1;
	private int id = -1;
	private double seatRow, seatAisle, mealChoice, bodyTemp, urineCloudy,
			urineDarkness, bmDark, dmHardness, bmFloats, pulseRate,
			bloodPressure, whtBldCellCnt, nausea, diffSwallowingDryMouth;

	public Data() {
		this.id = idcounter++;
	}

	/**
	 * @return the seatRow
	 */
	public double getSeatRow() {
		return seatRow;
	}

	@UseAttribute
	public double getNormSeatRow() {
		return getSeatRow() / 5;
	}

	/**
	 * @param seatRow
	 *            the seatRow to set
	 */
	@CSVValue(ColumnName = "SEAT_ROW", ColumnType = Double.class)
	public void setSeatRow(double seatRow) {
		this.seatRow = seatRow;
	}

	/**
	 * @return the seatAisle
	 */
	public double getSeatAisle() {
		return seatAisle;
	}

	@UseAttribute
	public double getNormSeatAisle() {
		return getSeatAisle() / 5;
	}

	/**
	 * @param seatAisle
	 *            the seatAisle to set
	 */
	@CSVValue(ColumnName = "SEAT_AISLE", ColumnType = Double.class)
	public void setSeatAisle(double seatAisle) {
		this.seatAisle = seatAisle;
	}

	/**
	 * @return the mealChoice
	 */
	public double getMealChoice() {
		return mealChoice;
	}

	@UseAttribute
	public double getNormMealChoice() {
		return (getMealChoice() - 2) / 8000;
	}

	/**
	 * @param mealChoice
	 *            the mealChoice to set
	 */
	@CSVValue(ColumnName = "MEAL CHOICE", ColumnType = Double.class)
	public void setMealChoice(double mealChoice) {
		this.mealChoice = mealChoice;
	}

	/**
	 * @return the bodyTemp
	 */
	public double getBodyTemp() {
		return bodyTemp;
	}

	@UseAttribute
	public double getNormBodyTemp() {
		return (bodyTemp - 98.6) / 0.5;
	}

	/**
	 * @param bodyTemp
	 *            the bodyTemp to set
	 */
	@CSVValue(ColumnName = "BODY TEMP", ColumnType = Double.class)
	public void setBodyTemp(double bodyTemp) {
		this.bodyTemp = bodyTemp;
	}

	/**
	 * @return the urineCloady
	 */
	public double getUrineCloudy() {
		return urineCloudy;
	}

	@UseAttribute
	public double getNormUrineCloudy() {
		return getUrineCloudy() - 0.5;
	}

	/**
	 * @param urineCloudy
	 *            the urineCloady to set
	 */
	@CSVValue(ColumnName = "URINE CLOUDY", ColumnType = Double.class)
	public void setUrineCloudy(double urineCloudy) {
		this.urineCloudy = urineCloudy;
	}

	/**
	 * @return the urineDarkness
	 */
	public double getUrineDarkness() {
		return urineDarkness;
	}

	@UseAttribute
	public double getNormUrineDarkness() {
		return getUrineDarkness() - 3.5;
	}

	/**
	 * @param urineDarkness
	 *            the urineDarkness to set
	 */
	@CSVValue(ColumnName = "URINE DARKNESS", ColumnType = Double.class)
	public void setUrineDarkness(double urineDarkness) {
		this.urineDarkness = urineDarkness;
	}

	/**
	 * @return the bmDark
	 */
	public double getBmDark() {
		return bmDark;
	}

	@UseAttribute
	public double getNormBmDark() {
		return (getBmDark() - 3) / 1000;
	}

	/**
	 * @param bmDark
	 *            the bmDark to set
	 */
	@CSVValue(ColumnName = "BM DARK", ColumnType = Double.class)
	public void setBmDark(double bmDark) {
		this.bmDark = bmDark;
	}

	/**
	 * @return the dmHardness
	 */
	public double getDmHardness() {
		return dmHardness;
	}

	@UseAttribute
	public double getNormDmHardness() {
		return (getDmHardness() - 3) / 1000;
	}

	/**
	 * @param dmHardness
	 *            the dmHardness to set
	 */
	@CSVValue(ColumnName = "BM HARDNESS", ColumnType = Double.class)
	public void setDmHardness(double dmHardness) {
		this.dmHardness = dmHardness;
	}

	/**
	 * @return the bmFloats
	 */
	public double getBmFloats() {
		return bmFloats;
	}

	@UseAttribute
	public double getNormBmFloats() {
		return (getBmFloats() - 0.5) / 1000;
	}

	/**
	 * @param bmFloats
	 *            the bmFloats to set
	 */
	@CSVValue(ColumnName = "BM FLOATS", ColumnType = Double.class)
	public void setBmFloats(double bmFloats) {
		this.bmFloats = bmFloats;
	}

	/**
	 * @return the pulseRate
	 */
	public double getPulseRate() {
		return pulseRate;
	}

	@UseAttribute
	public double getNormPulseRate() {
		return (getPulseRate() - 80) / 10;
	}

	/**
	 * @param pulseRate
	 *            the pulseRate to set
	 */
	@CSVValue(ColumnName = "PULSE RATE", ColumnType = Double.class)
	public void setPulseRate(double pulseRate) {
		this.pulseRate = pulseRate;
	}

	/**
	 * @return the bloodPressure
	 */
	public double getBloodPressure() {
		return bloodPressure;
	}

	@UseAttribute
	public double getNormBloodPressure() {
		return (getBloodPressure() - 120) / 10;
	}

	/**
	 * @param bloodPressure
	 *            the bloodPressure to set
	 */
	@CSVValue(ColumnName = "BLOOD PRESSURE", ColumnType = Double.class)
	public void setBloodPressure(double bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	/**
	 * @return the whtBldCellCnt
	 */
	public double getWhtBldCellCnt() {
		return whtBldCellCnt;
	}

	@UseAttribute
	public double getNormWhtBldCellCnt() {
		return (getWhtBldCellCnt() - 9000) / 2000;
	}

	/**
	 * @param whtBldCellCnt
	 *            the whtBldCellCnt to set
	 */
	@CSVValue(ColumnName = "WHT BLD CELL CNT", ColumnType = Double.class)
	public void setWhtBldCellCnt(double whtBldCellCnt) {
		this.whtBldCellCnt = whtBldCellCnt;
	}

	/**
	 * @return the nausea
	 */
	public double getNausea() {
		return nausea;
	}

	@UseAttribute
	public double getNormNausea() {
		return (getNausea() - 0.5) / 5;
	}

	/**
	 * @param nausea
	 *            the nausea to set
	 */
	@CSVValue(ColumnName = "NAUSEA", ColumnType = Double.class)
	public void setNausea(double nausea) {
		this.nausea = nausea;
	}

	/**
	 * @return the diffSwallowingDryMouth
	 */
	public double getDiffSwallowingDryMouth() {
		return diffSwallowingDryMouth;
	}

	@UseAttribute
	public double getNormDiffSwallowingDryMouth() {
		return (getDiffSwallowingDryMouth() - 0.5) / 5;
	}

	/**
	 * @param diffSwallowingDryMouth
	 *            the diffSwallowingDryMouth to set
	 */
	@CSVValue(ColumnName = "DIFF SWALLOWING DRY MOUTH", ColumnType = Double.class)
	public void setDiffSwallowingDryMouth(double diffSwallowingDryMouth) {
		this.diffSwallowingDryMouth = diffSwallowingDryMouth;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public double[] getAsPoint() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		int size = 0;
		for (Method m : Data.class.getDeclaredMethods())
			if (m.getAnnotation(UseAttribute.class) != null)
				++size;
		double[] point = new double[size];
		size = 0;
		for (Method m : Data.class.getDeclaredMethods())
			if (m.getAnnotation(UseAttribute.class) != null)
				point[size++] = (double) m.invoke(this, null);
		return point;
	}
}
