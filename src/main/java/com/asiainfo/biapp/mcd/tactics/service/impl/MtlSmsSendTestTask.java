package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.common.constants.MpmCONST;
import com.asiainfo.biapp.mcd.quota.dao.IMtlSysCampConfigDao;
import com.asiainfo.biapp.mcd.quota.vo.McdSysDic;
import com.asiainfo.biapp.mcd.tactics.dao.IMpmCampSegInfoDao;
import com.asiainfo.biapp.mcd.tactics.service.IMtlSmsSendTestTask;
import com.asiainfo.biapp.mcd.tactics.vo.McdCampDef;
import com.huawei.msp.mmap.server.MspWebServiceLocator;
import com.huawei.msp.mmap.server.MspWebServicePortType;
import com.huawei.msp.mmap.server.domain.AddTaskRsp;
import com.huawei.msp.mmap.server.domain.BatchAddTaskRsp;
import com.huawei.msp.mmap.server.domain.TaskValue;
/**
 * 短信测试Service
 * @author AsiaInfo-jie
 *
 */
@Service("mtlSmsSendTestTask")
public class MtlSmsSendTestTask  extends JdbcDaoBase  implements IMtlSmsSendTestTask {

    
    
    private static Logger log = LogManager.getLogger();
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    @Resource(name="sysCampConfigDao")
    private IMtlSysCampConfigDao mtlSysCampConfigDao;
    @Resource(name="mpmCampSegInfoDao")
    private IMpmCampSegInfoDao mpmCampSegInfoDao;
    

    
    public MtlSmsSendTestTask() {
        super();
    }

    public String mtlSmsSendTest(String CAMPSEG_ID,String channel_id) throws Exception{
        
        String port = "";
        String userPho = "";
        String message = "";
        String cityid = "";
        
        String reserve21 = "";
        String reserve22 = "";
        String reserve23 = "";
        String reserve24 = "";
        String reserve25 = "";
        String sendWSURL = "";        
        List<McdSysDic> list = mtlSysCampConfigDao.getAll();//获取配置项
        
        for(McdSysDic config:list){
            if("SMS_TEST_SEND_CHANNELID".equals(config.getConfigKey())){
                reserve21 = config.getConfigValue();
            }
            if("SMS_TEST_SEND_CHANNELNAME".equals(config.getConfigKey())){
                reserve22 = config.getConfigValue();
            }
            if("SMS_TEST_SEND_BUSIID".equals(config.getConfigKey())){
                reserve23 = config.getConfigValue();
            }
            if("SMS_TEST_SEND_BUSINAME".equals(config.getConfigKey())){
                reserve24 = config.getConfigValue();
            }
            if("SMS_TEST_SEND_TEMPLATE_ID".equals(config.getConfigKey())){
                reserve25 = config.getConfigValue();
            }
            if("SMS_TEST_SEND_SENDNOPREFIX".equals(config.getConfigKey())){
                port = config.getConfigValue();
            }
            if("SMS_TEST_SEND_WSURL".equals(config.getConfigKey())){
                sendWSURL = config.getConfigValue();
            }
        }
        
        Map<Integer, String> map = new HashMap<Integer, String>();//要发送的人放在map里
        Date beginTime = new Date(System.currentTimeMillis() - 1000);//开始时间
        Calendar schduleTime = Calendar.getInstance();
        schduleTime.add(Calendar.SECOND,-1);
        
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");  
        String schduleTimeStr = sdf.format(schduleTime);*/
        
        //端口号先固定位10086975
        port = port+getPort(CAMPSEG_ID);
        Date endTime = new Date(System.currentTimeMillis() + 1000*60*30-1000);//失效时间  开始后30分钟
        String campsegName = "";
        message = getMessage(CAMPSEG_ID, channel_id);
        String valueRet = getCityandCampsegNameCreUID(CAMPSEG_ID);//city|campsegName|create_userid
        cityid = valueRet.split("[|]")[0];//获取策划人地市
        campsegName = valueRet.split("[|]")[1];//获取策划名称
        map = getUserPhone(cityid);//获取要发送人名单
        
        int totalcount = map.size();
        
        int pageNum = totalcount/150;
        String[] xml ;
        if(totalcount%150==0){//判断发送量  一次最多150条
            xml = new String[pageNum];
        }else{
            xml = new String[pageNum+1];
        }
        List<TaskValue[]> taskList = new ArrayList<TaskValue[]>();
        
        
        String messageInfo = "SMS_CONTENT~尊敬的测试员您好,策划活动\""+campsegName+"\"用语如下:【"+message+"】如有异议，请回复1暂停该活动。30分钟内回复有效！";
        for(int i=0;i<xml.length;i++){//xml数组的长度来分批次推送短信
            /*StringBuffer xmlStr = 
                    new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Header/>");
            xmlStr.append("<soapenv:Body>");
            xmlStr.append("<addTask xmlns=\"http://server.mmap.msp.huawei.com\"><in0>");*/
            TaskValue[] taskValue = new TaskValue[150];//150条的数据
            for(int j=i*150;j<(i+1)*150;j++){
                if((j+1)>totalcount) continue;
                userPho = (String) map.get(j);
                TaskValue task = new TaskValue();
                saveMtkSmsSendTestTaskInfo(port,beginTime,endTime,messageInfo,CAMPSEG_ID);//保存发送的信息
                task.setContent(messageInfo);
                task.setMediaType("1014001");
                task.setReceiverInfo(userPho);
                task.setReserve21(reserve21);
                task.setReserve22(reserve22);
                task.setReserve23(reserve23);
                task.setReserve24(reserve24);
                task.setReserve25(reserve25);
                task.setSchduleTime(schduleTime);
                task.setSendNo(port);
                int index = j-i*150;
                taskValue[index] = task;
                /*xmlStr.append("<ns1:content xmlns:ns1=\"http://domain.server.mmap.msp.huawei.com\">"+messageInfo+"</ns1:content>");
                xmlStr.append("<ns2:mediaType xmlns:ns2=\"http://domain.server.mmap.msp.huawei.com\">1014001</ns2:mediaType>");
                xmlStr.append("<ns3:receiverInfo xmlns:ns3=\"http://domain.server.mmap.msp.huawei.com\">"+userPho+"</ns3:receiverInfo>");
                xmlStr.append("<ns4:reserve21 xmlns:ns4=\"http://domain.server.mmap.msp.huawei.com\">"+reserve21+"</ns4:reserve21>");
                xmlStr.append("<ns5:reserve22 xmlns:ns5=\"http://domain.server.mmap.msp.huawei.com\">"+reserve22+"</ns5:reserve22>");
                xmlStr.append("<ns6:reserve23 xmlns:ns6=\"http://domain.server.mmap.msp.huawei.com\">"+reserve23+"</ns6:reserve23>");
                xmlStr.append("<ns7:reserve24 xmlns:ns7=\"http://domain.server.mmap.msp.huawei.com\">"+reserve24+"</ns7:reserve24>");
                xmlStr.append("<ns8:reserve25 xmlns:ns8=\"http://domain.server.mmap.msp.huawei.com\">"+reserve25+"</ns8:reserve25>");
                xmlStr.append("<ns9:schduleTime xmlns:ns9=\"http://domain.server.mmap.msp.huawei.com\">"+schduleTimeStr+"</ns9:schduleTime>");
                xmlStr.append("<ns10:sendNo xmlns:ns10=\"http://domain.server.mmap.msp.huawei.com\">"+port+"</ns10:sendNo>");*/
                
            }
            taskList.add(taskValue);
            /*xmlStr.append("</in0></addTask>");
            xmlStr.append("</soapenv:Body></soapenv:Envelope>");
            xml[i] = xmlStr.toString();*/
        }
            
            try {
            //  log.info("------------------->"+taskList.get(0)[0].getSendNo()+"zzzz="+taskList.toString()+"zzzzz-->"+taskList.get(0).toString());
                MspWebServiceLocator locator = new MspWebServiceLocator();
                java.net.URL endpoint;
                try {
                    endpoint = new java.net.URL(sendWSURL);
                }
                catch (java.net.MalformedURLException e) {
                    log.error("短信发送地址有误："+sendWSURL);
                    e.printStackTrace();
                    return "短信发送地址解析错误，操作失败。";
                }

                MspWebServicePortType wbPortType = locator.getMspWebServiceHttpPort(endpoint);
                //MspWebServiceHttpBindingStub service = new MspWebServiceHttpBindingStub();
            
                for (int i = 0; i < taskList.size(); i++) {
                    TaskValue[] values ;
                    if(i==(taskList.size()-1)){
                        values = Arrays.copyOf(taskList.get(i), totalcount-150*i);
                    }else{
                        
                        values = Arrays.copyOf(taskList.get(i), 150);
                    }
                    BatchAddTaskRsp resultXml = wbPortType.addBatchTask(values);
                    log.info("此次发送短信数量为"+values.length+"     内容为:"+values[0].getContent()+"      端口号为:"+values[0].getSendNo()
                            +"     发送渠道"+values[0].getMediaType()+"     发送号码:"+values[0].getReceiverInfo()
                            +"     reserve21:"+values[0].getReserve21()+"     reserve22:"+values[0].getReserve22()
                            +"     reserve23:"+values[0].getReserve23()+"     reserve24:"+values[0].getReserve24()
                            +"     reserve25:"+values[0].getReserve25()
                            +"     发送时间:"+values[0].getSchduleTime());
                    String result = resultXml.getResultCode()+"";
                    
                    if(result.equals("1021001")){
                    //  log.info("生成的任务编号------------------>"+resultXml.);
                        return "操作成功";
                    }else{
                        log.error("返回的错误编码："+resultXml.getError());
                        return "操作失败";
                    }
            //  }
            //  BatchAddTaskRsp resultXml = wbPortType.addBatchTask(taskValue);
                //Object[] resultObject = client.invoke("addBatchTask", new Object[] { xmllist});
                /*org.apache.xerces.dom.DocumentImpl result = (org.apache.xerces.dom.DocumentImpl) resultObject[0];
                String retXml = result.getDocumentElement().getFirstChild().getNodeValue();
                String mediaTaskId = "";
                String resultCode = ""; 
                Document dom=DocumentHelper.parseText(retXml); 
                Element root=dom.getRootElement();  
                List<Element> elementList=root.elements("ns1:out");     
                for(int k=0;k<elementList.size();k++){
                    Element element= (Element) elementList.get(k);
                    mediaTaskId = element.element("mediaTaskId")==null?"":element.element("mediaTaskId").getText(); 
                    resultCode  = element.element("resultCode")==null?"":element.element("resultCode").getText(); 
                    
                    if(resultCode.equals("1021002")){
                        log.info("数据传输失败！");
                        return "操作失败";
                    }else{
                        
                    }*/
                }
        
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "操作失败";
            }
        return "操作成功";
        
    }
/**
 * 保存发送信息和发送端口信息
 * @throws SQLException 
 */
private void saveMtkSmsSendTestTaskInfo(String port,
            Date schduleTime, Date endTime, String message, String cAMPSEG_ID) throws SQLException {
        // TODO Auto-generated method stub
    String sql = "";
    try {
        conn = this.getJdbcTemplate().getDataSource().getConnection();
        conn.setAutoCommit(false);
        String qSql = " select * from mcd_sms_test_camp a where campseg_id = ?"; //判断是否存在
        ps = conn.prepareStatement(qSql);
        ps.setString(1, cAMPSEG_ID);
        rs = ps.executeQuery();
        
        if(rs.next()){
            
            sql = "update mcd_sms_test_camp a set a.test_content = ?,a.send_time=? where campseg_id = ?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, message);
            ps.setTimestamp(2, new Timestamp( schduleTime.getTime()));
            ps.setString(3, cAMPSEG_ID);
            ps.execute();
        }else{
            
            sql = "insert into mcd_sms_test_camp values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cAMPSEG_ID);
            ps.setString(2, message);
            ps.setTimestamp(3, new Timestamp( schduleTime.getTime()));
            ps.execute();
        }
        
        qSql = " select * from mcd_sms_test_send_port a where a.send_port=? and a.campseg_id= ?"; //判断是否存在
        ps = conn.prepareStatement(qSql);
        ps.setString(1, port);
        ps.setString(2, cAMPSEG_ID);
        rs = ps.executeQuery();
        
        if(rs.next()){
            
            sql = "update mcd_sms_test_send_port a set a.create_time = ?,a.invalid_time = ? where a.send_port = ? and a.campseg_id=?";
            
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp( schduleTime.getTime()));
            ps.setTimestamp(2, new Timestamp( endTime.getTime()));
            ps.setString(3, port);
            ps.setString(4, cAMPSEG_ID);
            ps.execute();
        }else{
            
            sql = "insert into mcd_sms_test_send_port values(?,?,?,?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, port);
            ps.setString(2, cAMPSEG_ID);
            ps.setTimestamp(3, new Timestamp( schduleTime.getTime()));
            ps.setTimestamp(4, new Timestamp( endTime.getTime()));
            ps.execute();
        }
        
        conn.commit();
        
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }finally{
        if(ps!=null) ps.close();
        if(conn!=null) conn.close();
    }
}
/**
 * 根据策略编码，渠道id获取发送用语
 * @param CAMPSEG_ID
 * @param channel_id
 * @return
 * @throws SQLException 
 */
public String getMessage(String CAMPSEG_ID,String channel_id) throws SQLException{
    String  message = "";
    
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            String sql = "select nvl(exec_content,'') from mcd_camp_channel_list a where a.campseg_id =? and a.channel_id= ? ";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, CAMPSEG_ID);
            ps.setString(2, channel_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                message = rs.getString(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(ps!=null) ps.close();
            if(conn!=null) conn.close();
        }
        
    return message;

}
/**
 * 获取发送端口号
 * 获取的是后四位
 * @param CAMPSEG_ID
 * @return
 * @throws SQLException 
 */

public String getPort(String CAMPSEG_ID) throws SQLException{
    String  port = "";
    
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            
            String sql = " select substr(max(send_port),9) as port_value from mcd_sms_test_send_port a where a.campseg_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, CAMPSEG_ID);
            rs = ps.executeQuery();
            
            
            if(rs.next()){
                port = rs.getString(1);
                if(port==null){
                    sql = " select substr(max(send_port),9) as port_value, count(*) as total_value from mcd_sms_test_send_port a";
                    port = "0001";
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        
                        port = rs.getString(1);
                        if(port!=null){
                            
                            int count = Integer.parseInt(rs.getString(2));
                            if(count>9999){
                                port = (count-9999)+"";
                                
                                if(port.length()==1){
                                    port = "000"+port;
                                }
                                if(port.length()==2){
                                    port = "00"+port;
                                }
                                if(port.length()==3){
                                    port = "0"+port;
                                }
                            }else{
                                port = (Integer.parseInt(port)+1)+"";
                                
                                if(port.length()==1){
                                    port = "000"+port;
                                }
                                if(port.length()==2){
                                    port = "00"+port;
                                }
                                if(port.length()==3){
                                    port = "0"+port;
                                }
                            }
                        }else{
                            port = "0001";
                        }
                    }
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(ps!=null) ps.close();
            if(conn!=null) conn.close();
        }
        
    return port;

}
/**
 * 根据策略编码获取策略人的所属地市，策略名称，策划人id
 * @param CAMPSEG_ID
 * @return
 * @throws SQLException 
 */
public String getCityandCampsegNameCreUID(String CAMPSEG_ID) throws SQLException{
    
    String cityid="";
    String campsegName="";
    String sql = " select city_id ,campseg_name ,create_userid from mcd_camp_def a where a.campseg_id =?  ";
    String createUserId = "";
    try {
        conn = this.getJdbcTemplate().getDataSource().getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, CAMPSEG_ID);
        rs = ps.executeQuery();
        while (rs.next()) {
            cityid = rs.getString(1);
            campsegName = rs.getString(2);
            createUserId = rs.getString(3);
        }
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }finally{
        if(ps!=null) ps.close();
        if(conn!=null) conn.close();
    }
    return cityid+"|"+campsegName+"|"+createUserId;
}
/**
 * 根据cityid获取此地市
 * 的测试人员
 * @param cityid
 * @return
 * @throws SQLException 
 */
public Map getUserPhone(String cityid) throws SQLException{
    Map map = new HashMap();
    
    String sql = " select * from mcd_sms_test_group where city_id=?";
    try {
        conn = this.getJdbcTemplate().getDataSource().getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, cityid);
        rs = ps.executeQuery();
        int count = 0;
        while (rs.next()) {
            map.put(count,rs.getString(2));
            count++;
        }
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }finally{
        if(ps!=null) ps.close();
        if(conn!=null) conn.close();
    }
    return map;
}

/**
 * 通过策略编码
 * 更新策略信息表的状态
 * @throws SQLException 
 */
@Override
public void updateCampsegInfoState(String campseg_id, String status) throws SQLException {
    // TODO Auto-generated method stub
    
    String sql = " update mcd_camp_def a set a.campseg_stat_id = ? where a.campseg_id = ? or a.campseg_id =(select campseg_pid from mcd_camp_def where campseg_id = ?)";
    try {
        conn = this.getJdbcTemplate().getDataSource().getConnection();
        conn.setAutoCommit(false);
        ps = conn.prepareStatement(sql);
        ps.setShort(1, Short.parseShort(status));
        ps.setString(2, campseg_id);
        ps.setString(3, campseg_id);
        ps.executeUpdate();
        
        conn.commit();
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }finally{
        if(ps!=null) ps.close();
        if(conn!=null) conn.close();
    }
    
}
/**
 * 解析回复人发挥的消息，并进行处理
 */
@SuppressWarnings("finally")
@Override
public String getSmsTestReplayContent(String replyXml) {
    // TODO Auto-generated method stub
    String sendNo = "";
    String replyPhoneNo = "";
    String replyContent = "";
    String replyTime = "";
    String Flag = "2";
    
    String campsegid = "";
    StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    xmlStr.append("<result>");
    Document dom;
    try {
        log.info("---------in------------------------------->"+replyXml);
        String repXml = replyXml.replaceAll("\n", "");
        log.info("---------------------------------------->"+repXml);
        dom = DocumentHelper.parseText(repXml);
        log.info("-------------------------out------------------------------------>");
        Element root=dom.getRootElement();  
        List<Element> elementList=root.elements();  
        sendNo = root.element("sendNo")==null?"":root.element("sendNo").getText(); //短信回复端口
        replyPhoneNo  = root.element("replyPhoneNo")==null?"":root.element("replyPhoneNo").getText(); //回复人号码
        replyContent = root.element("replyContent")==null?"":root.element("replyContent").getText(); //回复内容
        replyTime  = root.element("replyTime")==null?"":root.element("replyTime").getText(); 
        log.info("sendNo="+sendNo+"phoneNo="+replyPhoneNo+"replyContent="+replyContent+"replyTime="+replyTime+"zzzz="+root.element("sendNo"));
        Flag = "1";
        xmlStr.append("<flag>"+Flag+"</flag>");
        xmlStr.append("<msg>接收成功</msg>");
        log.info("数据接受成功！");
        log.info("sendNo="+sendNo+"phoneNo="+replyPhoneNo+"replyContent="+replyContent+"replyTime="+replyTime);
        String retValue = getCampidAndisOverByPort(sendNo,replyTime);
        campsegid = retValue.split("[|]")[0];//去端口的表查到策略编码 判断是否超时
        String isOver = retValue.split("[|]")[1];
        saveSmsTestReplayInfo(campsegid,sendNo,replyPhoneNo,replyContent,replyTime);//保存短信回复记录
        if(isOver.equals("2")){//1、没有超时 2、超时
        //超时 给回复人回发短信 ‘短信失效’
            String message = "尊敬的客户您好：此短信已经过期，操作无效！如需继续，请登录平台进行操作。";
            String result = simpleSmsSendTask(replyPhoneNo,sendNo,message);//发送短信
            if(result.equals("操作成功")){
                log.info("给回复人发送短信成功....");
            }else{
                log.info("给回复人发送短信失败....");
            }
        }else{//回复有效的话
            
            if(replyContent.equals("1")){//回复内容为1  进行 判断是否需要暂停，如果需要暂停，则 判断 策略状态是否为测试不通过，如果不是，则修改 策略状态为 测试不通过
                //获取当前策略状态
                try {
                    McdCampDef mtlCampSeginfo = mpmCampSegInfoDao.getCampSegInfo(campsegid);
                    //看状态是否为49  不是设置为49测试不通过
                    String status = mtlCampSeginfo.getStatId()+"";
                    if(!status.equals(MpmCONST.MPM_CAMPSEG_STAT_HDCSBTG)){
                        updateCampsegInfoState(campsegid, MpmCONST.MPM_CAMPSEG_STAT_HDCSBTG);
                    }
                    } catch (Exception e) {
                    // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                //根据模版给策划人发送短信
                    String campsegPho="";
                    String valueRet = getCityandCampsegNameCreUID(campsegid);//city|campsegName|create_userid
                    String cUID = valueRet.split("[|]")[2];
                    String campsegName = valueRet.split("[|]")[1];
                    
                    try {
                        // 暂时注释，因为这块迁移不能实现
//                        campsegPho = PrivilegeServiceUtil.getUserById(cUID).getMobilePhone();//根据权限接口 获取策划人对象  得到 电话号码
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String replyPhoName = getReplyPhoNameByPho(replyPhoneNo);
                    String message = "尊敬的客户您好:您的策略："
                            +campsegName+"  被测试员:"
                            +replyPhoName+",电话:"
                            +replyPhoneNo+" 退回";
                    Timestamp sendTime = new Timestamp(System.currentTimeMillis()-1000); 
                    saveFailLinformInfo(campsegid,campsegPho,message,sendTime);//保存不通过信息
                    String result = simpleSmsSendTask(campsegPho,sendNo,message);//发送短信
                    
                    if(result.equals("操作成功")){
                        log.info("给策划人发送短信成功....");
                    }else{
                        log.info("给策划人发送短信失败....");
                    }
                }
                
            }
    } catch (DocumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        xmlStr.append("<flag>"+Flag+"</flag>");
        xmlStr.append("<msg>接收失败</msg>");
    }finally{
        //log.info("为什么一下就跳到这来了！！！！！！！！！");
        xmlStr.append("</result>");
        return xmlStr.toString();
    }
    

}
/**
 * 测试不通过，保存不通过信息
 * @param campsegid
 * @param campsegPho
 * @param message
 * @param sendTime
 * @throws SQLException 
 */

private void saveFailLinformInfo(String campsegid, String campsegPho,
        String message, Timestamp sendTime) throws SQLException {
    // TODO Auto-generated method stub
    
    String sql ="";
    try {
        conn = this.getJdbcTemplate().getDataSource().getConnection();
        conn.setAutoCommit(false);
        String qSql = " select * from mcd_sms_test_fail_inform a where a.campseg_id =? and a.product_no=?"; //判断是否存在
        ps = conn.prepareStatement(qSql);
        ps.setString(1, campsegid);
        ps.setString(2, campsegPho);
        rs = ps.executeQuery();
        if(rs.next()){
            
            sql = " update mcd_sms_test_fail_inform a set a.inform_content = ?,a.inform_time=? where a.campseg_id =? and a.product_no=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, message);
            ps.setTimestamp(2, sendTime);
            ps.setString(3, campsegid);
            ps.setString(4, campsegPho);
            ps.execute();
        }else{
            
            sql = " insert into mcd_sms_test_fail_inform values(?,?,?,?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, campsegid);
            ps.setString(2, campsegPho);
            ps.setString(3, message);
            ps.setTimestamp(4, sendTime);
            ps.execute();
        }
        
        
        conn.commit();
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }finally{
        if(ps!=null) ps.close();
        if(conn!=null) conn.close();
    }
    
}

/**
 * 根据用户电话获取用户名字
 * @param replyPhoneNo
 * @return
 * @throws SQLException 
 */

private String getReplyPhoNameByPho(String replyPhoneNo) throws SQLException {
    // TODO Auto-generated method stub
    
    String sql = " select USER_NAME from mcd_sms_test_group where PRODUCT_NO=?";
    String userName = "";
    try {
        conn = this.getJdbcTemplate().getDataSource().getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, replyPhoneNo);
        rs = ps.executeQuery();
        while (rs.next()) {
            userName = rs.getString(1);
        }
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }finally{
        if(ps!=null) ps.close();
        if(conn!=null) conn.close();
    }
    return userName;
}
/**
 * 单条短信发送
 * @param campsegPho
 * @param sendNo
 * @param message
 * @return
 */
private String simpleSmsSendTask(String campsegPho, String sendNo, String message) {
    // TODO Auto-generated method stub
    

    String reserve21 = "";
    String reserve22 = "";
    String reserve23 = "";
    String reserve24 = "";
    String reserve25 = "";
    String sendWSURL = "";
    List<McdSysDic> list = mtlSysCampConfigDao.getAll();//获取配置项
    for(McdSysDic config:list){
        if("SMS_TEST_SEND_CHANNELID".equals(config.getConfigKey())){
            reserve21 = config.getConfigValue();
        }
        if("SMS_TEST_SEND_CHANNELNAME".equals(config.getConfigKey())){
            reserve22 = config.getConfigValue();
        }
        if("SMS_TEST_SEND_BUSIID".equals(config.getConfigKey())){
            reserve23 = config.getConfigValue();
        }
        if("SMS_TEST_SEND_BUSINAME".equals(config.getConfigKey())){
            reserve24 = config.getConfigValue();
        }
        if("SMS_TEST_SEND_TEMPLATE_ID".equals(config.getConfigKey())){
            reserve25 = config.getConfigValue();
        }
        if("SMS_TEST_SEND_WSURL".equals(config.getConfigKey())){
            sendWSURL = config.getConfigValue();
        }
    }
    String context = "SMS_CONTENT~"+message;
//  Date datetime = new Date(System.currentTimeMillis() - 1000);//开始时间
    Calendar schduleTime = Calendar.getInstance();
    schduleTime.add(Calendar.SECOND,-1);
    TaskValue task = new TaskValue();
    task.setContent(context);
    task.setMediaType("1014001");
    task.setReceiverInfo(campsegPho);
    task.setReserve21(reserve21);
    task.setReserve22(reserve22);
    task.setReserve23(reserve23);
    task.setReserve24(reserve24);
    task.setReserve25(reserve25);
    task.setSchduleTime(schduleTime);
    task.setSendNo(sendNo);

    
    MspWebServiceLocator locator = new MspWebServiceLocator();
    MspWebServicePortType wbPortType;
    String result =null;
    AddTaskRsp resultXml = null;
    try {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(sendWSURL);
        }
        catch (java.net.MalformedURLException e) {
            log.error("短信发送地址有误："+sendWSURL);
            e.printStackTrace();
            return "短信发送地址解析错误，操作失败。";
        }       
        wbPortType = locator.getMspWebServiceHttpPort(endpoint);
        resultXml = wbPortType.addTask(task);
        log.info("此次发送短信数量为1，内容为:"+task.getContent()+"端口号为:"+task.getSendNo()
                +"发送渠道"+task.getMediaType()+"发送号码:"+task.getReceiverInfo()
                +"reserve21:"+task.getReserve21()+"reserve22:"+task.getReserve22()
                +"reserve23:"+task.getReserve23()+"reserve24:"+task.getReserve24()
                +"reserve25:"+task.getReserve25()
                +"发送时间:"+task.getSchduleTime());
        result = resultXml.getResultCode().toString();
        
    } catch (ServiceException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    if(result.equals("1021001")){
        
        log.info("生成的任务编码："+resultXml.getMediaTaskId());
        
        return "操作成功";
    }else{
        log.error("返回的错误编码："+resultXml.getError());
        return "操作失败";
    }
        /*StringBuffer xmlStr = 
                new StringBuffer("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        xmlStr.append("<soapenv:Body>");
        xmlStr.append("<addTask xmlns=\"http://server.mmap.msp.huawei.com\"><in0>");
        xmlStr.append("<ns1:content xmlns:ns1=\"http://domain.server.mmap.msp.huawei.com\">"+message+"</ns1:content>");
        xmlStr.append("<ns2:mediaType xmlns:ns2=\"http://domain.server.mmap.msp.huawei.com\">1014001</ns2:mediaType>");
        xmlStr.append("<ns3:receiverInfo xmlns:ns3=\"http://domain.server.mmap.msp.huawei.com\">"+campsegPho+"</ns3:receiverInfo>");
        xmlStr.append("<ns4:reserve21 xmlns:ns4=\"http://domain.server.mmap.msp.huawei.com\">"+reserve21+"</ns4:reserve21>");
        xmlStr.append("<ns5:reserve22 xmlns:ns5=\"http://domain.server.mmap.msp.huawei.com\">"+reserve22+"</ns5:reserve22>");
        xmlStr.append("<ns6:reserve23 xmlns:ns6=\"http://domain.server.mmap.msp.huawei.com\">"+reserve23+"</ns6:reserve23>");
        xmlStr.append("<ns7:reserve24 xmlns:ns7=\"http://domain.server.mmap.msp.huawei.com\">"+reserve24+"</ns7:reserve24>");
        xmlStr.append("<ns8:reserve25 xmlns:ns8=\"http://domain.server.mmap.msp.huawei.com\">"+reserve25+"</ns8:reserve25>");
        xmlStr.append("<ns9:schduleTime xmlns:ns9=\"http://domain.server.mmap.msp.huawei.com\">"+schduleTime+"</ns9:schduleTime>");
        xmlStr.append("<ns10:sendNo xmlns:ns10=\"http://domain.server.mmap.msp.huawei.com\">"+sendNo+"</ns10:sendNo>");
        xmlStr.append("</in0></addTask>");
        xmlStr.append("</soapenv:Body></soapenv:Envelope>");
        MtlCallwsUrl url;
        try {
            IMtlCallWsUrlService callwsUrlService = (IMtlCallWsUrlService) SystemServiceLocator.getInstance().getService("callWsUrlService");
            url = callwsUrlService.getCallwsURL("MTL_SMS_SEND_TEST");
            Client client = new Client(new URL(url.getCallwsUrl()));
            log.info("发送报文="+xmlStr);
            Object[] resultObject = client.invoke("addTask", new Object[] { xmlStr.toString() });
            org.apache.xerces.dom.DocumentImpl result = (org.apache.xerces.dom.DocumentImpl) resultObject[0];
            String retXml = result.getDocumentElement().getFirstChild().getNodeValue();
            
            String mediaTaskId = "";
            String resultCode = ""; 
            Document dom=DocumentHelper.parseText(retXml); 
            Element root=dom.getRootElement();  
            List<Element> elementList=root.elements("ns1:out");     
            for(int i=0;i<elementList.size();i++){
                Element element= (Element) elementList.get(i);
                mediaTaskId = element.element("mediaTaskId")==null?"":element.element("mediaTaskId").getText(); 
                resultCode  = element.element("resultCode")==null?"":element.element("resultCode").getText(); 
                
                if(resultCode.equals("1021002")){
                    
                    return "操作失败";
                }else{
                    
                }
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/ 
    
    //return "操作成功";
}
/**
 * 短信回复后，保存回复信息
 * @param campsegid
 * @param sendNo
 * @param replyPhoneNo
 * @param replyContent
 * @param replyTime
 * @throws SQLException 
 */
private void saveSmsTestReplayInfo(String campsegid, String sendNo,
        String replyPhoneNo, String replyContent, String replyTime) throws SQLException {
    // TODO Auto-generated method stub
    
    String sql ="";
    try {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = (Date) f.parseObject(replyTime);
        Timestamp ts = new Timestamp(d.getTime());
        conn = this.getJdbcTemplate().getDataSource().getConnection();
        conn.setAutoCommit(false);
        
        String qSql = " select * from mcd_sms_test_reply a where a.campseg_id=? and a.product_no=?"; //判断是否存在
        ps = conn.prepareStatement(qSql);
        ps.setString(1, campsegid);
        ps.setString(2, replyPhoneNo);
        rs = ps.executeQuery();
        if(rs.next()){
            sql = " update mcd_sms_test_reply a set a.send_port = ?,a.reply_content=?,a.reply_time=? where a.campseg_id=? and a.product_no=?";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, sendNo);
            ps.setString(2, replyContent);
            ps.setString(3, campsegid);
            ps.setString(4, replyPhoneNo);
            ps.setTimestamp(5, ts);
            ps.execute();
        }else{
            
            sql = " insert into mcd_sms_test_reply values(?,?,?,?,?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, campsegid);
            ps.setString(2, replyPhoneNo);
            ps.setString(3, sendNo);
            ps.setString(4, replyContent);
            ps.setTimestamp(5, ts);
            ps.execute();
        }
        
        
        conn.commit();
        
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }finally{
        if(ps!=null) ps.close();
        if(conn!=null) conn.close();
    }
    
    
}
/**
 * 根据发送端口号查策略编码
 * 根据返回时间判断是否超时
 * @param sendNo
 * @param replyTime
 * @return
 * @throws SQLException 
 */

private String getCampidAndisOverByPort(String sendNo,String replyTime) throws SQLException {
    // TODO Auto-generated method stub
    String campsegid ="";
    Timestamp overTime ;
    
    String isOverTime = "2";//2默认超时 1没有超时   
    String sql = " select * from mcd_sms_test_send_port a where a.send_port = ?";
    
    try {
        //SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("---------->replyTime="+replyTime);
        //Date d = new Date(replyTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse( replyTime );
        Timestamp ts = new Timestamp(date.getTime());
        conn = this.getJdbcTemplate().getDataSource().getConnection();
        ps = conn.prepareStatement(sql);
        ps.setString(1, sendNo);
        rs = ps.executeQuery();
        while (rs.next()) {
            campsegid = rs.getString(2);
            overTime = rs.getTimestamp(4);
            if((ts.getTime()-overTime.getTime())<0){
                isOverTime = "1";
            }
        }
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }finally{
        if(ps!=null) ps.close();
        if(conn!=null) conn.close();
    }
    
    return campsegid+"|"+isOverTime;
}
    /**
     * 修改营销用语后更改测试短信发送时间当前时间
     * @param campsegId
     */
    @Override
    public void updateMtlSmsTestCampsegInfoSendTime(String campsegId)  throws SQLException {
        // TODO Auto-generated method stub
        String sql = " update mcd_sms_test_camp a set a.send_time = ? where a.campseg_id = ?";
        final java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            ps.setDate(1,sqlDate);
            ps.setString(2, campsegId);
            ps.executeUpdate();
            
            conn.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(ps!=null) ps.close();
            if(conn!=null) conn.close();
        }
    }

}
