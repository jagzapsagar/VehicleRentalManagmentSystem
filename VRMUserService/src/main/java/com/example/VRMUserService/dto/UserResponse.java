package com.example.VRMUserService.dto;

public class UserResponse {
	 private long userId;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String mobile;
	    private String role;
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		@Override
		public String toString() {
			return "UserResponse [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
					+ email + ", mobile=" + mobile + ", role=" + role + "]";
		}
	    
	    
}
