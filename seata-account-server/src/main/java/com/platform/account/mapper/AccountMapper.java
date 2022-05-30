package com.platform.account.mapper;


import com.platform.account.domain.Account;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AccountMapper
{
    public Account selectById(Long userId);

    public int updateById(Account account);
}
