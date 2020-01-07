package com.sk.config.social.weixin.api;

/**
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316518&token=2d3bbb7b39c63ab8196e7aad045811f2d47e8602&lang=zh_CN
 * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
 * 微信API调用接口
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
public interface Weixin {
    WeixinUserInfo getUserInfo(String openId);
}
