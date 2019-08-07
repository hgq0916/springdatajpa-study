package com.thizgroup.jpa.study.dto;

import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
public class PageRecord<T> extends PageBean {

  @Getter
  private List<T> data;//数据列表

  public PageRecord(int pageNumber, int pageSize, long totalCount,List<T> data) {
    super(pageNumber, pageSize, totalCount);
    this.data = data;
  }

  public PageRecord(PageBean pageBean, List<T> data){
    super(pageBean);
    this.data =data;
  }

}
