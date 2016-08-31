package com.asiainfo.biapp.mcd.tactics.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.vo.custgroup.MtlGroupInfo;
import com.asiainfo.biapp.mcd.tactics.dao.IMcdMtlGroupInfoDao;

/**
 * 客户群相关操作DAO
 * @author AsiaInfo-jie
 *
 */

@Repository("mcdMtlGroupInfoDao")
public class McdMtlGroupInfoDaoImpl extends JdbcDaoBase implements IMcdMtlGroupInfoDao {
    private static Logger log = LogManager.getLogger(McdMtlGroupInfoDaoImpl.class);
    /**
     * 根据客户群ID查找客户群
     * @param custgroupId
     * @return
     */
    @Override
    public MtlGroupInfo getMtlGroupInfo(String customgroupid) {
        List list = null;
        MtlGroupInfo custGroupInfo = null;
        try {
            StringBuffer sbuffer = new StringBuffer();
            sbuffer.append("SELECT * FROM MTL_GROUP_INFO WHERE custom_group_id = ?");
            log.info("查询客户群信息："+sbuffer.toString());
            list = this.getJdbcTemplate().queryForList(sbuffer.toString(),new String[] { customgroupid });
            for (int i=0;i<list.size();i++) {
                Map map = (Map)list.get(i);
                 custGroupInfo = new MtlGroupInfo();
                custGroupInfo.setCustomGroupId((String) map.get("CUSTOM_GROUP_ID"));
                custGroupInfo.setCustomGroupName((String) map.get("CUSTOM_GROUP_NAME"));    
                custGroupInfo.setCreateUserId((String) map.get("CREATE_USER_ID"));
                if(null != map.get("CUSTOM_NUM")){
                    custGroupInfo.setCustomNum(Integer.parseInt(String.valueOf(map.get("CUSTOM_NUM"))));
                }
                if(null != map.get("CUSTOM_STATUS_ID")){
                    custGroupInfo.setCustomStatusId(Integer.parseInt(String.valueOf(map.get("CUSTOM_STATUS_ID"))));
                }
                if(null != map.get("UPDATE_CYCLE")){
                    custGroupInfo.setUpdateCycle(Integer.parseInt(String.valueOf(map.get("UPDATE_CYCLE"))));
                }
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return custGroupInfo;
    }
}
