package com.asiainfo.biapp.framework.log;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * JDBCAppender使用spring配置好多数据源
 * 
 * @author hjn
 * 
 */
@Component("springJDBCAppender")
public class SpringJDBCAppender extends AppenderSkeleton implements Appender {

	private JdbcTemplate jdbcTemplateMaster;

	/**
	 * 插入的sql片段
	 */
    private String sql;

    /**
     * 缓存个数
     */
    private int bufferSize = 10;

	/**
	 * ArrayList holding the buffer of Logging Events.
	 */
    private ArrayList<LoggingEvent> buffer;


	public SpringJDBCAppender() {
		super();
		buffer = new ArrayList<LoggingEvent>(bufferSize);
	}

	/**
	 * Adds the event to the buffer. When full the buffer is flushed.
	 */
	@Override
	public void append(LoggingEvent event) {
		event.getNDC();
		event.getThreadName();
		// Get a copy of this thread's MDC.
		event.getMDCCopy();
		event.getRenderedMessage();
		event.getThrowableStrRep();
		if (buffer.size() >= bufferSize){
			flushBuffer();
			buffer.add(event);
		}else{
			buffer.add(event);
		}
	}


	/**
	 * Closes the appender, flushing the buffer first then closing the default
	 * connection if it is open.
	 */
	@Override
	public void close() {
		flushBuffer();
	}
	@Override
	public boolean requiresLayout() {
		return false;
	}

	/**
	 * loops through the buffer of LoggingEvents, gets a sql string from
	 * getLogStatement() and sends it to execute(). Errors are sent to the
	 * errorHandler.
	 * 
	 * If a statement fails the LoggingEvent stays in the buffer!
	 */
	public void flushBuffer() {
		// Do the actual logging
		StringBuffer sqlBf=new StringBuffer();
		sqlBf.append(getSql());
		for (Iterator<LoggingEvent> i = buffer.iterator(); i.hasNext();) {
			LoggingEvent logEvent = (LoggingEvent) i.next();
			sqlBf.append(logEvent.getMessage());
			if(i.hasNext()){
				sqlBf.append(",");
			}
		}
		String sqlStr=sqlBf.toString();
		//有信息时才执行sql
		if(!sqlStr.equals(getSql())){
			jdbcTemplateMaster.update(sqlStr);
		}
		buffer = new ArrayList<LoggingEvent>(bufferSize);
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public JdbcTemplate getJdbcTemplateMaster() {
		return jdbcTemplateMaster;
	}

	public void setJdbcTemplateMaster(JdbcTemplate jdbcTemplateMaster) {
		this.jdbcTemplateMaster = jdbcTemplateMaster;
	}
}
