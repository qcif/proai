package au.edu.qcif.ws.resources;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.ws.rs.NotFoundException;

import org.apache.commons.digester.plugins.PluginException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import au.edu.qcif.DBConnectionPoolFactory;
import au.edu.qcif.models.RecordImpl;



public class RecordResource extends ServerResource {
	
	private Dao<RecordImpl, Integer> recordDao;
	
	public RecordResource() throws SQLException {
		recordDao = DaoManager.createDao(DBConnectionPoolFactory.getConnectionPool(), RecordImpl.class);
	}
	
	@ApiOperation(value = "updates the record's metadata", tags = "recordmeta")
	@ApiResponses({ @ApiResponse(code = 200, message = "The record's metadata is updated"),
			@ApiResponse(code = 500, message = "General Error", response = Exception.class) })
	@Post("json")
	public String createOrUpdateRecord(JsonRepresentation data) throws SQLException {
		JSONObject recordJson = data.getJsonObject();
		
		RecordImpl record = new RecordImpl();
		record.setItemID(recordJson.getString("recordId"));
		record.setPrefix(recordJson.getString("metadataPrefix"));
		record.setSourceInfo(recordJson.getString("sourceInfo"));
		record.setXmlEntry(recordJson.getString("xml"));
		
		RecordImpl dbRecord =recordDao.queryBuilder().where().eq("recordId", record.getItemID()).and().eq("metadataPrefix", record.getPrefix()).and().eq("source", record.getSourceInfo()).queryForFirst();
		if(dbRecord != null) {
			dbRecord.setXmlEntry(record.getXmlEntry());
			recordDao.update(dbRecord);
		} else {
			recordDao.create(dbRecord);
		}
		//		CreateOrUpdateStatus status = recordDao.createOrUpdate(record);
		
		return "";
	}
	
	
	@ApiOperation(value = "updates the record's metadata", tags = "recordmeta")
	@ApiResponses({ @ApiResponse(code = 200, message = "The record's metadata is updated"),
			@ApiResponse(code = 500, message = "General Error", response = Exception.class) })
	@Delete("json")
	public String deleteRecord(JsonRepresentation data) throws SQLException {
		JSONObject recordJson = data.getJsonObject();
		

		String recordID = recordJson.getString("recordId");
		String metadataPrefix = recordJson.getString("metadataPrefix");
		String source = recordJson.getString("sourceInfo");

		
		RecordImpl dbRecord =recordDao.queryBuilder().where().eq("recordId", recordID).and().eq("metadataPrefix",metadataPrefix).and().eq("source", source).queryForFirst();
		if(dbRecord != null) {
			recordDao.delete(dbRecord);
		} else {
			throw new NotFoundException("Record not found");
		}
		
		return "";
	}

}
