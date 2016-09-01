package com.asiainfo.biapp.mcd.tactics.service;

import java.util.List;
import java.util.Map;

public interface IMtlChannelDefService {
    /**
     * add by jinl 20150717
     * 获取投放渠道
     * @param campsegId
     * @return
     */
    List<Map<String, Object>> getDeliveryChannel(String campsegId);
    /**
     * 获取投放渠道(外呼渠道)
     * @param campsegId
     * @return
     */
    List<Map<String, Object>> getDeliveryChannelCall(String campsegId);

}
