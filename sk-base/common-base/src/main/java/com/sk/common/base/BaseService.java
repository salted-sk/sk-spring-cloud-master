package com.sk.common.base;

import com.github.pagehelper.Page;
import com.sk.common.exception.ServiceException;
import com.sk.common.mapper.MyMapper;
import com.sk.common.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 通用service(包含单表的查询、新增、(多)修改,(多)删除)
 * T:数据库实体类或其子类
 * E:数据库实体对应数据库接口层
 * K:table主键类型
 *
 * @author zhangqiao
 * @since 2019-03-13 17:25:36
 */
public abstract class BaseService<T extends BaseEntity, E extends MyMapper<? super T>> {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected E dao;

    /**
     * 当前数据库实体类型
     */
    private Class<? super T> ofClass = getOfClass();

    /**
     * 当前T类型对应的class
     */
    private Class<T> ofTClass = getOfTClass();

    /**
     * 获取列表（未删除）
     *
     * @return 查询列表
     */
    public final List<T> selectList() {
        return selectList(getInstanceOfT());
    }

    /**
     * 根据实体条件获取相关符合数据集(未删除)
     *
     * @param t t
     * @return 查询列表
     */
    public final List<T> selectList(T t) {
        if (t == null) {
            t = getInstanceOfT();
        }
        t.setDeleted(false);
        List<? super T> list = dao.select(t);
        //判断返回类型与对应数据库实体类型是否相同
        boolean sameClass = (ofClass == t.getClass());
        return selectList(list, sameClass);
    }

    /**
     * 根据相关example条件获取相关符合数据集(未删除)
     *
     * @param consumer 参数条件
     * @return 查询列表
     */
    public final List<T> selectList(BiConsumer<Example.Criteria, Example> consumer) {
        Example example = new Example(ofClass);
        Example.Criteria criteria = example.createCriteria();
        consumer.accept(criteria, example);
        criteria.andEqualTo("deleted",false);
        List<? super T> list = dao.selectByExample(example);
        //判断返回类型与对应数据库实体类型是否相同
        boolean sameClass = (ofClass == ofTClass);
        return selectList(list, sameClass);
    }

    /**
     * 获取返回结果
     *
     * @param list 查询结果
     * @param sameClass 返回类型与对应数据库实体类型是否相同
     * @return 返回结果
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<T> selectList(List list, boolean sameClass) {
        List<T> resultList = list;
        if (!sameClass) {
            //将返回的数据库实体结果集转为T类型的结果集
            resultList = Stream.of(list.toArray()).map(this::getOfFrom).collect(toList());
            if (list instanceof Page) {
                list.clear();
                list.addAll(resultList);
                resultList = list;
            }
        }
        return resultList;
    }

    /**
     * 保存/更新(更新过程中不保存为null的属性)
     *
     * @param t t
     * @return 1成功/0失败
     */
    public final int saveAndUpdate(T t) {
        return saveAndUpdate(t, (String[]) null);
    }

    /**
     * 保存/更新(并可以根据需要将实体中部分属性值设置为null)
     *
     * @param t 实例
     * @param nullUpdatePropertyKvs 将修改过程中需要置为null的属性字段
     *        demo:[field1,field2] or [field1:fasle,field2:true]
     *        默认字段属性为false(只将传入为null的参数置为null),true(该字段一定置为null)
     * @return 1成功/0失败
     */
    public final int saveAndUpdate(T t, String... nullUpdatePropertyKvs) {
        int ret = 0;
        if (EmptyUtils.isNotEmpty(t)) {
            if (EmptyUtils.isNotEmpty(t.getId())) {
                t.setUpdateTime(LocalDateTime.now());
                if (EmptyUtils.isNotEmpty(nullUpdatePropertyKvs)) {
                    T t2 = selectOne(t.getId());
                    if (t2 != null) {
                        Map<String, Boolean> nullPropertyMap = getNullUpdatePropertyMap(nullUpdatePropertyKvs);
                        //将数据库查询的不为null的值赋予当前传入为null的实体中
                        Field[] fields = ofClass.getDeclaredFields();
                        updateFieldOf(t, t2, fields, nullPropertyMap);
                        Field[] supperFields = BaseEntity.class.getDeclaredFields();
                        updateFieldOf(t, t2, supperFields, nullPropertyMap);
                        ret = dao.updateByPrimaryKey(t);
                    }
                } else {
                    ret = dao.updateByPrimaryKeySelective(t);
                }
            } else {
                ret = dao.insertSelective(t);
            }
        }
        return ret;
    }

    /**
     * 将属性数组转为key-value值
     */
    private Map<String, Boolean> getNullUpdatePropertyMap(String[] nullUpdatePropertyKvs) {
        Map<String, Boolean> map = new HashMap<>(16);
        String[] propertyKv;
        for (String property : nullUpdatePropertyKvs) {
            propertyKv = property.split(":");
            if (propertyKv.length > 1) {
                map.put(propertyKv[0].trim(), Boolean.valueOf(propertyKv[1].trim()));
            } else {
                map.put(propertyKv[0].trim(), Boolean.FALSE);
            }
        }
        return map;
    }

    /**
     * 修改实体，将需要置为null的值赋予与需要置换的值对换
     */
    private void updateFieldOf(T t, T t2, Field[] fields, Map<String, Boolean> nullPropertyMap) {
        Method method;
        String name;
        try {
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Transient.class)) {
                    // 获取属性的名字
                    name = field.getName();
                    // 将属性的首字符大写获取对应get方法
                    method = t.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                    //判断是否需要将当前修改的实体设置为null
                    //如果传入的实体值为null且当前数据库存入数据不为null且不需要将当前字段置为null则将数据库当前值赋予t
                    if (method.invoke(t) != null && nullPropertyMap.get(name) != null
                            && nullPropertyMap.get(name)) {
                        field.setAccessible(true);
                        field.set(t, null);
                    } else if (method.invoke(t) == null && method.invoke(t2) != null
                            && nullPropertyMap.get(name) == null) {
                        field.setAccessible(true);
                        field.set(t, method.invoke(t2));
                    }
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("实体字段转换失败！");
            throw new ServiceException(e);
        }
    }

    /**
     * 批量修改(修改同一类内容,为null不修改)
     *
     * @param ids IDs
     * @param t t
     * @return 成功数/0失败
     */
    public final int saveAndUpdate(Integer[] ids, T t) {
        int ret = 0;
        if (t != null) {
            Example example = new Example(ofClass);
            Example.Criteria criteria = example.createCriteria();
            if (EmptyUtils.isNotEmpty(ids)) {
                criteria.andIn("id", Arrays.asList(ids));
                ret = dao.updateByExampleSelective(t, example);
            }
        }
        return ret;
    }

    /**
     * 根据主键id获取实体
     *
     * @param id ID
     * @return 实例
     */
    public T selectOne(Integer id) {
        return EmptyUtils.isNotEmpty(id) ? getOfFrom(dao.selectByPrimaryKey(id)) : null;
    }

    /**
     * 获取单个实例(有且当条件满足且确定实例唯一,默认查询未删除,否则抛出异常)
     *
     * @param t t
     * @return 实例
     */
    public final T selectOne(T t) {
        return selectOne(() -> t);
    }

    /**
     * 获取单个实例(有且当条件满足且确定实例唯一,全局查询,否则抛出异常)
     *
     * @param supplier supplier
     * @return 实例
     */
    public final T selectOne(Supplier<T> supplier) {
        if (EmptyUtils.isEmpty(supplier) || EmptyUtils.isEmpty(supplier.get())) {
            return null;
        }
        T t = supplier.get();
        t.setDeleted(false);
        return getOfFrom(dao.selectOne(t));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return 1成功/0失败
     */
    public int delete(Integer id) {
        return EmptyUtils.isNotEmpty(id) ? dao.deleteByPrimaryKey(id) : 0;
    }

    /**
     * 批量删除
     *
     * @param ids IDs
     * @return 成功数/0失败
     */
    public int delete(Integer[] ids) {
        int ret = 0;
        Example example = new Example(ofClass);
        Example.Criteria criteria = example.createCriteria();
        if (EmptyUtils.isNotEmpty(ids)) {
            criteria.andIn("id", Arrays.asList(ids));
            ret = dao.deleteByExample(example);
        }
        return ret;
    }

    /**
     * 根据条件获取数量（根据实体属性查询符合数据量,默认查询未删除）
     *
     * @param t t
     * @return 数量
     */
    public final int selectCount(T t) {
        return selectCount(() -> t);
    }

    /**
     * 根据条件获取数量（根据实体属性查询符合数据量）
     *
     * @param supplier supplier
     * @return 数量
     */
    public final int selectCount(Supplier<T> supplier) {
        T t;
        if (EmptyUtils.isNotEmpty(supplier) && EmptyUtils.isNotEmpty(supplier.get())) {
            t = supplier.get();
        } else {
            t = getInstanceOfT();
        }
        t.setDeleted(false);
        return dao.selectCount(t);
    }

    /**
     * 根据条件获取数量（根据相关条件查询符合数据量）
     *
     * @param consumer consumer
     * @return 数量
     */
    public final int selectCount(BiConsumer<Example.Criteria, Example> consumer) {
        Example example = new Example(ofClass);
        Example.Criteria criteria = example.createCriteria();
        if (consumer != null) {
            consumer.accept(criteria, example);
        }
        criteria.andEqualTo("deleted", false);
        return dao.selectCountByExample(example);
    }

    /**
     * 软删除
     *
     * @param id ID
     * @return 1成功/0失败
     */
    public final int undelete(Integer id) {
        T t = getInstanceOfT();
        t.setId(id);
        t.setDeleted(true);
        return saveAndUpdate(t);
    }

    /**
     * 批量软删除
     *
     * @param ids IDs
     * @return 成功数/0失败
     */
    public final int undelete(Integer[] ids) {
        T t = getInstanceOfT();
        t.setDeleted(true);
        return saveAndUpdate(ids, t);
    }

    /**
     * 将数据库实体类转为T类型
     */
    private T getOfFrom(Object o) {
        if (o == null) {
            return null;
        }
        T t = getInstanceOfT();
        BeanUtils.copyProperties(o, t);
        return t;
    }

    /**
     * 获取当前数据库实体类Class
     */
    private Class<? super T> getOfClass() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<? super T> t = (Class<? super T>) superClass.getActualTypeArguments()[0];
        if (t != null && BaseEntity.class.isAssignableFrom(t)) {
            while (t.getSuperclass() != BaseEntity.class) {
                t = t.getSuperclass();
            }
            return t;
        }
        log.error("无法获取当前对应数据库实体类！");
        throw new ServiceException();
    }

    /**
     * 获取当前T类型对象实例
     * 可在对应seveice层通过new重写该方法
     */
    protected T getInstanceOfT() {
        try {
            return ofTClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("无法获取当前对象实体！");
            throw new ServiceException(e);
        }
    }

    private Class<T> getOfTClass() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked")
        Class<T> t = (Class<T>) superClass.getActualTypeArguments()[0];
        return t;
    }

}


