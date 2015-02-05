package com.lidroid.xutils.bean;

public class Doctor {
	
	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", spell_code="
				+ spell_code + ", gender=" + gender + ", birthday=" + birthday
				+ ", mobile_phone=" + mobile_phone + ", email=" + email
				+ ", introduction=" + introduction + ", credential_type="
				+ credential_type + ", credential_type_number="
				+ credential_type_number + ", birthplace=" + birthplace
				+ ", address=" + address + ", nationality=" + nationality
				+ ", citizenship=" + citizenship + ", province=" + province
				+ ", county=" + county + ", marriage=" + marriage
				+ ", home_phone=" + home_phone + ", home_address="
				+ home_address + ", contact=" + contact + ", contact_phone="
				+ contact_phone + ", home_postcode=" + home_postcode
				+ ", hospital_id=" + hospital_id + ", department_id="
				+ department_id + ", professional_title=" + professional_title
				+ ", position=" + position + ", hire_date=" + hire_date
				+ ", certificate_number=" + certificate_number + ", expertise="
				+ expertise + ", degree=" + degree + ", photo=" + photo
				+ ", username=" + username + ", hospital_name=" + hospital_name
				+ "]";
	}

	private int id;
	private String name;
	private String spell_code;
	private String gender;
	private String birthday;
	private String mobile_phone;
	private String email;
	private String introduction;
	private String credential_type;
	private String credential_type_number;          
	private	String birthplace;          
	private	String address;          
	private String nationality;          
	private String citizenship;          
	private String province;          
	private String county;          
	private String marriage;          
	private String home_phone;          
	private String home_address;          
	private String contact;          
	private String contact_phone;          
	private String home_postcode;          
	private int hospital_id;
	private String department_id;          
	private String professional_title;          
	private String position;          
	private	String hire_date;          
	private String certificate_number;          
	private String expertise;          
	private String degree;          
	private String photo;
	private String username;
	private String hospital_name; 

	public void setHospitalId(int id) {
		hospital_id = id;
	}
	
	public int getHospitalId() {
		return hospital_id;
	}
	
	public void setHospitalName(String name) {
		hospital_name = name;
	}
	
	public String getHospitalName() {
		return hospital_name;
	}
	
}

