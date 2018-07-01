package au.edu.qcif.models;

import java.io.PrintWriter;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import proai.SetInfo;
import proai.error.ServerException;

@DatabaseTable(tableName = "provider_sets")
public class Set implements SetInfo{
	
	@DatabaseField(columnName="id", generatedId= true)
	private int id;
	@DatabaseField(columnName="spec")
	private String spec;
	@DatabaseField(columnName="xmlEntry")
	private String xmlEntry;
	@DatabaseField(columnName="lastModified", version = true)
	private Date lastModified;
	
	
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getXmlEntry() {
		return xmlEntry;
	}

	public void setXmlEntry(String xmlEntry) {
		this.xmlEntry = xmlEntry;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public int getId() {
		return id;
	}

	public void write(PrintWriter writer) throws ServerException {
		writer.println(xmlEntry);
	}
	
	public String getSetSpec() {
		return spec;
	}
}
