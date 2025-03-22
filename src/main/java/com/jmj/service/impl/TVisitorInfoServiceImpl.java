package com.jmj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jmj.entity.TVisitorInfo;
import com.jmj.service.TVisitorInfoService;
import com.jmj.mapper.TVisitorInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 73848
* @description 针对表【t_visitor_info】的数据库操作Service实现
* @createDate 2025-03-13 17:53:09
*/
@Service
public class TVisitorInfoServiceImpl extends ServiceImpl<TVisitorInfoMapper, TVisitorInfo>
    implements TVisitorInfoService {

}
