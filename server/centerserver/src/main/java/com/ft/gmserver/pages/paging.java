package com.ft.gmserver.pages;

public class paging {
	private int pageNumber;
	private int pageSize;
	private int totalRow;
	
	public paging(int pageNumber, int pageSize, int totalRow) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalRow = totalRow;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalRow() {
		return totalRow;
	}
	
	public int getTotalPage() {
		return totalRow % pageSize == 0 ? totalRow / pageSize : (totalRow / pageSize) + 1;
	}
}
