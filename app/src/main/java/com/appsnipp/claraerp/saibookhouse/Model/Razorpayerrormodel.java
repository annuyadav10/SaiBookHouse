package com.appsnipp.claraerp.saibookhouse.Model;

import com.google.gson.annotations.SerializedName;

public class Razorpayerrormodel{

	@SerializedName("error")
	private Error error;

	public Error getError(){
		return error;
	}

	public class Error{

		@SerializedName("reason")
		private String reason;

		@SerializedName("metadata")
		private Metadata metadata;

		@SerializedName("code")
		private String code;

		@SerializedName("field")
		private Object field;

		@SerializedName("description")
		private String description;

		@SerializedName("step")
		private String step;

		@SerializedName("source")
		private String source;

		public String getReason(){
			return reason;
		}

		public Metadata getMetadata(){
			return metadata;
		}

		public String getCode(){
			return code;
		}

		public Object getField(){
			return field;
		}

		public String getDescription(){
			return description;
		}

		public String getStep(){
			return step;
		}

		public String getSource(){
			return source;
		}
	}
}