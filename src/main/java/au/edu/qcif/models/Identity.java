package au.edu.qcif.models;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "provider_identity")
public class Identity {

	@DatabaseField(columnName="id", generatedId= true)
	private int id;
	@DatabaseField(columnName="xmlEntry")
	private String xmlEntry;
	@DatabaseField(columnName="lastModified", version = true)
	private Date lastModified;
	
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
}
