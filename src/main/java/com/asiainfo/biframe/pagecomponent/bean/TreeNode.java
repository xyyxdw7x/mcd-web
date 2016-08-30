package com.asiainfo.biframe.pagecomponent.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.asiainfo.biframe.utils.string.GetPy;

/**
 * 树节点，参考EXT2.2的TreeNode
 * 
 * @author huangchao2
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TreeNode implements Comparable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 309047327307446894L;
	// 必填信息
	private String id;// 节点id
	private String pid;// 父id
	private String text = "";// 节点title
	// 必填信息end

	// 选填信息
	private boolean leaf = true;// 是否叶子节点
	private String qtip;// 节点tip信息
	private String cls;// 节点css
	private String icon;// 节点图标
	private List<TreeNode> children = new ArrayList<TreeNode>();// 子节点
	private boolean checked;// 是否选中
	private boolean disabled;// 是否失效
	private boolean draggable = true;// 是否支持拖拽(必须TreePanel设置enableDD为true，节点才有可能支持拖拽)
	private boolean expanded = false;// 是否初始展开
	private String href = "";// 节点对应的url
	private String hrefTarget;// 节点url对应的target frame
	private String param1;// 备用字段,页面js通过node.attributes.param1获取
	private String param2;// 备用字段,页面js通过node.attributes.param2获取
	private String param3;// 备用字段,页面js通过node.attributes.param3获取
	// 选填信息end

	private boolean match = false;// 是否通过了过滤

	public boolean isMatch() {
		return match;
	}

	public void setMatch(boolean match) {
		this.match = match;
		if (match) {
			// System.out.println(this.text+" matches...");
		}
	}

	public TreeNode() {

	}

	/**
	 * 构造函数，是否叶子节点由系统自动判断 如果节点有下级节点，则是目录；否则是叶子
	 * 
	 * @param id
	 * @param pid
	 * @param text
	 */
	public TreeNode(String id, String pid, String text) {
		this.id = id;
		this.pid = pid;
		this.text = text;
	}

	public TreeNode(String id, String pid, String text, String href) {
		this.id = id;
		this.pid = pid;
		this.text = text;
		this.href = href;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * 支持按pid和name排序
	 */
	public int compareTo(Object o) {
		if (o instanceof TreeNode) {
			TreeNode tn = (TreeNode) o;
			String pid = tn.getPid();
			String text = tn.getText();
			if (this.pid.compareTo(pid) > 0) {
				return 1;
			} else if (this.pid.compareTo(pid) == 0) {
				if (this.text.compareTo(text) > 0) {
					return 1;
				} else if (this.text.compareTo(text) < 0) {
					return -1;
				} else {
					return 0;
				}
			} else {
				return -1;
			}
		} else {
			throw new ClassCastException("Can't compare");
		}
	}

	/**
	 * 是否满足过滤条件
	 * 
	 * @param filterStr：过滤条件
	 * @return
	 */
	public boolean match(String filterStr) {
		if (filterStr == null || filterStr.trim().length() == 0) {
			return true;
		}
		return (this.getText().indexOf(filterStr) >= 0) || (GetPy.getGBKpy(this.getText()).indexOf(filterStr) >= 0);
	}

	@Override
	public String toString() {
		return "id=[" + this.id + "],pid=[" + this.pid + "],text=[" + this.text + "],href=[" + this.href + "]"
				+ ",target=[" + hrefTarget + "],expanded=[" + expanded + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	@Override
	public boolean equals(Object o) {
		if ((o == null) || !(o instanceof TreeNode)) {
			return false;
		}
		TreeNode node = (TreeNode) o;
		if (this.getId().equals(node.getId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hb = new HashCodeBuilder();
		hb.append(this.getId());
		hb.append(this.getPid());
		return hb.toHashCode();
	}
}
