package com.sk.blog.website.service;

import com.sk.blog.website.model.Vo.OptionVo;

import java.util.List;
import java.util.Map;

/**
 * options的接口
 * Created by BlueT on 2017/3/7.
 */
public interface IOptionService {

    void insertOption(OptionVo optionVo);

    void insertOption(String name, String value);

    List<OptionVo> getOptions();


    /**
     * 保存一组配置
     *
     * @param options
     */
    void saveOptions(Map<String, String> options);

    OptionVo getOptionByName(String name);
}
