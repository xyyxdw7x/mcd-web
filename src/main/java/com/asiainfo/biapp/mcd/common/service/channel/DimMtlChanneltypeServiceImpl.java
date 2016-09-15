package com.asiainfo.biapp.mcd.common.service.channel;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.dao.channel.DimMtlChanneltypeDao;
import com.asiainfo.biapp.mcd.common.vo.channel.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.exception.MpmException;

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
	}

	/* （非 Javadoc）
	 * @see com.asiainfo.biapp.mcd.service.IDimChannelUserRelationService#save(com.asiainfo.biapp.mcd.model.DimChannelUserRelation)
	 */
	public void save(DimMtlChanneltype dimMtlChanneltype) throws MpmException {
		try {
			dimMtlChanneltypeDao.save(dimMtlChanneltype);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(e.getMessage());
		}
	}



	/* （非 Javadoc）
	 * @see com.asiainfo.biapp.mcd.service.IDimChannelUserRelationService#getChannelUserRelation(int, java.lang.Short)
	 */
	public DimMtlChanneltype getMtlChanneltype(Short Channeltype) throws MpmException {
		try {
			return dimMtlChanneltypeDao.getMtlChanneltype(Channeltype);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(e.getMessage());
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

		try {
			return dimMtlChanneltypeDao.getSendOddTypeByChannelType(ChannelTypeId);
		} catch (Exception e) {
			log.error("", e);
			throw new MpmException(e.getMessage());
		}
	}

	
	
	public List<DimMtlChanneltype> getMtlChannelTypeList() throws Exception {
		List<DimMtlChanneltype> list = dimMtlChanneltypeDao.getAllChannelType(null);
		return list;
	}
	
	public List<DimMtlChanneltype> getAllChannelTypeForSys(String SysId) throws MpmException {
		List<DimMtlChanneltype> list = dimMtlChanneltypeDao.getAllChannelTypeForSys(SysId);
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
