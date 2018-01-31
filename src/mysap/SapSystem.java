/*******************************************************************************
 * Copyright (C) 2018 kienhung
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package mysap;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SapSystem
{
	String customerName;
	String checkerName;
	String reportFile;
	
	String os;
	Boolean sapShortCut;
	Boolean autoLogin;
	Boolean autoLogout;
	Boolean autoVPN;
	Boolean autoRDP;
	Boolean onlyCheck;
	Boolean isLoaded = false;
	int delay = 0;
	int timeout = 10;

	List<Map<String, Object>> systems = new ArrayList();

	static final Logger logger = LogManager.getLogger(SapSystem.class.getName());
	ArrayList SID = new ArrayList();


	public void readConfig(String settings, String secKey)
			throws Exception
	{
		this.systems.clear();
		this.SID.clear();
		String myTest = readResource(settings);
		JsonValue value = Json.parse(myTest);

		JsonArray items = value.asObject().get("Systems").asArray();

		this.customerName = value.asObject().getString("CustomerName", "");
		this.checkerName = value.asObject().getString("Checker", "");
		this.reportFile = value.asObject().getString("ReportFile", "");
		this.os = value.asObject().getString("OS", "Windows10");
		this.sapShortCut = Boolean.valueOf(value.asObject().getBoolean("SapShortCut", false));
		this.autoLogin = Boolean.valueOf(value.asObject().getBoolean("AutoLogin", false));
		this.autoLogout = Boolean.valueOf(value.asObject().getBoolean("AutoLogout", false));
		this.autoVPN = Boolean.valueOf(value.asObject().getBoolean("AutoVPN", false));
		this.autoRDP = Boolean.valueOf(value.asObject().getBoolean("AutoRDP", false));
		this.onlyCheck = Boolean.valueOf(value.asObject().getBoolean("OnlyCheck", false));
		this.delay = value.asObject().getInt("Delay", 1);
		this.timeout = value.asObject().getInt("Timeout", 10);


		for (JsonValue item : items)
		{
			String[] tcodeArray = new String[item.asObject().get("Tcode").asArray().size()];
			int i = 0;
			String decryptPass = AES.decrypt(item.asObject().getString("Password", ""), secKey);
			Map<String, Object> sys = new HashMap();
			sys.put("SID", item.asObject().getString("SID", ""));
			sys.put("ConnectionString", item.asObject().getString("ConnectionString", ""));
			sys.put("Client", item.asObject().getString("Client", ""));
			sys.put("Username", item.asObject().getString("Username", ""));
			sys.put("Password", decryptPass);
			sys.put("Desc", item.asObject().getString("Desc", ""));
			sys.put("Lang", item.asObject().getString("Lang", ""));

			for (JsonValue tcode : item.asObject().get("Tcode").asArray())
			{
				tcodeArray[i] = tcode.asString().toLowerCase();
				i++;
			}
			sys.put("Tcode", tcodeArray);

			sys.put("Fromday", item.asObject().getString("Fromday", ""));
			sys.put("Today", item.asObject().getString("Today", ""));

			this.systems.add(sys);
			this.SID.add(item.asObject().getString("SID", ""));
		}
		this.isLoaded = true;
	}


	private String readResource(String name)
			throws IOException
	{
		InputStream inputStream = new FileInputStream(new File(name));
		if (inputStream == null) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder();
		char[] buffer = new char['Ѐ'];
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			int read;
			while ((read = reader.read(buffer)) != -1) {
				stringBuilder.append(buffer, 0, read);
			}
		} finally {
			inputStream.close();
		}
		return stringBuilder.toString();
	}


	public String getCustomerName()
	{
		return this.customerName;
	}

	public String getcheckerName() {
		return this.checkerName;
	}

	public String getreportFile() {
		return this.reportFile;
	}

	public int getDelay() {
		return this.delay;
	}

	public int getTimeout() {
		return this.timeout;
	}

	public String getOS() {
		return this.os;
	}

	public Boolean isSapShortCut() {
		if (this.sapShortCut.booleanValue()) {
			return true;
		}
		return false;
	}

	public Boolean isAutoLogin() {
		if (this.autoLogin.booleanValue()) {
			return true;
		}
		return false;
	}

	public Boolean isAutoLogout() {
		if (this.autoLogout.booleanValue()) {
			return true;
		}
		return false;
	}

	public Boolean isAutoVPN() {
		if (this.autoVPN.booleanValue()) {
			return true;
		}
		return false;
	}

	public Boolean isAutoRDP() {
		if (this.autoRDP.booleanValue()) {
			return true;
		}
		return false;
	}

	public Boolean isOnlyCheck() {
		if (this.onlyCheck.booleanValue()) {
			return true;
		}
		return false;
	}

	public Boolean isLoadedSettings() {
		if (this.isLoaded.booleanValue()) {
			return true;
		}
		return false;
	}

	public List<Map<String, Object>> getSystems()
	{
		return this.systems;
	}

	public ArrayList<String> getSID()
	{
		return this.SID;
	}

	public int countSystems()
	{
		return this.systems.size();
	}

	public Map getSystemBySID(String SID)
	{
		String sid = "";

		Map<String, Object> temp = null;

		for (Map<String, Object> sys : this.systems)
		{
			sid = (String)sys.get("SID");

			if (SID.equals(sid))
			{
				temp = sys;
				break;
			}
		}
		return temp;
	}
}
