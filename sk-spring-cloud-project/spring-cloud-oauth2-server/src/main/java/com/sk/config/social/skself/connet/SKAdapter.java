package com.sk.config.social.skself.connet;

import com.sk.config.social.skself.api.SK;
import com.sk.config.social.skself.api.SKUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 登陆配置
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
public class SKAdapter implements ApiAdapter<SK> {

	@Override
	public boolean test(SK api) {
		return true;
	}

	@Override
	public void setConnectionValues(SK api, ConnectionValues values) {
		SKUserInfo userInfo = api.getUserInfo();
		values.setDisplayName(userInfo.getScreenname());
		values.setImageUrl(userInfo.getImgUrl());
		values.setProfileUrl(null);
		values.setProviderUserId(userInfo.getProviderUserId());
	}

	@Override
	public UserProfile fetchUserProfile(SK api) {
		return null;
	}

	@Override
	public void updateStatus(SK api, String message) {
	}

}
