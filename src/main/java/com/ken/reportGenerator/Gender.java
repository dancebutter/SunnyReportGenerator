package com.ken.reportGenerator;

public class Gender {
	String ta;
	String tas;
	String ta_i;
	String tas_i;
	public Gender(String gender) {
		if(gender == "m") {
			ta = "he";
			tas = "his";
			ta_i = "He";
			tas_i = "His";
		} else if(gender == "f") {
			ta = "she";
			tas = "her";
			ta_i = "She";
			tas_i = "Her";
		} else {
			ta = "he/she";
			tas = "his/her";
			ta_i = "He/She";
			tas_i = "His/Her";
		}
	}
}
