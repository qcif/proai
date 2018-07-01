package au.edu.qcif.models;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import proai.Record;

@DatabaseTable(tableName = "provider_records")
public class RecordImpl implements Record{

	

	@DatabaseField(columnName="id", generatedId= true)
	private int id;
	
	@DatabaseField(columnName="recordId")
	private String itemID;
	
	@DatabaseField(columnName="metadataPrefix")
	private String prefix;
	
	@DatabaseField(columnName="source")
	private String sourceInfo;
	
	@DatabaseField(columnName="xmlEntry")
	private String xmlEntry;
	
	@DatabaseField(columnName="lastmodified", version = true)
	private Date lastModified;
	

	public void setId(int id) {
		this.id = id;
	}
	
	public void setItemID(String recordId) {
		this.itemID = recordId;
	}


	public String getXmlEntry() {
		return xmlEntry;
	}


	public void setXmlEntry(String xmlEntry) {
		this.xmlEntry = xmlEntry;
	}


	public int getId() {
		return id;
	}


	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSourceInfo(String sourceInfo) {
		this.sourceInfo = sourceInfo;
	}

	public String getItemID() {
		return this.itemID;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public String getSourceInfo() {
		return this.sourceInfo;
	}

}
