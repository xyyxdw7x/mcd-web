package com.asiainfo.biapp.mcd.common.service.channel;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.channel.DimMtlChanneltypeDao;
import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.exception.MpmException;
import com.asiainfo.biapp.mcd.form.DimMtlChanneltypeForm;
import com.asiainfo.biapp.mcd.util.MpmLocaleUtil;

/**
 * 
 * @author wulg
 *
 */
@Service("dimMtlChanneltypeService")
public class DimMtlChanneltypeServiceImpl implements DimMtlChanneltypeService {
	private static Logger log = LogManager.getLogger();

	@Resource(name = "dimMtlChanneltypeDao")
	private DimMtlChanneltypeDao dimMtlChanneltypeDao;

	public DimMtlChanneltypeServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* （非 Javadoc）
	 * @see com.asiainfo.biapp.mcd.service.IDimChannelUserRelationService#save(com.asiainfo.biapp.mcd.model.DimChannelUserRelation)
	 */
	public void save(DimMtlChanneltype dimMtlChanneltype) throws MpmException {
		// TODO 自动生成方法存根
		try {
			dimMtlChanneltypeDao.save(dimMtlChanneltype);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.bcqdlxdyxx1"));
		}
	}

	/* （非 Javadoc）
	 * @see com.asiainfo.biapp.mcd.service.IDimChannelUserRelationService#searchChannelUserRelation(com.asiainfo.biapp.mcd.form.DimChannelUserRelationForm)
	 */
	public Map searchMtlChanneltype(DimMtlChanneltypeForm searchForm, Integer curPage, Integer pageSize) throws MpmException {
		// TODO 自动生成方法存根
		try {
			return dimMtlChanneltypeDao.searchMtlChanneltype(searchForm, curPage, pageSize);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.cxqdlxdyxx"));
		}
	}

	/* （非 Javadoc）
	 * @see com.asiainfo.biapp.mcd.service.IDimChannelUserRelationService#getChannelUserRelation(int, java.lang.Short)
	 */
	public DimMtlChanneltype getMtlChanneltype(Short Channeltype) throws MpmException {
		// TODO 自动生成方法存根
		try {
			return dimMtlChanneltypeDao.getMtlChanneltype(Channeltype);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.qqdlxdyxxs"));
		}
	}

	/* （非 Javadoc）
	 * @see com.asiainfo.biapp.mcd.service.IDimChannelUserRelationService#delete(com.asiainfo.biapp.mcd.form.DimChannelUserRelationForm)
	 */
	public void delete(DimMtlChanneltypeForm searchForm) throws MpmException {
		// TODO 自动生成方法存根
		try {
			dimMtlChanneltypeDao.delete(searchForm);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.scqdlxdyxx1"));
		}
	}

	/**
	 * @return dimMtlChanneltypeDao
	 */
	public DimMtlChanneltypeDao getDimMtlChanneltypeDao() {
		return dimMtlChanneltypeDao;
	}

	/**
	 * @param dimMtlChanneltypeDao 要设置的 dimMtlChanneltypeDao
	 */
	public void setDimMtlChanneltypeDao(DimMtlChanneltypeDao dimMtlChanneltypeDao) {
		this.dimMtlChanneltypeDao = dimMtlChanneltypeDao;
	}

	public Integer getSendOddTypeByChannelType(Integer ChannelTypeId)
			throws MpmException {
		// TODO Auto-generated method stub

		try {
			return dimMtlChanneltypeDao.getSendOddTypeByChannelType(ChannelTypeId);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(MpmLocaleUtil.getMessage("mcd.java.scqdlxdyxx1"));
		}
	}

	
	
	public List getMtlChannelTypeList() throws Exception {
		List<DimMtlChanneltype> list = dimMtlChanneltypeDao.getAllChannelType(null);
		return list;
	}
	
	public List getAllChannelTypeForSys(String SysId) throws MpmException {
		List list = dimMtlChanneltypeDao.getAllChannelTypeForSys(SysId);
		return list;
	}

	@Override
	public List<DimMtlChanneltype> getChannelMsg(String isDoubleSelect) {
		List<DimMtlChanneltype> list = null;
		try {
			list = dimMtlChanneltypeDao.getChannelMsg(isDoubleSelect);
		} catch (Exception e) {
			log.error("", e);
		}
		return list;
	}
	
	@Override
	public List<DimMtlChanneltype> initChannel(boolean isOnLine,String cityId) {
		List<DimMtlChanneltype> list = null;
		try {
			list = dimMtlChanneltypeDao.initChannel(isOnLine,cityId);
		} catch (Exception e) {
			log.error("", e);
		}
		return list;
	}
}
