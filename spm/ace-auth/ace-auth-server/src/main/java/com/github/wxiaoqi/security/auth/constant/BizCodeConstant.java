package com.github.wxiaoqi.security.auth.constant;

/**
 * ClientAccountConstant class
 *
 * @author liang
 * @todo
 * @date 2020/10/30
 */
public class BizCodeConstant {



    public static enum CommonBIzCode {
        COMMON_BIZ_EX(30101, "业务处理异常");
        int code;
        String msg;


        CommonBIzCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
   public static enum LoginBizCode {
         APP_BIND_EX(401020,"用户已绑定设备编码，请联系管理员"),
         APP_BIND_PARAMS_EX(401021,"设备编码请求参数无效"),
         USER_NAME_NOT_FOIND(401022,"用户名不合法"),
         USER_PWD_ERROR(401023,"密码错误"),
         ACCOUNT_DISABLE(401024,"帐号已冻结");
         int code;
         String msg;


        LoginBizCode(int code, String msg) {
            this.code=code;
            this.msg=msg;
        }

       public int getCode() {
           return code;
       }

       public String getMsg() {
           return msg;
       }
   }
}
