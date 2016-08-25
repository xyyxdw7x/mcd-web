package  com.asiainfo.biapp.mcd.common.service;

import java.util.List;

import com.asiainfo.biapp.mcd.tactics.exception.MpmException;
import com.asiainfo.biapp.mcd.tactics.vo.DimMtlChanneltype;
import com.asiainfo.biapp.mcd.tactics.vo.DimPlanSrvType;
import com.asiainfo.biapp.mcd.tactics.vo.DimPlanType;

public interface IMpmCommonService {
	/**
	 *  新建策略页面---初始化政策类别
	 * @return
	 */
	public  List<DimPlanType> initDimPlanType();
	/**
	 * 新建策略页面---初始化政策粒度
	 * @return
	 * @throws MpmException
	 */
	public List<DimPlanSrvType> getGradeList() throws MpmException;
	/**
	 * 新建策略页面---政策适用渠道
	 * @return
	 * @throws MpmException
	 */
	public List<DimMtlChanneltype> getMtlChanneltypeByCondition(String isDoubleSelect) throws MpmException;
}
