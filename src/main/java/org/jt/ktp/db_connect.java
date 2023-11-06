package org.jt.ktp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class db_connect {

    private Connection connection;

    private String host = "localhost";
    private int port = 3306;
    private String database = "mc_test";
    private String username = "root";
    private String password = "1017";

    public int openConnection(JavaPlugin plugin){
        try {
            if (connection != null && !connection.isClosed()) {
                plugin.getLogger().info("데이터 베이스 연결에 실패 했습니다.");
                return 0;
            }

            synchronized (this) {
                if (connection != null && !connection.isClosed()) {
                    plugin.getLogger().info("데이터 베이스 연결에 실패 했습니다.");
                    return 0;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
                plugin.getLogger().info("데이터 베이스에 정상적으로 연결이 되었습니다.");
                if(connection == null) Bukkit.shutdown();
            }
        }
        catch (Exception e) {
            plugin.getLogger().info("데이터 베이스 연결에 실패 했습니다.");
            e.printStackTrace();
            return 0;
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }
}
