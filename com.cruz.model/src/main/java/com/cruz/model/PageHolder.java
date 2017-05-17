package com.cruz.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PageHolder<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	int currentPage;
	int pageSize;
	int totalCount;
	List<T> list;
	String pageUrl;

	public PageHolder() {
		super();
	}

	public PageHolder(int currentPage, int pageSize, int totalCount, List<T> list, String pageUrl) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.list = list;
		this.pageUrl = pageUrl;
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

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public int getNextPage() {
		return (((getCurrentPage() * getPageSize()) + 1) > getTotalCount()) ? getTotalCount()
				: ((getCurrentPage() * getPageSize()) + 1);
	}

	public int getPrevPage() {
		return (((getCurrentPage() * getPageSize()) - 1) < 0) ? 0 : ((getCurrentPage() * getPageSize()) - 1);
	}
}
