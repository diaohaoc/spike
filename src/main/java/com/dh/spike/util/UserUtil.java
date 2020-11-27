package com.dh.spike.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dh.spike.domain.SpikeUser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by DiaoHao on 2020/10/27 21:10
 */
public class UserUtil {

    private static void createUser(int count) throws Exception {
        List<SpikeUser> users = new ArrayList<>();
        //生成用户
        for (int i = 0; i < count; i++) {
            SpikeUser user = new SpikeUser();
            user.setId(19900000000L + i);
            user.setLoginCount(1);
            user.setNickname("user" + i);
            user.setRegisterDate(new Date());
            user.setSalt("1a2b3c");
            user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSalt()));
            users.add(user);
        }
        System.out.println("create user");
        //插入数据库
//        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.50.251:3306/spikemall?useSSL=false&serverTimezone=UTC",
//                "admin", "root");
//        String sql = "insert into spike_user(id,nickname,password,salt,register_date,login_count)values(?,?,?,?,?,?)";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        for (SpikeUser user : users) {
//            ps.setLong(1, user.getId());
//            ps.setString(2, user.getNickname());
//            ps.setString(3, user.getPassword());
//            ps.setString(4, user.getSalt());
//            ps.setTimestamp(5, new Timestamp(user.getRegisterDate().getTime()));
//            ps.setInt(6, user.getLoginCount());
//            ps.addBatch();
//        }
//        ps.executeBatch();
//        ps.close();
//        conn.close();
//        System.out.println("insert to db");
        //生成token
        String urlString = "http://localhost:8080/login/do_login";
        File file = new File("D:/tokens.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (SpikeUser user : users) {
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection)url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String param = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFormPass("123456");
            out.write(param.getBytes());
            out.flush();
            InputStream is = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff)) > 0) {
                bout.write(buff, 0, len);
            }
            is.close();;
            bout.close();
            String response = new String(bout.toByteArray());
            JSONObject jo = JSON.parseObject(response);
            String token = jo.getString("data");
            System.out.println("create token : " + user.getId());
            String row = user.getId() + "," + token;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();

        System.out.println("over");
    }

    public static void main(String[] args) throws Exception{
        createUser(5000);
    }
}
