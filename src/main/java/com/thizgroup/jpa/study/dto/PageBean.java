package com.thizgroup.jpa.study.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.springframework.util.Assert;

@Data//使用lombok生成getter、setter
public class PageBean implements Serializable {

  @Getter
  private long totalCount;//总记录数

  @Getter
  private int totalPages;//总页数

  @Getter
  private int pageNumber;//第几页

  @Getter
  private int pageSize;//每页条数

  public PageBean(int pageNumber, int pageSize, long totalCount) {

    pageNumber = pageNumber <0 ? 0 :pageNumber;//jpa中页码从0开始
    pageSize = pageSize <=0 ? 15 : pageSize;//默认取15条记录

    //计算总页数
    int totalPages = (int)((totalCount+pageSize-1)/pageSize);
    //计算起始页
    if(totalPages>0){
      pageNumber = totalPages <= pageNumber ? totalPages-1 : pageNumber;
    }

    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.totalPages =  totalPages;
    this.totalCount = totalCount;

  }

  public PageBean(PageBean pageBean) {
    Assert.notNull(pageBean,"pagebean cannot be null");
    this.pageNumber = pageBean.getPageNumber();
    this.pageSize = pageBean.getPageSize();
    this.totalPages =  pageBean.getTotalPages();
    this.totalCount = pageBean.getTotalCount();
  }

}
