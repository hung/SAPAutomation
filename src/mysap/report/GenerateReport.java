/*******************************************************************************
 * Copyright (C) 2018 kienhung
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package mysap.report;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import mysap.Check;
import org.sikuli.script.ImagePath;


public class GenerateReport
{
	private String customer;
	private String SID;

	public GenerateReport(String customer, String SID)
	{
		this.customer = customer;
		this.SID = SID;
	}

	public void MakeReport()
	{
		Configuration cfg = new Configuration();

		try
		{
			cfg.setDirectoryForTemplateLoading(new File(ImagePath.getBundlePath()));
			Template template = cfg.getTemplate("report.tpl");

			Map<String, Object> data = new HashMap();
			String today = new SimpleDateFormat("ddMMyyyy").format(new Date());

			data.put("customer", this.customer);
			data.put("SID", this.SID);
			data.put("today", today);
			data.put("myData", Check.reportData);


			String path = String.format("%s/%s/%s/%s/", new Object[] {
			ImagePath.getBundlePath(), "reports", this.customer, this.SID });
			String fileName = String.format("%s_%s_%s.html", new Object[] { this.customer, this.SID, today });

			Writer file = new FileWriter(new File(path + fileName));
			template.process(data, file);
			file.flush();
			file.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
