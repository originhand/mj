package com.jmj.mapper;

import com.jmj.entity.TVisitorInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jmj.entity.vo.VisitorInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 73848
* @description 针对表【t_visitor_info】的数据库操作Mapper
* @createDate 2025-03-13 17:53:09
* @Entity com.jmj.domain.TVisitorInfo
*/
@Mapper
public interface TVisitorInfoMapper extends BaseMapper<TVisitorInfo> {

    List<VisitorInfoVO> getListForEmpId(Integer empId);
}




