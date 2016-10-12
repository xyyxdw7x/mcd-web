package com.asiainfo.biapp.framework.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.crazycake.jdbcTemplateTool.JdbcTemplateTool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DaoSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 对常用的JDBC做进一步封装
 * @author hanjn
 * RowMapper通常是一个静态的内部类
 *
 */
public class JdbcDaoBase extends DaoSupport implements InitializingBean,DisposableBean{

	
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	
	//@Autowired
	//@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	@Qualifier("jdbcTemplateTool")
	private JdbcTemplateTool jdbcTemplateTool;
	
	protected ResultSetExtractor<Long> longResultSetExtractor = new ResultSetExtractor<Long>(){
	    public Long extractData(ResultSet rs) throws SQLException, DataAccessException {  
           	Long result = 0l;
            try {  
                while (rs.next()) {  
                    Object obj  = rs.getObject(1);
                    if (null != obj && StringUtils.isNumeric(String.valueOf(obj))) {
                    	result += rs.getLong(1);
                    }
                }  
            } catch (Throwable e) {  
                throw new ObjectRetrievalFailureException("拼装Map对象出错！", e);  
            }   
            return result;  
        }  
	};
	
	/**
	 * 数据库的类型  DB2 ORACLE MYSQL
	 */
	public static String DB_TYPE=null;
 
	//@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		setDbType(dataSource);
		if (this.namedParameterJdbcTemplate == null) {
			this.namedParameterJdbcTemplate = createParameterJdbcTemplate(dataSource);
			initParameterJdbcTemplateConfig();
		}
	}
	
	/**
	 *
	 * with different configuration, or a custom JdbcTemplate subclass.
	 * @param dataSource the JDBC DataSource to create a NamedParameterJdbcTemplate for
	 * @return the new NamedParameterJdbcTemplate instance
	 * @see #setDataSource
	 */
	protected NamedParameterJdbcTemplate createParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	/**
	 * Set the NamedParameterJdbcTemplate for this DAO explicitly,
	 * as an alternative to specifying a DataSource.
	 */
	public final void setJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		initParameterJdbcTemplateConfig();
	}

	/**
	 * Return the JdbcTemplate for this DAO,
	 * pre-initialized with the DataSource or set explicitly.
	 */
	public final NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
	  return this.namedParameterJdbcTemplate;
	}
	
	/**
	 * Initialize the template-based configuration of this DAO.
	 * Called after a new NamedParameterJdbcTemplate has been set, either directly
	 * or through a DataSource.
	 * <p>This implementation is empty. Subclasses may override this
	 * to configure further objects based on the JdbcTemplate.
	 * @see #getNamedParameterJdbcTemplate()
	 */
	protected void initParameterJdbcTemplateConfig() {
	}
	

	
	
	/**
	 * 设置数据库的类型 
	 * @param dataSource
	 */
	private void setDbType(DataSource dataSource){
		if(JdbcDaoBase.DB_TYPE!=null){
			return ;
		}
		Connection conn=null;
		try {
			conn=dataSource.getConnection();
			String productName=conn.getMetaData().getDatabaseProductName();
			productName=productName.toUpperCase();
			
			if(productName.indexOf("DB2")>=0){
				JdbcDaoBase.DB_TYPE="DB2";
			}else if(productName.indexOf("ORACLE")>=0){
				JdbcDaoBase.DB_TYPE="ORACLE";
			}else if(productName.indexOf("MYSQL")>=0){
				JdbcDaoBase.DB_TYPE="MYSQL";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void initDao() throws Exception {
		super.initDao();
	}


	@Override
	public void destroy() throws Exception {
		
	}

	public JdbcTemplateTool getJdbcTemplateTool() {
		
		return jdbcTemplateTool;
	}

	public void setJdbcTemplateTool(JdbcTemplateTool jdbcTemplateTool) {
		this.jdbcTemplateTool = jdbcTemplateTool;
	}
	
	private JdbcTemplate jdbcTemplate;


	/**
	 * Set the JDBC DataSource to be used by this DAO.
	 */
	public final void setDataSource(DataSource dataSource) {
		if (this.jdbcTemplate == null || dataSource != this.jdbcTemplate.getDataSource()) {
			this.jdbcTemplate = createJdbcTemplate(dataSource);
			initTemplateConfig();
		}
	}

	/**
	 * Create a JdbcTemplate for the given DataSource.
	 * Only invoked if populating the DAO with a DataSource reference!
	 * <p>Can be overridden in subclasses to provide a JdbcTemplate instance
	 * with different configuration, or a custom JdbcTemplate subclass.
	 * @param dataSource the JDBC DataSource to create a JdbcTemplate for
	 * @return the new JdbcTemplate instance
	 * @see #setDataSource
	 */
	protected JdbcTemplate createJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}


	/**
	 * Set the JdbcTemplate for this DAO explicitly,
	 * as an alternative to specifying a DataSource.
	 */
	public final void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		initTemplateConfig();
	}

	/**
	 * Return the JdbcTemplate for this DAO,
	 * pre-initialized with the DataSource or set explicitly.
	 */
	public  JdbcTemplate getJdbcTemplate() {
		this.jdbcTemplate.setDataSource(this.getDataSource());;
	  return this.jdbcTemplate;
	}

	/**
	 * Initialize the template-based configuration of this DAO.
	 * Called after a new JdbcTemplate has been set, either directly
	 * or through a DataSource.
	 * <p>This implementation is empty. Subclasses may override this
	 * to configure further objects based on the JdbcTemplate.
	 * @see #getJdbcTemplate()
	 */
	protected void initTemplateConfig() {
	}

	@Override
	protected void checkDaoConfig() {
		initialize();
		if (this.jdbcTemplate == null) {
			throw new IllegalArgumentException("'dataSource' or 'jdbcTemplate' is required");
		}
	}


	/**
	 * Return the SQLExceptionTranslator of this DAO's JdbcTemplate,
	 * for translating SQLExceptions in custom JDBC access code.
	 * @see org.springframework.jdbc.core.JdbcTemplate#getExceptionTranslator()
	 */
	protected final SQLExceptionTranslator getExceptionTranslator() {
		return getJdbcTemplate().getExceptionTranslator();
	}

	/**
	 * Get a JDBC Connection, either from the current transaction or a new one.
	 * @return the JDBC Connection
	 * @throws CannotGetJdbcConnectionException if the attempt to get a Connection failed
	 * @see org.springframework.jdbc.datasource.DataSourceUtils#getConnection(javax.sql.DataSource)
	 */
	protected final Connection getConnection() throws CannotGetJdbcConnectionException {
		return DataSourceUtils.getConnection(this.getDataSource());
	}

	/**
	 * Close the given JDBC Connection, created via this DAO's DataSource,
	 * if it isn't bound to the thread.
	 * @param con Connection to close
	 * @see org.springframework.jdbc.datasource.DataSourceUtils#releaseConnection
	 */
	protected final void releaseConnection(Connection con) {
		DataSourceUtils.releaseConnection(con, this.getDataSource());
	}

	public DataSource getDataSource() {
		return dataSource;
	}


}
