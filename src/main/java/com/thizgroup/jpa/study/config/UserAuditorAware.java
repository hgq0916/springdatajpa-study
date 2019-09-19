package com.thizgroup.jpa.study.config;

import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration//使用jpa审计功能，保存数据时自动插入创建人id和更新人id
public class UserAuditorAware implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    //从session中获取当前登录用户的id
    Long userId = 2L;
    return Optional.of(userId);
  }
}
