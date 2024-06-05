package fah.expensemgmt.model;

import java.sql.Timestamp;

public class ResponsiblePerson {

	private long id;
	private String responsiblePerson;
	private boolean inactiveSw;
	private Timestamp createdDt;
	private String createdBy;
	private Timestamp updatedDt;
	private String updatedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public boolean isInactiveSw() {
		return inactiveSw;
	}

	public void setInactiveSw(boolean inactiveSw) {
		this.inactiveSw = inactiveSw;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
