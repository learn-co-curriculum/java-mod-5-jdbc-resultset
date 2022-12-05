import java.sql.*;

public class SelectStatement {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/employee_db";
    static final String USER = "postgres";
    static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        try (
                //Establish a connection to the database
                Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                //Create a statement object to execute SQL statements on the database
                Statement statement = connection.createStatement();
        ) {
            try (
                    //The method executeQuery returns a ResultSet object, which encapsulates the set of rows returned from the query.
                    ResultSet rs = statement.executeQuery("SELECT * FROM employee");
            ) {
                //Loop to get each row in the result set.  The method rs.next() moves a cursor to next row.
                while (rs.next()) {
                    //Use the column name to retrieve the value from the current row
                    System.out.println(
                                String.format("id %d email %s office %s salary %.2f",
                                        rs.getInt("id"),
                                        rs.getString("email"),
                                        rs.getString("office"),
                                        rs.getDouble("salary")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}