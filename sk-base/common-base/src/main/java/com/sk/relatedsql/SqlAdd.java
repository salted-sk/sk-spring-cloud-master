package com.sk.relatedsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 自动导入数据到数据库
 *
 * @author zhangqiao
 * @since 2019/12/4 17:39
 */
public class SqlAdd {
//    static final String url = "jdbc:oracle:thin:@47.104.23.202:49161:xe";
    static final String url = "jdbc:mysql://112.124.3.225:3333/blog";
    static final String name = "com.mysql.jdbc.Driver";
//    static final String name = "oracle.jdbc.driver.OracleDriver";
    static final String user = "root";
    static final String password = "frzxbkw";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        final String url = "jdbc:mysql://ip";

        Connection conn = null;
        Class.forName(name);//指定连接类型
        conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn!=null) {
            System.out.println("获取连接成功");
            insert3(conn);
        }else {
            System.out.println("获取连接失败");
        }

    }

    public static void insert3(Connection conn) {
        try {
            String sql = "INSERT INTO weather_city (id,pid,city_code,city_name,post_code,area_code) VALUES (?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            List<Map> list = Json2Map.getlist();
            for (int i = 0; i < list.size(); i++) {
                conn.setAutoCommit(false);//设置数据手动提交，自己管理事务  
                pstmt.setInt(1, (Integer) list.get(i).get("id"));
                pstmt.setInt(2, (Integer) list.get(i).get("pid"));
                pstmt.setString(3, (String) list.get(i).get("city_code"));
                pstmt.setString(4, (String) list.get(i).get("city_name"));
                pstmt.setString(5, (String) list.get(i).get("post_code"));
                pstmt.setString(6, (String) list.get(i).get("area_code"));
                pstmt.addBatch();
                if(i == list.size()-1){
                    pstmt.executeBatch();
                    conn.commit();
                    conn.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
