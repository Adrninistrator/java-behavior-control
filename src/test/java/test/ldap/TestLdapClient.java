package test.ldap;

import com.sun.rowset.JdbcRowSetImpl;
import org.junit.Test;
import test.base.TestBase;

import java.sql.SQLException;

import static org.junit.Assert.assertThrows;

public class TestLdapClient extends TestBase {

    @Test
    public void test() throws SQLException {
        JdbcRowSetImpl jdbcRowSet = new JdbcRowSetImpl();
        jdbcRowSet.setDataSourceName("ldap://localhost:1099/remote");

        assertThrows(SQLException.class, () ->
                jdbcRowSet.setAutoCommit(true)
        );
    }
}
