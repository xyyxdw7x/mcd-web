package com.asiainfo.biapp.mcd.tactics.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.mcd.common.util.McdZjPrivilegeUtil;
import com.asiainfo.biapp.mcd.common.util.MpmConfigure;
import com.asiainfo.biapp.mcd.tactics.service.IMpmUserPrivilegeService;
import com.asiainfo.biapp.mcd.tactics.vo.LkgStaff;
import com.asiainfo.biframe.exception.ServiceException;
import com.asiainfo.biframe.privilege.IUser;

/**
 * 
 * Title: Description:浙江权限管理实现类，从黄金眼系统中直接获取信息 Copyright: (C) Copyright 1993-2014
 * AsiaInfo Holdings, Inc Company: 亚信科技（中国）有限公司
 * 
 * @author lixq10 2015-10-14 下午4:49:29
 * @version 1.0
 */
@Service("mpmUserPrivilegeService")
public class McdZjPrivilegeServiceImpl implements IMpmUserPrivilegeService {

	@Override
	public IUser getUser(String userId) throws ServiceException {
		LkgStaff user = new LkgStaff();
		String paramStr = McdZjPrivilegeUtil.createParam("getUserInfo", userId, null, null, "0");
		DefaultHttpClient client = new DefaultHttpClient();
		String url = MpmConfigure.getInstance().getProperty("MEUN_LIST_ACCESS_URL");
		HttpPost post = new HttpPost(url);
		try {
			StringEntity s = new StringEntity(paramStr);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);

			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String charset = EntityUtils.getContentCharSet(entity);
				BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
				String line = "";
				StringBuffer buffer = new StringBuffer();
				for (line = br.readLine(); line != null; line = br.readLine()) {
					buffer.append(line);
				}
				user = McdZjPrivilegeUtil.parseUserXmlToList(buffer.toString());
			} else {
				System.err.println("ERROR");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return user;
	}

}
