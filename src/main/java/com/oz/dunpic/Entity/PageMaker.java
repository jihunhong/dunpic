package com.oz.dunpic.Entity;

import org.springframework.data.domain.Page;

public class PageMaker {
	
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prve;
	private boolean next;
	
	private int displayPageNum = 10;
	
	private Page<Post> postPage;
	private Page<Contents> contentsPage;
	private Page<Test> testPage;
	
	private void calcData(Page<Post> postPage) {
		endPage = (int) (Math.ceil((postPage.getNumber()+1) / (double) displayPageNum ) * displayPageNum);
		startPage = (endPage - displayPageNum)+1;
		if(endPage > postPage.getTotalPages())
		{
			endPage = postPage.getTotalPages();
		}
	}
	
	private void calcData_c(Page<Contents> contentsPage) {
		endPage = (int) (Math.ceil((contentsPage.getNumber()+1) / (double) displayPageNum ) * displayPageNum);
		startPage = (endPage - displayPageNum)+1;
		if(endPage > contentsPage.getTotalPages())
		{
			endPage = contentsPage.getTotalPages();
		}
	}
	
	private void calcData_t(Page<Test> testPage) {
		endPage = (int) (Math.ceil((testPage.getNumber()+1) / (double) displayPageNum ) * displayPageNum);
		startPage = (endPage - displayPageNum)+1;
		if(endPage > testPage.getTotalPages())
		{
			endPage = testPage.getTotalPages();
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrve() {
		return prve;
	}

	public void setPrve(boolean prve) {
		this.prve = prve;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}
	
	public void setPostPage(Page<Post> postPage) {
		this.postPage = postPage;
		
		calcData(postPage);
	}
	
	public void setTestPage(Page<Test> testPage) {
		this.testPage = testPage;
		
		calcData_t(testPage);
	}
	
	
	public void setContentsPage(Page<Contents> contentsPage) {
		this.contentsPage = contentsPage;
		
		calcData_c(contentsPage);
	}
	

}
