package com.sk.common;

import com.sk.common.utils.UUIDUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 自动导入数据到数据库
 *
 * @author zhangqiao
 * @since 2019/12/4 17:39
 */
public class Main {
    static final String url = "jdbc:oracle:thin:@47.104.23.202:49161:xe";
    //        final String name = "com.mysql.jdbc.Driver";
    static final String name = "oracle.jdbc.driver.OracleDriver";
    static final String user = "sk";
    static final String password = "123456";

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
            String sql = "INSERT INTO SK_USERS (ID,ACCOUNT,TEL,AGE,NAME,SEX) VALUES (?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            int recordNum = 0; // 计数器
            int commit_size = 5000;// 每次提交记录数5
            for(int j = 1; j <= 1000000; j++){
                int i = 1;
                conn.setAutoCommit(false);//设置数据手动提交，自己管理事务  
                recordNum++; // 计数
                pstmt.setString(1, UUIDUtils.getUUID());
                pstmt.setString(2, "sk" + j);
                pstmt.setString(3, "13275"+(1000000  - j));
                pstmt.setInt(4, j % 80);
                pstmt.setString(5, "张乔"  + j);
                pstmt.setInt(6, j % 2);
                pstmt.addBatch();
                if(recordNum % commit_size == 0){
                    pstmt.executeBatch();
                    conn.commit();
                    conn.close();
                    conn = DriverManager.getConnection(url, user, password);
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement(sql);
                }
            }
            if (recordNum % commit_size != 0) {
                pstmt.executeBatch();
                conn.commit();
                System.out.println("提交:" + recordNum);
            }
            pstmt.close();
            conn.close();
            System.out.println("insert success!!!");
        } catch (Exception e) {

        }
    }

    public static void insert2(Connection conn) {
        // 开始时间
        Long begin = System.currentTimeMillis();
        // sql前缀
        String prefix = "INSERT INTO SK_USERS (ID,ACCOUNT,TEL,AGE,NAME,SEX) VALUES (?,?,?,?,?,?)";
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(prefix);//准备执行语句

            // 外层循环，总提交事务次数
            for (int i = 1; i <= 10; i++) {
                suffix = new StringBuffer();
                // 第j次提交步长
                for (int j = 1; j <= 100000; j++) {
                    // 构建SQL后缀
                    pst.setString(1, UUIDUtils.getUUID());
                    pst.setString(2, "sk" + i*100000 + j);
                    pst.setString(3, "13275"+(1000000 - i*100000 - j));
                    pst.setInt(4, j % 80);
                    pst.setString(5, "张乔" + i*100000 + j);
                    pst.setInt(6, j % 2);
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//                    suffix.append("('" +  +"','"+sk+"','"+tel+"','"+age+"','"+name+"','"+sex+"'),");
                    pst.executeBatch();

                }
                // 执行操作
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = System.currentTimeMillis();
        // 耗时
        System.out.println("1000万条数据插入花费时间 : " + (end - begin) / 1000 + " s");
        System.out.println("插入完成");
    }

    public static void insert(Connection conn) {
        // 开始时间
        Long begin = System.currentTimeMillis();
        // sql前缀
        String prefix = "INSERT INTO sk_users (id,account,tel,age,name,sex) VALUES ";
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement(" ");//准备执行语句

            // 外层循环，总提交事务次数
            for (int i = 1; i <= 10; i++) {
                suffix = new StringBuffer();
                // 第j次提交步长
                for (int j = 1; j <= 100000; j++) {
                    // 构建SQL后缀
//                    String string = "";
//                    for (int k = 0; k < 10; k++) {
//                        char c = (char) ((Math.random() * 26) + 97);
//                        string += (c + "");
//                    }
                    String sk = "sk" + i*100000 + j;
                    String tel = "13275"+(1000000 - i*100000 - j );
                    String name = "张乔" + i*100000 + j;
                    int age = j % 80;
                    int sex = j % 2;
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    suffix.append("('" + UUIDUtils.getUUID() +"','"+sk+"','"+tel+"','"+age+"','"+name+"','"+sex+"'),");
                }
                // 构建完整SQL
                String sql = prefix + suffix.substring(0, suffix.length() - 1);
                // 添加执行SQL
                pst.addBatch(sql);
                // 执行操作
                pst.executeBatch();
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = System.currentTimeMillis();
        // 耗时
        System.out.println("1000万条数据插入花费时间 : " + (end - begin) / 1000 + " s");
        System.out.println("插入完成");
    }

}
