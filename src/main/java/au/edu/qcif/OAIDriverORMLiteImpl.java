package au.edu.qcif;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import au.edu.qcif.models.Identity;
import au.edu.qcif.models.MetadataFormatImpl;
import au.edu.qcif.models.RecordImpl;
import au.edu.qcif.models.Set;
import proai.MetadataFormat;
import proai.Record;
import proai.SetInfo;
import proai.driver.OAIDriver;
import proai.driver.RemoteIterator;
import proai.driver.impl.RemoteIteratorImpl;
import proai.error.RepositoryException;

public class OAIDriverORMLiteImpl implements OAIDriver {

	private ConnectionSource connectionSource;
	private Dao<Identity, Integer> identityDao;
	private Dao<RecordImpl, Integer> recordDao;
	private Dao<MetadataFormatImpl, Integer> metadataFormatDao;
	private Dao<Set, Integer> setDao;

	public void init(Properties props) throws RepositoryException {
		String dburl = props.getProperty("proai.OaiDriver.db.url");
		String dbuser = props.getProperty("proai.OaiDriver.db.user");
		String dbPassword = props.getProperty("proai.OaiDriver.db.pw");
		String dbDriver = props.getProperty("proai.OaiDriver.db.driver");

		try {
			
			connectionSource = DBConnectionPoolFactory.getConnectionPool(dburl, dbuser, dbPassword, dbDriver);

			TableUtils.createTableIfNotExists(connectionSource, RecordImpl.class);
			recordDao = DaoManager.createDao(connectionSource, RecordImpl.class);
			TableUtils.createTableIfNotExists(connectionSource, Identity.class);
			identityDao = DaoManager.createDao(connectionSource, Identity.class);
			TableUtils.createTableIfNotExists(connectionSource, MetadataFormatImpl.class);
			metadataFormatDao = DaoManager.createDao(connectionSource, MetadataFormatImpl.class);
			TableUtils.createTableIfNotExists(connectionSource, Set.class);
			setDao = DaoManager.createDao(connectionSource, Set.class);

		} catch (SQLException e) {
			throw new RepositoryException("DB connection failed", e);
		} catch (ClassNotFoundException e) {
			throw new RepositoryException("DB Driver not found", e);
		}
	}

	public void write(PrintWriter writer) throws RepositoryException {
		try {

			Identity identity = identityDao.queryForAll().get(0);
			writer.println(identity.getXmlEntry());
		} catch (SQLException e) {
			throw new RepositoryException("Error writing identity info", e);
		}

	}

	public Date getLatestDate() throws RepositoryException {
		try {
			List<Date> lastModifiedDates = new ArrayList<Date>();
			Date date = identityDao.queryBuilder().orderBy("lastModified", false).queryForFirst().getLastModified();
			if (date != null) {
				lastModifiedDates.add(date);
			}
			date = metadataFormatDao.queryBuilder().orderBy("lastModified", false).queryForFirst().getLastModified();
			if (date != null) {
				lastModifiedDates.add(date);
			}
			date = recordDao.queryBuilder().orderBy("lastModified", false).queryForFirst().getLastModified();
			if (date != null) {
				lastModifiedDates.add(date);
			}
			date = setDao.queryBuilder().orderBy("lastModified", false).queryForFirst().getLastModified();
			if (date != null) {
				lastModifiedDates.add(date);
			}

			Collections.sort(lastModifiedDates);
			
			return lastModifiedDates.get(lastModifiedDates.size() -1);
		} catch (SQLException e) {
			throw new RepositoryException("Error getting latest date", e);
		}
	}

	public RemoteIterator<? extends MetadataFormat> listMetadataFormats() throws RepositoryException {
		try {	
			@SuppressWarnings("unchecked")
			List<MetadataFormat> metaFormatList = (List)metadataFormatDao.queryForAll();
			return new RemoteIteratorImpl<MetadataFormat>(metaFormatList.iterator());
		} catch (SQLException e) {
			throw new RepositoryException("Error listing metadata formats",e);
		}
		
	}

	public RemoteIterator<? extends SetInfo> listSetInfo() throws RepositoryException {
		try {	
			@SuppressWarnings("unchecked")
			List<SetInfo> setList = (List)setDao.queryForAll();
			return new RemoteIteratorImpl<SetInfo>(setList.iterator());
		} catch (SQLException e) {
			throw new RepositoryException("Error listing set info",e);
		}
	}

	
	public RemoteIterator<? extends Record> listRecords(Date from, Date until, String mdPrefix)
			throws RepositoryException {
		try {	
			Where<RecordImpl, Integer> listRecordsWhere = recordDao.queryBuilder().where().eq("metadataPrefix",mdPrefix);
			if(from != null) {
				listRecordsWhere = listRecordsWhere.and().gt("lastModified", from);
			}
			if(until != null) {
				listRecordsWhere = listRecordsWhere.and().lt("lastModified", until);
			}
			@SuppressWarnings("unchecked")
			List<Record> records = (List)listRecordsWhere.query();
			return new RemoteIteratorImpl<Record>(records.iterator());
		} catch (SQLException e) {
			throw new RepositoryException("Error writing record XML",e);
		}
	}

	public void writeRecordXML(String itemID, String mdPrefix, String sourceInfo, PrintWriter writer)
			throws RepositoryException {
		try {	
			RecordImpl record = recordDao.queryBuilder().where().eq("recordId", itemID).and().eq("metadataPrefix",mdPrefix).and().eq("source",sourceInfo).queryForFirst();
			writer.println(record.getXmlEntry());
		} catch (SQLException e) {
			throw new RepositoryException("Error writing record XML",e);
		}
	}

	public void close() throws RepositoryException {
		try {
			connectionSource.close();
		} catch (IOException e) {
			throw new RepositoryException("Error closing DB connection",e);
		}
	}

}
