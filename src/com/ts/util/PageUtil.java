package com.ts.util;

public class PageUtil {

	public static String drawNavi(int currentPage, int totalPage) {
		/*<span class='first' which-page=1><<</span>
		<span class='counter-base' which-page=0>0+</span>
		<span class='current' which-page=1></span>
		<span class='counter' which-page=2></span>
		<span class='counter' which-page=3></span>
		<span class='counter' which-page=4></span>
		<span class='counter' which-page=5></span>
		<span class='null' which-page=6></span>
		<span class='null' which-page=7></span>
		<span class='null' which-page=8></span>
		<span class='null' which-page=9></span>
		<span class='null' which-page=10></span>
		<span class='counter-next null' which-page=11>10+</span>
		<span class='last' which-page=5>>></span>
		<span class=current-page'>1</span> / <span class='total-page'>5</span>*/
		System.out.println("pageUtil: " + currentPage + " " + totalPage);
		StringBuilder sb = new StringBuilder();
		int counterBase = currentPage / 11 * 10;
		int currentPageIndex,totalPageIndex;

		if (counterBase != 0) {
			currentPageIndex = currentPage % counterBase;
			totalPageIndex = totalPage % counterBase;
		}
		else {
			currentPageIndex = currentPage;
			totalPageIndex = totalPage;
		}
		
		System.out.println("cpi: " + currentPageIndex);
		System.out.println("tpi: " + totalPageIndex);
		
		sb.append("<span class='first' which-page=1><<</span>");
		sb.append("<span class='counter-base' which-page=" + counterBase + ">" + counterBase + "+</span>");
		
		for (int i = 1; i <= 10; i++) {
			if (i == currentPageIndex)
				sb.append("<span class='current' which-page=" + i + "></span>");
			else if (i <= totalPageIndex)
				sb.append("<span class='counter' which-page=" + i + "></span>");
			else
				sb.append("<span class='null' which-page=" + i + "></span>");
		}
		
		if (currentPage / 11 < totalPage / 11) {
			if (totalPage % 10 != 0)
				sb.append("<span class='counter-next' which-page=" + (counterBase + 11) + ">" + (counterBase + 10) + "+</span>");
			else
				sb.append("<span class='counter-next null' which-page=" + (counterBase + 11) + ">" + (counterBase + 10) + "+</span>");
		}
		else
			sb.append("<span class='counter-next null' which-page=" + (counterBase + 11) + ">" + (counterBase + 10) + "+</span>");
		
		sb.append("<span class='last' which-page=" + totalPage + ">>></span>");
		sb.append("<span class=current-page'>" + currentPage + "</span> / <span class='total-page'>" + totalPage + "</span>");
		
		return sb.toString();
	}
	
}
