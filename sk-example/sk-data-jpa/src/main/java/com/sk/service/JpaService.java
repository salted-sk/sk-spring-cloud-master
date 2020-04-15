package com.sk.service;

import com.github.pagehelper.PageHelper;
import com.sk.dao.JpaDao;
import com.sk.dao.TkJpaDao;
import com.sk.entity.JpaTable1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/4/15 15:10
 */
@Service
public class JpaService {

    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private TkJpaDao tkJpaDao;

    public List<JpaTable1> getList() {
        PageHelper.startPage(1, 2);
        tkJpaDao.selectAll();
        return jpaDao.findAll();
    }

}
