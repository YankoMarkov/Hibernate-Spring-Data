package mostwanted.domain.dtos.bindings.raceEntry;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "race-entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportDto {
	
	@XmlAttribute(name = "has-finished")
	private boolean hasFinished;
	
	@XmlAttribute(name = "finish-time")
	private Double finishTime;
	
	@XmlAttribute(name = "car-id")
	private Long carId;
	
	@XmlElement(name = "racer")
	private String racer;
	
	public boolean isHasFinished() {
		return hasFinished;
	}
	
	public void setHasFinished(boolean hasFinished) {
		this.hasFinished = hasFinished;
	}
	
	public Double getFinishTime() {
		return finishTime;
	}
	
	public void setFinishTime(Double finishTime) {
		this.finishTime = finishTime;
	}
	
	public Long getCarId() {
		return carId;
	}
	
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	
	public String getRacer() {
		return racer;
	}
	
	public void setRacer(String racer) {
		this.racer = racer;
	}
}
