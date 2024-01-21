package com.dafy.core.helper.page;


public class Page {
	public static final String LIMIT = "limit";
	public static final String OFFSET = "offset";
	
	private int limit;
	private int offset;
	private int totalRecords;
	//private AtomicInteger count = new AtomicInteger(1);
	private int count = 1;
	
	public Page(){
		
	}

	public Page(int limit, int offset) {
		super();
		this.limit = limit;
		this.offset = offset;
	}
	
	public boolean getCount() {
		//return count.decrementAndGet() == 0;
		return (--count) == 0;
	}

	public void setCount(int count) {
		//this.count.set(count);
		this.count = count;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
}
