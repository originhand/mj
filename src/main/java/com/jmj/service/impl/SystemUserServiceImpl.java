package com.jmj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jmj.entity.SystemUser;
import com.jmj.service.SystemUserService;
import com.jmj.mapper.SystemUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 73848
* @description 针对表【system_user】的数据库操作Service实现
* @createDate 2025-03-13 17:53:08
*/
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
    implements SystemUserService{

}




