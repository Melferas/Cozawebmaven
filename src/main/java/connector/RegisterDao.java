package connector;

import java.sql.*;

public class RegisterDao {

    public static boolean validate(LoginBean bean) {
        boolean status = false;
        try {
            Connection con = ConnectionProvider.getCon();

            PreparedStatement ps = con.prepareStatement(
                    "insert into users (email,password) VALUES (?,?)");

            ps.setString(1, bean.getEmail());
            ps.setString(2, bean.getPass());

            int rs = ps.executeUpdate();
            status = rs>0;

        } catch (SQLException e) {

            System.out.println("error: " + e.getMessage());
        }

        return status;

    }
}
