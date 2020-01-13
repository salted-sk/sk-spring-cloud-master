package com.sk.relatedsql;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/1/13 16:54
 */
public class Json2Map {
    public static List<Map> getlist()  {
        List<Map> list = new ArrayList<>();
        try {
            File file = new File("a.json");
            FileReader reader = new FileReader(file);
            char[] chars = new char[1];
            String maps = "";
            boolean flag = false;
            while (reader.read(chars) != -1){
                if (chars[0] == '{') {
                    flag = true;
                }
                if (chars[0] == '}') {
                    maps += "}";
                    Map map = (Map) JSON.parse(maps);
                    list.add(map);
                    flag = false;
                    maps = "";
                }
                if (flag) {
                    maps += chars[0];
                }
            }
            reader.close();
            list.size();
        } catch (Exception e) {

        }
        return list;
    }
}
