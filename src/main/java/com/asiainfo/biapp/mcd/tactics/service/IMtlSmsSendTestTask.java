package com.asiainfo.biapp.mcd.tactics.service;

import java.sql.SQLException;

public interface IMtlSmsSendTestTask {
    /**
     * @author weixiang
     * @param CAMPSEG_ID
     * @param channel_id
     * @return
     * @throws Exception
     */
        public String mtlSmsSendTest(String CAMPSEG_ID,String channel_id)throws Exception;
    /**
     * 更改规则的执行状态为测试中  
     * 通过campseg——id
     * @param campseg_id
     * @param status
     */
        
        public void updateCampsegInfoState(String campseg_id,String status)throws Exception;
        
        /**
         * 处理短信回复过来的xml文件 拼接返回的xml
         * @param replyXml
         * @return
         */
        public String getSmsTestReplayContent(String replyXml);
        /**
         * 修改营销用语后更改测试短信发送时间当前时间
         * @param campsegId
         */
        public void updateMtlSmsTestCampsegInfoSendTime(String campsegId)  throws SQLException ;

}
