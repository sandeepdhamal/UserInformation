package com.newvision.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
//@Data
@Table(name = "user_details")
//@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "password")
	private String password;

	@Column(name = "reminder_query_question")
	private String reminderQueryQuestion;

	@Column(name = "reminder_query_answer")
	private String reminderQueryAnswer;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middlea_name")
	private String middleaName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "job_title")
	private String jobTitle;

	@Column(name = "login_date")
	private Date loginDate;

	@Column(name = "agreed_to_terms_of_use")
	private boolean agreedToTermsOfUse;

	@Column(name = "status")
	private Integer status;

	@Column(name = "email_address_verified")
	private boolean emailAddressVerified;

	@OneToMany(targetEntity = Roles.class, cascade = CascadeType.ALL)
	private List<Roles> roles;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReminderQueryQuestion() {
		return reminderQueryQuestion;
	}

	public void setReminderQueryQuestion(String reminderQueryQuestion) {
		this.reminderQueryQuestion = reminderQueryQuestion;
	}

	public String getReminderQueryAnswer() {
		return reminderQueryAnswer;
	}

	public void setReminderQueryAnswer(String reminderQueryAnswer) {
		this.reminderQueryAnswer = reminderQueryAnswer;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleaName() {
		return middleaName;
	}

	public void setMiddleaName(String middleaName) {
		this.middleaName = middleaName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public boolean isAgreedToTermsOfUse() {
		return agreedToTermsOfUse;
	}

	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		this.agreedToTermsOfUse = agreedToTermsOfUse;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean isEmailAddressVerified() {
		return emailAddressVerified;
	}

	public void setEmailAddressVerified(boolean emailAddressVerified) {
		this.emailAddressVerified = emailAddressVerified;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

}