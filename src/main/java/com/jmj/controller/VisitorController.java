package com.jmj.controller;

import com.common.ResponseBean;
import com.common.ResponseEnum;
import com.jmj.entity.TVisitorInfo;
import com.jmj.service.TVisitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/visitor")
public class VisitorController {
    @Autowired
    private TVisitorInfoService visitorInfoService;

    @PostMapping("/visit")
    @ResponseBody
    public ResponseBean visit(TVisitorInfo visitorInfo) {
        // 确保 visitorInfo 对象的必要字段不为空
        if (visitorInfo.getVisitorName() == null || visitorInfo.getVisitorTelNumber() == null) {
            return ResponseBean.error(ResponseEnum.PARAMETER_IS_ABNORMAL);
        }
        boolean result = visitorInfoService.save(visitorInfo);
        return result ? ResponseBean.success() : ResponseBean.error(ResponseEnum.PARAMETER_IS_ABNORMAL);
    }
}