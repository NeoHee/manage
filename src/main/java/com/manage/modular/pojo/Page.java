package com.manage.modular.pojo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.manage.modular.util.CookieUtils;

public class Page<T> {
	//查询的起始位置  limit ？，？ 中的第一个参数
		private int offset;  
		
		//一页显示记录条数  limit ？，？ 中的第二参数
		private int lineSize=10; //默认值 显示3条数据
		
		//记录总数
		private int totalRecords;
		
		//存放结果集
		private List results;
		
		 //请求URI 
		private String url;       
		
		//存放查询参数
		private Map<String, String> searchParam=new HashMap<String, String>();
		
		
		//总页数
		private int totalPage;
		
		//当前页
		private int pageNo;
		
		//生成的分页表单
		private String pageList;
		
		//储存对象
		private List<T> list;
		
		private String pageHtml;
		
		
		
		public String getPageHtml() {
			return pageHtml;
		}

		public void setPageHtml(String pageHtml) {
			this.pageHtml = pageHtml;
		}

		/**
		 * @return 返回当前页数
		 */
		public int getPageNo() {
			return pageNo;
		}
		
		public Page(HttpServletRequest req, HttpServletResponse resp) {
			this(req, resp, -2);
		}
		
		/**
		 * 构造方法
		 * @param request 传递 repage 参数，来记住页码
		 * @param response 用于设置 Cookie，记住页码
		 * @param defaultPageSize 默认分页大小，如果传递 -1 则为不分页，返回所有数据
		 */
		public Page(HttpServletRequest request, HttpServletResponse response, int defaultPageSize){
			// 设置页码参数（传递repage参数，来记住页码）
			String no = request.getParameter("pageNo");
			if("".equals(no)){
				no = null;
			}
//			this.first = 1;
//			this.last = (int)(count / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);
//			if (this.pageNo >= this.last) {
//				this.pageNo = this.last;
//				this.lastPage=true;
//				no = String.valueOf(last);
//			}
			
			if (StringUtils.isNumeric(no)){
				CookieUtils.setCookie(response, "pageNo", no);
				this.setPageNo(Integer.parseInt(no));
			}else if (request.getParameter("repage")!=null){
				no = CookieUtils.getCookie(request, "pageNo");
				if (StringUtils.isNumeric(no)){
					this.setPageNo(Integer.parseInt(no));
				}
			}
			// 设置页面大小参数（传递repage参数，来记住页码大小）
			String size = request.getParameter("pageSize");
			if("".equals(size)){
				size = null;
			}
			if (StringUtils.isNumeric(size)){
				CookieUtils.setCookie(response, "pageSize", size);
				this.setLineSize(Integer.parseInt(size));
			}else if (request.getParameter("repage")!=null){
				no = CookieUtils.getCookie(request, "pageSize");
				if (StringUtils.isNumeric(size)){
					this.setLineSize(Integer.parseInt(size));
				}
			}else if (defaultPageSize != -2){
				this.lineSize = defaultPageSize;
			}
		}
		
		
		/**
		 * @return 返回总页数
		 */
		public int getTotalPage() {
			return (totalRecords+lineSize-1)/lineSize;
		}

		
		
		public List<T> getList() {
			return list;
		}


		public void setList(List<T> list) {
			this.list = list;
		}


		public int getOffset() {
			if(this.pageNo==0) {
				return 0;
			}
			return (this.pageNo-1)*this.lineSize;
		}

		public void setOffset(int offset) {
			this.offset = offset;
		}

		public int getLineSize() {
			return lineSize;
		}

		public void setLineSize(int lineSize) {
			this.lineSize = lineSize;
		}



		public int getTotalRecords() {
			return totalRecords;
		}

		public void setTotalRecords(int totalRecords) {
			this.totalRecords = totalRecords;
		}

		public void setTotal(int total) {
			this.totalRecords = total;
		}

		public List getResults() {
			return results;
		}

		public void setResults(List results) {
			this.results = results;
		}


		public Map<String, String> getSearchParam() {
			return searchParam;
		}

		public void setSearchParam(Map<String, String> searchParam) {
			this.searchParam = searchParam;
		}

		
		
		@Override
		public String toString() {
		
			int pageCount = getTotalPage();  //计算总页数  
		//
		    //拼写要输出到页面的HTML文本   
	        StringBuilder sb = new StringBuilder();   
	           
	           
	        if(getTotalPage() == 0){   
	            sb.append("<strong>没有可显示的项目</strong>\r\n");   
	        }else{   
	            //页号越界处理   
	            if(pageNo > pageCount){      pageNo = pageCount; }   
	            if(pageNo < 1){      pageNo = 1; }   
	               
	            //获取请求中的所有参数   
	        	Set<Map.Entry<String, String>>	set =this.searchParam.entrySet();
	    		Iterator<Map.Entry<String, String>> it = set.iterator(); 
	            String name = null;  //参数名   
	            String value = null; //参数值   
	            //把请求中的所有参数当作隐藏表单域   
	            while (it.hasNext()) {   
	            	Map.Entry<String, String> entry= it.next();
	            	name = entry.getKey();
	                value = entry.getValue();   
	                // 去除页号   
	                if (name.equals("pageNo")) {   
	                    if (null != value && !"".equals(value)) {   
	                        pageNo = Integer.parseInt(value);   
	                    }   
	                    continue;   
	                }   
	                
	            }   
	       
	            
	            // 输出统计数据   
	            sb.append("&nbsp;共<strong>").append(this.totalRecords)   
	                .append("</strong>项")   
	                .append(",<strong>")   
	                .append(pageCount)   
	                .append("</strong>页:&nbsp;\r\n");   
	               
	            //上一页处理   
	            if (pageNo == 1) {   
	                sb.append("<span class=\"disabled\">&laquo;&nbsp;上一页")   
	                    .append("</span>\r\n");   
	            } else {   
	                sb.append("<a href=\"javascript:turnOverPage(")   
	                  .append((pageNo - 1)+","+lineSize)   
	                  .append(")\">&laquo;&nbsp;上一页</a>\r\n");   
	            }   
	               
	            //如果前面页数过多,显示"..."   
	            int start = 1;    
	            if(this.pageNo > 4){   
	                start = this.pageNo - 1;   
	                sb.append("<a href=\"javascript:turnOverPage(1,10)\">1</a>\r\n");   
	                sb.append("<a href=\"javascript:turnOverPage(2,10)\">2</a>\r\n");   
	                sb.append("&hellip;\r\n");   
	            }   
	            //显示当前页附近的页   
	            int end = this.pageNo + 1;   
	            if(end > pageCount){   
	                end = pageCount;   
	            }   
	            for(int i = start; i <= end; i++){   
	                if(pageNo == i){   //当前页号不需要超链接   
	                    sb.append("<span class=\"current\">")   
	                        .append(i)   
	                        .append("</span>\r\n");   
	                }else{   
	                    sb.append("<a href=\"javascript:turnOverPage(")   
	                        .append(i+","+lineSize)   
	                        .append(")\">")   
	                        .append(i)   
	                        .append("</a>\r\n");   
	                }   
	            }   
	            //如果后面页数过多,显示"..."   
	            if(end < pageCount - 2){   
	                sb.append("&hellip;\r\n");   
	            }   
	            if(end < pageCount - 1){   
	                sb.append("<a href=\"javascript:turnOverPage(")   
	                .append(pageCount - 1+","+lineSize)   
	                .append(")\">")   
	                .append(pageCount - 1)   
	                .append("</a>\r\n");   
	            }   
	            if(end < pageCount){   
	                sb.append("<a href=\"javascript:turnOverPage(")   
	                .append(pageCount+","+lineSize)   
	                .append(")\">")   
	                .append(pageCount)   
	                .append("</a>\r\n");    
	            }   
	               
	            //下一页处理   
	            if (pageNo == pageCount) {   
	                sb.append("<span class=\"disabled\">下一页&nbsp;&raquo;")   
	                    .append("</span>\r\n");   
	            } else {   
	                sb.append("<a href=\"javascript:turnOverPage(")   
	                    .append((pageNo + 1)+","+lineSize)   
	                    .append(")\">下一页&nbsp;&raquo;</a>\r\n");   
	            }   
	       
	        }   
			
			return sb.toString();
		
		
		}


		public String getUrl() {
			return url;
		}


		public void setUrl(String url) {
			this.url = url;
		}


		public void setPageNo(int pageNo) {
			this.pageNo = pageNo;
		}
	
}
