package com.ts.spm.bizs.biz.admin;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.admin.SecurityCompany;
import com.ts.spm.bizs.mapper.admin.SecurityCompanyMapper;
import org.springframework.stereotype.Service;

@Service
public class CompanyBiz extends BusinessBiz<SecurityCompanyMapper, SecurityCompany> {
}
