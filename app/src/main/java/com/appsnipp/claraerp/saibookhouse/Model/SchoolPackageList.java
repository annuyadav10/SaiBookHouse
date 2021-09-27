package com.appsnipp.claraerp.saibookhouse.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SchoolPackageList{

	@SerializedName("response")
	private List<ResponseItem> response;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public List<ResponseItem> getResponse(){
		return response;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}

	public class ResponseItem{

		@SerializedName("package_language")
		private String packageLanguage;

		@SerializedName("school_id")
		private String schoolId;

		@SerializedName("package_name")
		private String packageName;

		@SerializedName("school_name")
		private String schoolName;

		@SerializedName("package_id")
		private String packageId;

		@SerializedName("class_name")
		private String className;

		@SerializedName("package_price")
		private String packagePrice;

		public String getPackageLanguage(){
			return packageLanguage;
		}

		public String getSchoolId(){
			return schoolId;
		}

		public String getPackageName(){
			return packageName;
		}

		public String getSchoolName(){
			return schoolName;
		}

		public String getPackageId(){
			return packageId;
		}

		public String getClassName(){
			return className;
		}

		public String getPackagePrice(){
			return packagePrice;
		}
	}
}