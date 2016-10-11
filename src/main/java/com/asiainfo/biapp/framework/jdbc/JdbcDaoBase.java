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
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.orm.ObjectRetrievalFailureException;


import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 对常用的JDBC做进一步封装
 * @author hanjn
 * RowMapper通常是一个静态的内部类
 *
 */
public class JdbcDaoBase extends JdbcDaoSupport implements InitializingBean,DisposableBean{

	
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
	protected void checkDaoConfig() {
		initialize();
		super.checkDaoConfig();
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

}
