package com.ts.spm.bizs.rest.matter;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.ts.spm.bizs.biz.AttachmentBiz;
import com.ts.spm.bizs.biz.matter.PointDangerBiz;
import com.ts.spm.bizs.entity.Attachment;
import com.ts.spm.bizs.entity.matter.PointDanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tbcPointDanger")
@IgnoreClientToken
@IgnoreUserToken
public class TbcPointDangerController extends BaseController<PointDangerBiz, PointDanger, Integer> {
    @Autowired
    AttachmentBiz attachmentBiz;
    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
    public PointDanger getById(@PathVariable Integer id) {
        PointDanger pointDanger = getDanger(id);
        return pointDanger;
    }

    PointDanger getDanger(Integer id) {
        PointDanger o=baseBiz.getById(id);
        if(o!=null){
            Attachment att=new Attachment();
            att.setPid(id.toString());
            att.setType("danger");
            o.setAttachs(attachmentBiz.selectList(att));
        }
        return o;
    }

}
