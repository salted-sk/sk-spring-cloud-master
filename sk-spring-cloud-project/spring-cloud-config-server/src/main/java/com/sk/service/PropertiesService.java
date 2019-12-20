package com.sk.service;

import com.sk.common.base.BaseService;
import com.sk.dao.PropertiesDao;
import com.sk.entity.Properties;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务层
 *
 * @author zhangqiao
 * @since 2019-12-12 11:11:41
 */
@Service
public class PropertiesService extends BaseService<Properties, PropertiesDao> {

    public List<String> types(String type) {
        return dao.getGroupTypeList(type);
    }
    
}