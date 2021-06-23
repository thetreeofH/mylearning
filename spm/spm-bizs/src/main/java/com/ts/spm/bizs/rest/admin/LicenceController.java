package com.ts.spm.bizs.rest.admin;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.admin.LicenceBiz;
import com.ts.spm.bizs.entity.admin.Licence;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ace
 */
@RestController
@RequestMapping("licence")
@CheckClientToken
@CheckUserToken
public class LicenceController extends BaseController<LicenceBiz, Licence, String> {

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ObjectRestResponse getLicence() {
        return new ObjectRestResponse().data(baseBiz.getLicenceInfo());
    }
}
