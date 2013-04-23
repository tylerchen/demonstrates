package com.foreveross.util.mybatis.plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.foreveross.util.BeanHelper;

@SuppressWarnings( { "serial", "unchecked" })
@XmlRootElement(name = "Page")
public class Page implements Serializable, Cloneable {
	private static int PAGE_SIZE_DEFAULT = 10; //显示数目
	private int pageSize = PAGE_SIZE_DEFAULT; // 当页显示数目
	private int totalCount; // 总记录数
	private int currentPage = 0; //当前页

	/** 分页结果 */
	private List rows = new ArrayList();

	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page(int pageSize, int totalCount, int currentPage, List rows) {
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.rows = rows;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 
	 * @description getTotalPage(获取页码数)
	 * @conditions (总个数存在，当前页显示数目存在)
	 * @return int
	 * @exception
	 * @since 1.0.0
	 */
	public int getTotalPage() {
		if (this.getTotalCount() == 0)
			return 0;
		int totalPage = (this.getTotalCount() + pageSize - 1) / pageSize;
		return totalPage;
	}

	public <T> List<T> getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Page toPage(Class<?> voClass) {
		if (this.rows == null || this.rows.isEmpty()) {
			return this;
		}
		List list = new ArrayList(this.rows.size());
		for (Object o : this.rows) {
			try {
				list.add(BeanHelper.copyProperties(voClass.getConstructor()
						.newInstance(), o));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new Page(pageSize, totalCount, currentPage, list);
	}

	@Override
	public Page clone() {
		return new Page(pageSize, totalCount, currentPage, rows);
	}

	@Override
	public String toString() {
		return "Pages [currentPage=" + currentPage + ", pageSize=" + pageSize
				+ ", rows=" + rows + ", totalCount=" + totalCount + "]";
	}
}
