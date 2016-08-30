package com.asiainfo.biapp.mcd.jms.beans;

/**
 * 使用行为反馈数据
 * @author zhangyb5
 *
 */
public class FluxActionOutBean extends NbsBaseBean {
	private static final long serialVersionUID = -7952053062193391432L;
	//非必填项
	private String start_time;//上线时间开始，YYYYMMDDHHMISS，上线行为开始进行判定的时间
	private String end_time;//上线时间结束，YYYYMMDDHHMISS，上线行为结束判定的时间
	private Integer site_logic_id;//位置逻辑区编号，待定，位置参数
	private Integer site_logic_type_id;//位置逻辑区类型编号，待定，位置参数
	private Integer site_logic_subtype_id;//位置逻辑区类别编号，待定，位置参数
	private String site_logic_name;//位置逻辑区名称，待定，位置参数
	private String site_id;//位置标识，GPRS：代表 lac-ci,lac和ci都是十进制，通过中划线相连；WLAN代表nasid。本期不提供
	private Integer network_link_type;//网络类型，1：GPRS数据，2：WLAN数据
	private Integer network_access_type;//接入方式，GPRS代表（1：2G；2：3G；3：4G；0：未知）；WLAN代表（1：SIM认证；2：PEAP认证；3：WEB认证；0：其他认证方式）
	private String network_access_point;//接入点，GPRS：代表APN，如CMWAP、CMNET；WLAN代表SSID： CMCC、CMCC-edu等，便于区分使用何种WLAN网络。本期不能超过5个，or关系，多个之间用“，”分隔
	private Integer terminal_type;//终端类型，1：PC；2：手机；3：Pad；0：其他
	private Integer terminal_factory;//终端厂商编号，可以为空，终端参数。本期不能超过5个，or关系，多个之间用“，”分隔。用户选择终端厂商名称，系统通过维表自动获取终端厂商编号
	private Integer terminal_brand;//终端品牌编号，可以为空，终端参数。本期不能超过5个，or关系，多个之间用“，”分隔。用户选择终端厂商名称，系统通过维表自动获取终端厂商编号
	private Integer terminal_mode;//终端型号编号，可以为空，终端参数。本期不能超过5个，or关系，多个之间用“，”分隔。用户选择终端厂商名称，系统通过维表自动获取终端厂商编号
	private String terminal_opersys;//终端操作系统，可以为空，终端参数。本期不能超过5个，or关系，多个之间用“，”分隔
	private Integer terminal_price_range;//终端价位区间，终端参数，1:500以下，2:500~1000，3:1000~2000，4:2000~3000，5: 3000~4000，6: 4000以上。本期不能超过5个，or关系，多个之间用“，”分隔
	private String terminal_screen_size;//终端屏幕大小，终端参数，如：200*300。本期不能超过5个，or关系，多个之间用“，”分隔
	private Float month_accum_flux;//当月累计上网流量，流量使用参数，上线行为开始时客户当月已经累计使用的总流量。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：M
	private Float month_accum_up_flux;//当月累计上网上行流量，流量使用参数，上线行为开始时客户当月已经累计使用的上行流量。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：M
	private Float month_accum_down_flux;//当月累计上网下行流量，流量使用参数，上线行为开始时客户当月已经累计使用的下行流量。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：M
	private Float day_accum_flux;//当日累计上网流量，流量使用参数，上线行为开始时客户当日已经累计使用的总流量。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：M
	private Float day_accum_up_flux;//当日累计上网上行流量，流量使用参数，上线行为开始时客户当日已经累计使用的上行流量。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：M
	private Float day_accum_down_flux;//当日累计上网下行流量，流量使用参数，上线行为开始时客户当日已经累计使用的下行流量。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：M
	private Float month_accum_during_time;//当月累计上网时长，流量使用参数，上线行为开始时客户当月已经累计使用的总时长。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：秒
	private Float day_accum_during_time;//当日累计上网时长，流量使用参数，上线行为开始时客户当日已经累计使用的总时长。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：秒

	private Integer bearing_layer_protocol;//承载层协议，1:TCP，2:UDP
	private String software_name_id;//业务入口编号，软件名称, 从UA中获取。本期单字段不能超过5个，or关系，多个之间用“，”分隔
	private String application_layer_protocol_id;//应用层协议编号，HTTP、FTP、SMTP、QQ、SIP等。本期单字段不能超过5个，or关系，多个之间用“，”分隔
	private String business_id;//业务编码，详细的业务类型,例如，QQ、迅雷、新浪微博。本期单字段不能超过5个，or关系，多个之间用“，”分隔
	private String business_type_id;//业务类别编号。即时通信、微博等。本期单字段不能超过5个，or关系，多个之间用“，”分隔
	private String business_operator_id;//业务提供商。提供业务的运营商名称。本期单字段不能超过5个，or关系，多个之间用“，”分隔
	private String domain_id;//网站编号。客户所浏览网站的名称。本期单字段不能超过5个，or关系，多个之间用“，”分隔
	private String channel_domain_id;//网站频道编号。客户所浏览网站的频道。本期单字段不能超过5个，or关系，多个之间用“，”分隔
	private String resources_operator;//资源归属运营商。根据IP地址来判别；区分运营商（0：移动；1：电信；2：联通；3：铁通；4：教育；6：国内其他；7：国外运营商）。本期单字段不能超过5个，or关系，多个之间用“，”分隔
	private String resources_province;//资源归属省份。提供移动运营商省份。本期单字段不能超过5个，or关系，多个之间用“，”分隔
	private String content_type;//内容分类。本期不能超过3个，or关系，多个之间的格式“分类体系：分类，概率^分类体系：分类，概率^分类体系：分类，概率”
	private Integer market_keyword_id;//营销词编号。客户上网所涉及到的营销相关的关键词语编码，-1:未匹配上。本期单字段不能超过1个
	private String keyword;//关键字。客户上网所涉及到的关键词语。本期不支持

	private String business_start_time;//用户开始使用时间	

	public FluxActionOutBean() {
		super();
	}

	public String getBusiness_start_time() {
		return business_start_time;
	}

	public void setBusiness_start_time(String business_start_time) {
		this.business_start_time = business_start_time;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Integer getSite_logic_id() {
		return site_logic_id;
	}

	public void setSite_logic_id(Integer site_logic_id) {
		this.site_logic_id = site_logic_id;
	}

	public Integer getSite_logic_type_id() {
		return site_logic_type_id;
	}

	public void setSite_logic_type_id(Integer site_logic_type_id) {
		this.site_logic_type_id = site_logic_type_id;
	}

	public Integer getSite_logic_subtype_id() {
		return site_logic_subtype_id;
	}

	public void setSite_logic_subtype_id(Integer site_logic_subtype_id) {
		this.site_logic_subtype_id = site_logic_subtype_id;
	}

	public String getSite_logic_name() {
		return site_logic_name;
	}

	public void setSite_logic_name(String site_logic_name) {
		this.site_logic_name = site_logic_name;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public Integer getNetwork_link_type() {
		return network_link_type;
	}

	public void setNetwork_link_type(Integer network_link_type) {
		this.network_link_type = network_link_type;
	}

	public Integer getNetwork_access_type() {
		return network_access_type;
	}

	public void setNetwork_access_type(Integer network_access_type) {
		this.network_access_type = network_access_type;
	}

	public String getNetwork_access_point() {
		return network_access_point;
	}

	public void setNetwork_access_point(String network_access_point) {
		this.network_access_point = network_access_point;
	}

	public Integer getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(Integer terminal_type) {
		this.terminal_type = terminal_type;
	}

	public Integer getTerminal_factory() {
		return terminal_factory;
	}

	public void setTerminal_factory(Integer terminal_factory) {
		this.terminal_factory = terminal_factory;
	}

	public Integer getTerminal_brand() {
		return terminal_brand;
	}

	public void setTerminal_brand(Integer terminal_brand) {
		this.terminal_brand = terminal_brand;
	}

	public Integer getTerminal_mode() {
		return terminal_mode;
	}

	public void setTerminal_mode(Integer terminal_mode) {
		this.terminal_mode = terminal_mode;
	}

	public String getTerminal_opersys() {
		return terminal_opersys;
	}

	public void setTerminal_opersys(String terminal_opersys) {
		this.terminal_opersys = terminal_opersys;
	}

	public Integer getTerminal_price_range() {
		return terminal_price_range;
	}

	public void setTerminal_price_range(Integer terminal_price_range) {
		this.terminal_price_range = terminal_price_range;
	}

	public String getTerminal_screen_size() {
		return terminal_screen_size;
	}

	public void setTerminal_screen_size(String terminal_screen_size) {
		this.terminal_screen_size = terminal_screen_size;
	}

	public Float getMonth_accum_flux() {
		return month_accum_flux;
	}

	public void setMonth_accum_flux(Float month_accum_flux) {
		this.month_accum_flux = month_accum_flux;
	}

	public Float getMonth_accum_up_flux() {
		return month_accum_up_flux;
	}

	public void setMonth_accum_up_flux(Float month_accum_up_flux) {
		this.month_accum_up_flux = month_accum_up_flux;
	}

	public Float getMonth_accum_down_flux() {
		return month_accum_down_flux;
	}

	public void setMonth_accum_down_flux(Float month_accum_down_flux) {
		this.month_accum_down_flux = month_accum_down_flux;
	}

	public Float getDay_accum_flux() {
		return day_accum_flux;
	}

	public void setDay_accum_flux(Float day_accum_flux) {
		this.day_accum_flux = day_accum_flux;
	}

	public Float getDay_accum_up_flux() {
		return day_accum_up_flux;
	}

	public void setDay_accum_up_flux(Float day_accum_up_flux) {
		this.day_accum_up_flux = day_accum_up_flux;
	}

	public Float getDay_accum_down_flux() {
		return day_accum_down_flux;
	}

	public void setDay_accum_down_flux(Float day_accum_down_flux) {
		this.day_accum_down_flux = day_accum_down_flux;
	}

	public Float getMonth_accum_during_time() {
		return month_accum_during_time;
	}

	public void setMonth_accum_during_time(Float month_accum_during_time) {
		this.month_accum_during_time = month_accum_during_time;
	}

	public Float getDay_accum_during_time() {
		return day_accum_during_time;
	}

	public void setDay_accum_during_time(Float day_accum_during_time) {
		this.day_accum_during_time = day_accum_during_time;
	}

	public Integer getBearing_layer_protocol() {
		return bearing_layer_protocol;
	}

	public void setBearing_layer_protocol(Integer bearing_layer_protocol) {
		this.bearing_layer_protocol = bearing_layer_protocol;
	}

	public String getSoftware_name_id() {
		return software_name_id;
	}

	public void setSoftware_name_id(String software_name_id) {
		this.software_name_id = software_name_id;
	}

	public String getApplication_layer_protocol_id() {
		return application_layer_protocol_id;
	}

	public void setApplication_layer_protocol_id(String application_layer_protocol_id) {
		this.application_layer_protocol_id = application_layer_protocol_id;
	}

	public String getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}

	public String getBusiness_type_id() {
		return business_type_id;
	}

	public void setBusiness_type_id(String business_type_id) {
		this.business_type_id = business_type_id;
	}

	public String getBusiness_operator_id() {
		return business_operator_id;
	}

	public void setBusiness_operator_id(String business_operator_id) {
		this.business_operator_id = business_operator_id;
	}

	public String getDomain_id() {
		return domain_id;
	}

	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
	}

	public String getChannel_domain_id() {
		return channel_domain_id;
	}

	public void setChannel_domain_id(String channel_domain_id) {
		this.channel_domain_id = channel_domain_id;
	}

	public String getResources_operator() {
		return resources_operator;
	}

	public void setResources_operator(String resources_operator) {
		this.resources_operator = resources_operator;
	}

	public String getResources_province() {
		return resources_province;
	}

	public void setResources_province(String resources_province) {
		this.resources_province = resources_province;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public Integer getMarket_keyword_id() {
		return market_keyword_id;
	}

	public void setMarket_keyword_id(Integer market_keyword_id) {
		this.market_keyword_id = market_keyword_id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
