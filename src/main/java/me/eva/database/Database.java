package me.eva.database;

import io.github.cdimascio.dotenv.Dotenv;
import me.eva.ColonThreeBot;
import me.eva.utils.Initialize;
import net.dv8tion.jda.api.entities.User;

import java.sql.*;

public class Database {

    private static Connection connection;

    private void initialize() {}

    public static void writeUser(Userdata user) {
        try {
            connection = DriverManager.getConnection(getDbString("bot_client", ColonThreeBot.dotenv.get("BOT_DB_PASSWORD")));

            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (id, joined_at) VALUES (?,?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getJoinedAt());

            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Userdata queryUser(String id) {
        try {
            connection = DriverManager.getConnection(getDbString("bot_client", ColonThreeBot.dotenv.get("BOT_DB_PASSWORD")));

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id='" + id + "'");
            ResultSet rs = ps.executeQuery();
            Userdata userdata = Userdata.of(rs.getString("id"), rs.getString("joined_at"));

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Userdata.of("null", "null");
    }

    private static String getDbString(String user, String password) {
        return "jdbc:mysql://localhost/cheese_discord_bot_data?user=" + user +"&password=" + password;
    }

}
