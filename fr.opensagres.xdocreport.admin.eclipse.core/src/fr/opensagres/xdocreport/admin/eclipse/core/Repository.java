package fr.opensagres.xdocreport.admin.eclipse.core;

import fr.opensagres.xdocreport.remoting.resources.services.ServiceType;

public class Repository {

	public static final String SERVICE_TYPE_PROPERTY = "serviceType";
	public static final String BASE_ADDRESS_PROPERTY = "baseAddress";
	public static final String USERNAME_PROPERTY = "username";
	public static final String PASSWORD_PROPERTY = "password";
	public static final String CONNECTION_TIMEOUT_PROPERTY = "connectionTimeout";
	public static final String ALLOW_CHUNKING_PROPERTY = "allowChunking";

	private Long id;
	private ServiceType serviceType;
	private String baseAddress;
	private String username;
	private String password;
	private Long connectionTimeout;
	private Boolean allowChunking;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

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
