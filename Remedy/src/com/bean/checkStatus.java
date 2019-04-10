package com.bean;

public class checkStatus {
	private int id;
    private String status,name,treatment;
	public checkStatus() {
		super();
	}
	public checkStatus(int id, String name, String treatment, String status) {
		super();
		this.id = id;
		this.treatment = treatment;
		this.status = status;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTreatment() { return treatment; }
	public void setTreatment(String treatment) { this.treatment = treatment; }

	@Override
	public String toString() {
		return "checkStatus [id=" + id + ", status=" + status + ", name=" + name + "]";
	}
    
	

}
