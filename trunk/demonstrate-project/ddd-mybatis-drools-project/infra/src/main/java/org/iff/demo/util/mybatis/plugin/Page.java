package org.iff.demo.util.mybatis.plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.iff.demo.util.BeanHelper;

@SuppressWarnings( { "serial", "unchecked" })
public class Page implements Serializable, Cloneable {

	public static int PAGE_SIZE = 3; //显示数目
	public int pageSize = PAGE_SIZE; // 当页显示数目
	private int totalCount; // 总记录数
	private int currentPage = 1; //当前页

	/** 分页结果 */
	private List<Object> rows = new ArrayList<Object>();

	public Page() {
	}

	public Page(int totalCount) {
		this.totalCount = totalCount;
	}

	public Page(int totalCount, int pageSize) {
		this.totalCount = totalCount;
		this.pageSize = pageSize;
	}

	public Page(int pageSize, int totalCount, int currentPage, List<Object> rows) {
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

	public List<Object> getRows() {
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
