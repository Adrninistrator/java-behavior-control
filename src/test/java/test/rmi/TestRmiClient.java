package test.rmi;

import com.sun.rowset.JdbcRowSetImpl;
import org.junit.Test;
import test.base.TestBase;

import java.sql.SQLException;

public class TestRmiClient extends TestBase {

    @Test
    public void test() throws SQLException {
        JdbcRowSetImpl jdbcRowSet = new JdbcRowSetImpl();
        jdbcRowSet.setDataSourceName("rmi://localhost:1099/remote");
        jdbcRowSet.setAutoCommit(true);
    }
}
