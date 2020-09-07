package com.thizgroup.jpa.study.converter;

import com.thizgroup.jpa.study.enums.SexEnum;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)//autoApply为true表示这个转换器被自动应用到所有的entity
public class SexEnumAttributeConverter implements AttributeConverter<SexEnum,Integer> {

  @Override
  public Integer convertToDatabaseColumn(SexEnum sexEnum) {
    if(sexEnum == null) {
      return null;
    }
    return sexEnum.getCode();
  }

  @Override
  public SexEnum convertToEntityAttribute(Integer code) {
    if(code == null) {
      return null;
    }
    SexEnum[] values = SexEnum.values();
    for(SexEnum sexEnum : values) {
      if(code.equals(sexEnum.getCode())) {
        return sexEnum;
      }
    }
    throw new IllegalArgumentException("无效的SexEnum:"+code);
  }

}
