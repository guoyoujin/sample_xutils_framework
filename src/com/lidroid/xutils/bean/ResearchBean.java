package com.lidroid.xutils.bean;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ResearchBean implements Serializable{

    @Override
	public String toString() {
		return "Researchs [id=" + id + ", name=" + name + ", intro=" + intro
				+ ", doctor_id=" + doctor_id + ", patient_count="
				+ patient_count + ", upload_state=" + upload_state
				+ ", created_at=" + created_at + "]";
	}

	private Long id;
    private String name;
    private String intro;
    private String doctor_id;
    private String patient_count;
    private String upload_state;
    private String created_at;
    private String image_url;
    
    
    public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public ResearchBean() {
    }

    public ResearchBean(Long id) {
        this.id = id;
    }

    public ResearchBean(Long id, String name, String intro, String doctor_id, String patient_count) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.doctor_id = doctor_id;
        this.patient_count = patient_count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getPatient_count() {
        return patient_count;
    }

    public void setPatient_count(String patient_count) {
        this.patient_count = patient_count;
    }

	public String getUpload_state() {
		return upload_state;
	}

	public void setUpload_state(String upload_state) {
		this.upload_state = upload_state;
	}

}
