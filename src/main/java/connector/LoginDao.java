package connector;

import java.sql.*;

public class LoginDao {

    public static boolean validate(LoginBean bean) {
        boolean status = false;
        try {
            Connection con = ConnectionProvider.getCon();

            PreparedStatement ps = con.prepareStatement(
                    "select * from users where email=? and password=?");

            ps.setString(1, bean.getEmail());
            ps.setString(2, bean.getPass());

            ResultSet rs = ps.executeQuery();
            status = rs.next();

        } catch (SQLException e) {

            System.out.println("error: " + e.getMessage());
        }

        return status;

    }
}
