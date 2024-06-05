package fah.expensemgmt.model;

import java.util.ArrayList;
import java.util.List;

public class ChartDataByCategory {

	List<ChartDataBySubcategory> subcatlist = new ArrayList<ChartDataBySubcategory>();
	private String mainCategory;
	private double finalAmt;

	private String subJson;

	public double getFinalAmt() {
		return finalAmt;
	}

	public void setFinalAmt(double finalAmt) {
		this.finalAmt = finalAmt;
	}

	public String getSubJson() {
		return subJson;
	}

	public void setSubJson(String subJson) {
		this.subJson = subJson;
	}

	public String getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}

	public List<ChartDataBySubcategory> getSubcatlist() {
		return subcatlist;
	}

	public void setSubcatlist(List<ChartDataBySubcategory> subcatlist) {
		this.subcatlist = subcatlist;
	}

}
