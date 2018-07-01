package au.edu.qcif.models;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import proai.MetadataFormat;

@DatabaseTable(tableName = "provider_metadataformat")
public class MetadataFormatImpl implements MetadataFormat {

	@DatabaseField(columnName="id", generatedId= true)
	private int id;
	@DatabaseField(columnName="metadataPrefix")
    private String prefix;
	@DatabaseField(columnName="metadataNamespace")
    private String namespaceURI;
	@DatabaseField(columnName="schemaTxt")
    private String schemaLocation;
	@DatabaseField(columnName="lastModified", version = true)
	private Date lastModified;
   

    public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public int getId() {
		return id;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public String getPrefix() {
        return prefix;
    }

    public String getNamespaceURI() {
        return namespaceURI;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

}