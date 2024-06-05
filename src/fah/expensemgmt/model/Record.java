package fah.expensemgmt.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Record {

	private long id;
	private Date expenseDt;
	private String incomeExpense;
	private String descr;
	private String mainCategory;
	private String subCategory;
	private float amount;
	private String responsiblePerson;
	private boolean inactiveSw;
	private Timestamp createdDt;
	private String createdBy;
	private Timestamp updatedDt;
	private String updatedBy;
	private String comments;

	// Additional fields for Filtering records on Transaction Screen
	private Date fromDate;
	private Date toDate;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getExpenseDt() {
		return expenseDt;
	}

	public void setExpenseDt(Date expenseDt) {
		this.expenseDt = expenseDt;
	}

	public String getIncomeExpense() {
		return incomeExpense;
	}

	public void setIncomeExpense(String incomeExpense) {
		this.incomeExpense = incomeExpense;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", expenseDt=" + expenseDt + ", incomeExpense=" + incomeExpense + ", descr=" + descr
				+ ", mainCategory=" + mainCategory + ", subCategory=" + subCategory + ", amount=" + amount
				+ ", responsiblePerson=" + responsiblePerson + ", inactiveSw=" + inactiveSw + ", createdDt=" + createdDt
				+ ", createdBy=" + createdBy + ", updatedDt=" + updatedDt + ", updatedBy=" + updatedBy + ", comments="
				+ comments + ", getId()=" + getId() + ", getExpenseDt()=" + getExpenseDt() + ", getIncomeExpense()="
				+ getIncomeExpense() + ", getDescr()=" + getDescr() + ", getMainCategory()=" + getMainCategory()
				+ ", getSubCategory()=" + getSubCategory() + ", getAmount()=" + getAmount()
				+ ", getResponsiblePerson()=" + getResponsiblePerson() + ", isInactiveSw()=" + isInactiveSw()
				+ ", getCreatedDt()=" + getCreatedDt() + ", getCreatedBy()=" + getCreatedBy() + ", getUpdatedDt()="
				+ getUpdatedDt() + ", getUpdatedBy()=" + getUpdatedBy() + ", getComments()=" + getComments()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
