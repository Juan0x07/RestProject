package fr.insa.sosa.RestProject;

public class Sensor extends Device{
	private String featureOfInterest;
	private int measuredValue;
	private String unit;
	
	public Sensor(String id, String type){
		super();
		this.setId(id);
		this.setType(type);	
	}

	public String getFeatureOfInterest() {
		return featureOfInterest;
	}

	public void setFeatureOfInterest(String featureOfInterest) {
		this.featureOfInterest = featureOfInterest;
	}

	public int getMeasuredValue() {
		return measuredValue;
	}

	public void setMeasuredValue(int measuredValue) {
		this.measuredValue = measuredValue;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
