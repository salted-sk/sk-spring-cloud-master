package com.sk.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

/**
 * 克隆工具类，实现原理通过JSON序列化反序列化实现
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
@SuppressWarnings("unchecked")
public final class CloneUtils {
    /**
     * 禁用构造方法
     */
    private CloneUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过java序列化方式进行克隆
     *
     * @param entity 实体对象
     * @return 克隆后的实体对象
     */
    public static <E> E cloneBySerial(E entity) {
        if (entity == null) {
            return null;
        }
        // 没有实现序列化统一使用json方式序列化
        if (!(entity instanceof Serializable)) {
            return cloneByJson(entity, false);
        }
        // 定义一个缓冲输出流对象
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectInputStream input = null;
        try (ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            // 将对象输出到缓冲区
            out.writeObject(entity);
            // 重新从缓冲区读取对象
            input = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            return (E) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                buffer.close();
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过JSON序列化方式进行克隆，默认不copy忽略对象
     *
     * @param entity        实例对象
     * @param typeReference 返回类型
     * @return 克隆后的实体对象
     */
    public static <E, T extends E> E cloneByJson(E entity, TypeReference<T> typeReference) {
        return cloneByJson(entity, typeReference, false);
    }

    /**
     * 通过JSON序列化方式进行克隆
     *
     * @param entity 实例对象
     * @return 克隆后的实体对象
     */
    public static <E> E cloneByJson(E entity) {
        return cloneByJson(entity, false);
    }

    /**
     * 通过JSON序列化方式进行克隆
     *
     * @param entity 实例对象
     * @param copy   是否复制被忽略的属性
     * @return 克隆后的实体对象
     */
    public static <E> E cloneByJson(E entity, boolean copy) {
        return cloneByJson(entity, null, copy);
    }

    /**
     * 通过JSON序列化方式进行克隆
     *
     * @param entity        实例对象
     * @param copy          是否复制被忽略的属性
     * @param typeReference 返回类型
     * @return 克隆后的实体对象
     */
    public static <E, T extends E> E cloneByJson(E entity, TypeReference<T> typeReference, boolean copy) {
        if (entity == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 进行序列化
            String json = objectMapper.writeValueAsString(entity);
            // 进行反序列化
            E result;
            if (typeReference == null) {
                result = (E) objectMapper.readValue(json, entity.getClass());
            } else {
                result = objectMapper.readValue(json, typeReference);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
