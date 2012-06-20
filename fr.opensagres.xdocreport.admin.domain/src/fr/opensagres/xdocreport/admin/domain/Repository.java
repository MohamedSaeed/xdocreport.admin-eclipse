package fr.opensagres.xdocreport.admin.domain;


public class Repository {

	private String baseAddress;
	private String username;
	private String password;
	private Long connectionTimeout;
	private Boolean allowChunking;

	public String getBaseAddress() {
		return baseAddress;
	}

	public void setBaseAddress(String baseAddress) {
		this.baseAddress = baseAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Boolean getAllowChunking() {
		return allowChunking;
	}

	public void setAllowChunking(Boolean allowChunking) {
		this.allowChunking = allowChunking;
	}

}
