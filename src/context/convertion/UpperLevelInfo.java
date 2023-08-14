package context.convertion;

import java.util.ArrayList;
import java.util.List;

public class UpperLevelInfo {
	private String infoType;
	private String deviceType;
	private String deviceName;
	private String deviceId;
	private String domainName;
	private String domainId;
	private String domainURI;
	private List<Triple> infoList;
	private List<TripleHR> infoListHR;
	/**
	 * @return the infoType
	 */
	public String getInfoType() {
		return infoType;
	}
	/**
	 * @param infoType the infoType to set
	 */
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}
	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * @return the infoList
	 */
	public List<Triple> getInfoList() {
		return infoList;
	}
	/**
	 * @param infoList the infoList to set
	 */
	public void setInfoList(List<Triple> infoList) {
		this.infoList = infoList;
	}
	/**
	 * @return the infoListHR
	 */
	public List<TripleHR> getInfoListHR() {
		return infoListHR;
	}
	/**
	 * @param infoListHR the infoListHR to set
	 */
	public void setInfoListHR(List<TripleHR> infoListHR) {
		this.infoListHR = infoListHR;
	}
	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 * @param domainName the domainName to set
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	/**
	 * @return the domainId
	 */
	public String getDomainId() {
		return domainId;
	}
	/**
	 * @param domainId the domainId to set
	 */
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	/**
	 * @return the domainURI
	 */
	public String getDomainURI() {
		return domainURI;
	}
	/**
	 * @param domainURI the domainURI to set
	 */
	public void setDomainURI(String domainURI) {
		this.domainURI = domainURI;
	}

}
