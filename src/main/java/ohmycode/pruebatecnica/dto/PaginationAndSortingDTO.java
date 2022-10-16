package ohmycode.pruebatecnica.dto;

public class PaginationAndSortingDTO {
	
	private Integer pagNum;
	
	private Integer pagSize;
	
	private String sortBy;
	
	private String sortDir;
	
	public Integer getPagNum() {
		return pagNum;
	}
	
	public Integer getPagSize() {
		return pagSize;
	}
	
	public String getSortBy() {
		return sortBy;
	}
	
	public String getSortDir() {
		return sortDir;
	}
}
