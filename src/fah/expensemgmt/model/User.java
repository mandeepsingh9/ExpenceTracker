package fah.expensemgmt.model;

import java.sql.Timestamp;

public class User {
	private long id;
	private String username;
	private String passwd;
	private String name;
	private Timestamp lastLogin;
	private boolean isLocked;
	private String roleName;
	private boolean inactiveSw;
	private Timestamp createdDt;
	private String createdBy;
	private Timestamp updatedDt;
	private String updatedBy;
	private String comments;

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		return "User [id=" + id + ", username=" + username + ", passwd=" + passwd + ", name=" + name + ", lastLogin="
				+ lastLogin + ", isLocked=" + isLocked + ", roleName=" + roleName + ", inactiveSw=" + inactiveSw
				+ ", createdDt=" + createdDt + ", createdBy=" + createdBy + ", updatedDt=" + updatedDt + ", updatedBy="
				+ updatedBy + ", comments=" + comments + "]";
	}

}
