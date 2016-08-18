package com.asiainfo.biapp.mcd.tactics.vo;

public class MtlCampSeginfo {
   
    private Integer pageNum; //当前页
    private Integer isSelectMy; //查询的是否是自己的 0 ：是     1 ：不是
    private Boolean isZJ;//是否是浙江 
    private Integer isMy;//是否是自己创建的  0 ：是     1 ：不是
    private String campDrvIds; //业务类型可多选情况
    private String campsegStatName; //状态名称
    private Short campsegStatId; // 营销活动状态ID
    private String channelId;
    private String channelTypeId;
    private String createUserid; // 活动策划人
    private String keywords;// 关键字
    private String areaId; // 营销活动所属地区
    private String campsegName;
    private String startDate; // 开始时间
    private String endDate; // 结束时间
    private String campsegPid; // 活动编号父ID
    private Short campsegTypeId; // 活动营销类型
    private String planId; // 产品编码
    private boolean isFatherNode;  //是否为策略基础信息；true:是基本信息；false:子规则
    private Integer isFileterDisturb;
    private Integer deptId; // 策划人部门id
    
    
    public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCampsegPid() {
		return campsegPid;
	}
	public void setCampsegPid(String campsegPid) {
		this.campsegPid = campsegPid;
	}
	public Short getCampsegTypeId() {
		return campsegTypeId;
	}
	public void setCampsegTypeId(Short campsegTypeId) {
		this.campsegTypeId = campsegTypeId;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public boolean isFatherNode() {
		return isFatherNode;
	}
	public void setFatherNode(boolean isFatherNode) {
		this.isFatherNode = isFatherNode;
	}
	public Integer getIsFileterDisturb() {
		return isFileterDisturb;
	}
	public void setIsFileterDisturb(Integer isFileterDisturb) {
		this.isFileterDisturb = isFileterDisturb;
	}
	public String getCampsegName() {
		return campsegName;
	}
	public void setCampsegName(String campsegName) {
		this.campsegName = campsegName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Integer getPageNum() {
        return pageNum;
    }
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    public Integer getIsSelectMy() {
        return isSelectMy;
    }
    public void setIsSelectMy(Integer isSelectMy) {
        this.isSelectMy = isSelectMy;
    }
    public Boolean getIsZJ() {
        return isZJ;
    }
    public void setIsZJ(Boolean isZJ) {
        this.isZJ = isZJ;
    }
    public Integer getIsMy() {
        return isMy;
    }
    public void setIsMy(Integer isMy) {
        this.isMy = isMy;
    }
    public String getCampDrvIds() {
        return campDrvIds;
    }
    public void setCampDrvIds(String campDrvIds) {
        this.campDrvIds = campDrvIds;
    }
    public String getCampsegStatName() {
        return campsegStatName;
    }
    public void setCampsegStatName(String campsegStatName) {
        this.campsegStatName = campsegStatName;
    }
    public Short getCampsegStatId() {
        return campsegStatId;
    }
    public void setCampsegStatId(Short campsegStatId) {
        this.campsegStatId = campsegStatId;
    }
    public String getChannelId() {
        return channelId;
    }
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getChannelTypeId() {
        return channelTypeId;
    }
    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }
    public String getCreateUserid() {
        return createUserid;
    }
    public void setCreateUserid(String createUserid) {
        this.createUserid = createUserid;
    }
    public String getKeywords() {
        return keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getAreaId() {
        return areaId;
    }
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    
    
    
}
