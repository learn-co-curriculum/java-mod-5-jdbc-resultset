# JDBC ResultSet

## Learning Goals

- Create a `Statement` object to execute SQL statements on the database.
- Call the `executeQuery` method to execute SQL `SELECT` statements.

## Introduction

The previous lesson covered the `execute` and `executeUpdate` methods of the `Statement` interface.
We used the `execute` method to execute `CREATE TABLE` and `DROP TABLE` statements,
and the `executeUpdate` method to execute `INSERT`, `UPDATE`, and `DELETE` statements.

In this lesson we will use the `executeQuery` method to execute a `SELECT` statement.
The `executeQuery` method executes the query passed as a parameter, and returns a single `ResultSet` object:

- `ResultSet	executeQuery(String sql)`

The `ResultSet` interface encapsulates a data table:

- **ResultSet** : An interface that represents a data table, which is usually generated by executing a `SELECT` statement.

A `ResultSet` object maintains a cursor pointing to its current row of data.
The cursor is initially positioned before the first row.  We use the `next()`
method to move the cursor forward to the next row. The `next()` method returns a boolean
indicating whether there are more rows in the result set. We call `next()` in a loop
to move the cursor forward to each row in the query result set.

- `boolean	next()` : Moves the cursor forward one row from its current position.

The `ResultSet` interface includes methods to retrieve a value from the current row either by column index or name.
We will use the following methods:

- `int	getInt(int columnIndex)` : Returns an integer value based on the column index.
- `int	getInt(int columnName)` : Returns an integer value based on the column name.
- `double	getDouble(int columnIndex)` : Returns a double value based on the column index.
- `double	getDouble(int columnName)` : Returns a double value based on the column name.
- `String	getString(int columnIndex)` : Returns a String value based on the column index.
- `String	getString(int columnName)` : Returns a String value based on the column name.

## Code along

[Fork and clone](https://github.com/learn-co-curriculum/java-mod-5-jdbc-resultset) this lesson to run the sample Java programs.

NOTE: This lesson assumes you created a new PostgreSQL database named `employee_db` as instructed
in the first lesson.

The lesson files include the `CreateTableStatement` and `InsertStatement` classes
from the previous lesson.  Run the `main` method in each class to ensure the `employee` table
is populated with the three sample rows.

1. Run `CreateTableStatement.main` to create an empty `employee` table.
2. Run `InsertStatement.main` to add three rows to the table.

Query the `employee` table in the **pgAdmin** query tool to confirm the 3 rows:

![insert 3 rows](https://curriculum-content.s3.amazonaws.com/6036/jdbc-statement/insert_rows.png)

## Execute a SELECT statement

The steps to execute an SQL SELECT statement are:

1. Create a `Connection` object.  
   `Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);`
2. Use the `Connection` object to create a `Statement` object .  
   `Statement statement = connection.createStatement();`
3. Call the `executeQuery` method on the `Statement` object, passing a string containing an SQL SELECT statement.   
   The `executeQuery` method returns a `ResultSet` object containing a table of rows in the result.      
   `ResultSet rs = statement.executeQuery("SELECT * FROM employee");`
4. Loop through each row in the result set.  

The `SelectStatement` class shown below connects to the database, creates a statement object through the connection,
calls the `executeQuery` method to select all rows in the `employee` table, then loops through the result to
print the values in each row. 

Similar to a `Connection` and `Statement`,  a `ResultSet` should be closed after the try statement
executes, thus we surround the call to `executeQuery` in a try-with-resources statement.

```java
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
```

Run `SelectStatement.main` and confirm the output:

```text
id 1 email emp1@company.com office b150 salary 60000.00
id 2 email emp2@company.com office a120 salary 87000.00
id 3 email emp3@company.com office b200 salary 150000.00
```

## Conclusion

The `executeQuery` method of the `Statement` interface executes a
SQL DQL statement such as `SELECT`. The `executeQuery` method returns a single `ResultSet` object.
We can use a loop to get each row in the result set and access the data returned from a query.

## Resources

- [JDBC API](https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html)
- [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/download/)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html)
- [java.sql.DriverManager](https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/DriverManager.html)
- [java.sql.Connection](https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/Connection.html)
- [java.sql.Statement](https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/Statement.html)
- [java.sql.ResultSet](https://docs.oracle.com/en/java/javase/11/docs/api/java.sql/java/sql/ResultSet.html)   


