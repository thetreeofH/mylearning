package com.ts.spm.bizs.rest.gis;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

/**
 * Created by zhoukun on 2020/4/17.
 */

@RestController
@RequestMapping("tile")
@IgnoreClientToken
@IgnoreUserToken
public class GisTileServerController {
    private Connection conn = null;
    private byte[] imgByte = null;

    @Value("${gisTilePath}")
    private String tilePath;

    @GetMapping(value = "info")
    public void getTile(HttpServletResponse response, String L, String X, String Y) {
        if(conn == null){
            try {
                Class.forName("org.sqlite.JDBC");
                String conurl = "jdbc:sqlite:" + tilePath;
                conn = DriverManager.getConnection(conurl,null,null);
                // 设置自动提交为false
                conn.setAutoCommit(false);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
                System.out.println("数据库驱动未找到!");
                conn = null;
                return;
            }
        }
        response.setHeader("Content-type", "image/png");
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT tile_data FROM tiles WHERE zoom_level = "+ L +
                    " AND tile_column = "+ X +
                    " AND tile_row = "+ Y +
                    " limit 1";
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                byte[] imgByte = (byte[]) rs.getObject("tile_data");
                OutputStream os = response.getOutputStream();
                os.write(imgByte);
                os.close();
            } else {
                if(imgByte == null) {
                    InputStream is = new ClassPathResource("data/cover.png").getInputStream();
                    imgByte = new byte[is.available()];
                    is.read(imgByte);
                    is.close();
                }
                OutputStream os = response.getOutputStream();
                os.write(imgByte);
                os.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL异常!");
        } catch(IOException io) {
            io.printStackTrace();
            System.out.println("读取切片异常!");
        } finally {
            try {
                rs.close();
                //conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
