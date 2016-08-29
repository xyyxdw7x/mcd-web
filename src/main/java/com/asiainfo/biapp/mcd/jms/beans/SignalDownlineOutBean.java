package com.asiainfo.biapp.mcd.jms.beans;

/**
 * 下线行为反馈数据
 * @author zhangyb5
 *
 */
public class SignalDownlineOutBean extends NbsBaseBean {
	private static final long serialVersionUID = -2728141950616176479L;
	//非必填项
	private String online_time_start;//上线时间开始，YYYYMMDDHHMISS，上线行为开始进行判定的时间
	private String online_time_end;//上线时间结束，YYYYMMDDHHMISS，上线行为结束判定的时间
	private String downline_time_start;//下线时间开始，YYYYMMDDHHMISS，下线行为开始进行判定的时间
	private String downline_time_end;//下线时间结束，YYYYMMDDHHMISS，下线行为结束判定的时间
	private String downline_cause;//下线原因，WLAN报文中的下线代码

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
	private Float single_all_flux;//本次上网总流量。流量使用参数，本次上网行为完成时累计使用的总流量。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：M
	private Float single_up_flux;//本次上网上行流量。流量使用参数，本次上网行为完成时累计使用的上行流量。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：M
	private Float single_down_flux;//本次上网下行流量。流量使用参数，本次上网行为完成时累计使用的下行流量。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：M
	private Float month_accum_during_time;//当月累计上网时长，流量使用参数，上线行为开始时客户当月已经累计使用的总时长。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：秒
	private Float day_accum_during_time;//当日累计上网时长，流量使用参数，上线行为开始时客户当日已经累计使用的总时长。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：秒
	private Float single_all_during_time;//本次上网总时长。流量使用参数，本次上网行为完成时累计使用的总时长。本期只提供关系“大于等于”的关系，即填入50，则判定大于等于50的情况。单位：秒
	private String online_time;//用户上线用时间
	private String downline_time;//用户下线时间

	public SignalDownlineOutBean() {
		super();
	}

	public String getOnline_time() {
		return online_time;
	}

	public void setOnline_time(String online_time) {
		this.online_time = online_time;
	}

	public String getDownline_time() {
		return downline_time;
	}

	public void setDownline_time(String downline_time) {
		this.downline_time = downline_time;
	}

	public String getOnline_time_start() {
		return online_time_start;
	}

	public void setOnline_time_start(String online_time_start) {
		this.online_time_start = online_time_start;
	}

	public String getOnline_time_end() {
		return online_time_end;
	}

	public void setOnline_time_end(String online_time_end) {
		this.online_time_end = online_time_end;
	}

	public String getDownline_time_start() {
		return downline_time_start;
	}

	public void setDownline_time_start(String downline_time_start) {
		this.downline_time_start = downline_time_start;
	}

	public String getDownline_time_end() {
		return downline_time_end;
	}

	public void setDownline_time_end(String downline_time_end) {
		this.downline_time_end = downline_time_end;
	}

	public String getDownline_cause() {
		return downline_cause;
	}

	public void setDownline_cause(String downline_cause) {
		this.downline_cause = downline_cause;
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

	public Float getSingle_all_flux() {
		return single_all_flux;
	}

	public void setSingle_all_flux(Float single_all_flux) {
		this.single_all_flux = single_all_flux;
	}

	public Float getSingle_up_flux() {
		return single_up_flux;
	}

	public void setSingle_up_flux(Float single_up_flux) {
		this.single_up_flux = single_up_flux;
	}

	public Float getSingle_down_flux() {
		return single_down_flux;
	}

	public void setSingle_down_flux(Float single_down_flux) {
		this.single_down_flux = single_down_flux;
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

	public Float getSingle_all_during_time() {
		return single_all_during_time;
	}

	public void setSingle_all_during_time(Float single_all_during_time) {
		this.single_all_during_time = single_all_during_time;
	}

}
