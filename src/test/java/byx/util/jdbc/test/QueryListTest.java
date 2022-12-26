package byx.util.jdbc.test;

import byx.util.jdbc.core.BeanRowMapper;
import byx.util.jdbc.test.domain.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryListTest extends BaseTest {
    @Test
    public void test1() {
        List<User> users = jdbcUtils.queryList("SELECT * FROM users", new BeanRowMapper<>(User.class));

        assertNotNull(users);
        assertEquals(5, users.size());
    }

    @Test
    public void test2() {
        List<User> users = jdbcUtils.query("SELECT * FROM users", record -> {
            List<User> us = new ArrayList<>();
            while (record.next()) {
                User u = new User();
                u.setId(record.getInt("id"));
                u.setUsername(record.getString("username"));
                u.setPassword(record.getString("password"));
                us.add(u);
            }
            return us;
        });

        assertNotNull(users);
        assertEquals(5, users.size());
    }

    @Test
    public void test3() {
        List<User> users = jdbcUtils.queryList("SELECT * FROM users", row -> {
            User u = new User();
            try {
                u.setId(row.getInt("id"));
                u.setUsername(row.getString("username"));
                u.setPassword(row.getString("password"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return u;
        });

        assertNotNull(users);
        assertEquals(5, users.size());
    }

    @Test
    public void test4() {
        List<User> users = jdbcUtils.queryList("SELECT * FROM users WHERE password = ?", new BeanRowMapper<>(User.class), "456");

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void test5() {
        List<User> users = jdbcUtils.queryList("SELECT * FROM users WHERE password = 10086", new BeanRowMapper<>(User.class));

        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test
    public void test6() {
        List<String> usernames = jdbcUtils.queryList("SELECT * FROM users", row -> {
            try {
                return row.getString("username");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        assertNotNull(usernames);
        assertEquals(5, usernames.size());
    }

    @Test()
    public void test7() {
        assertThrows(RuntimeException.class, () -> jdbcUtils.queryList("SELECT * FROM users WEAR id = 1", new BeanRowMapper<>(User.class)));
    }

    @Test
    public void test8() {
        assertThrows(RuntimeException.class, () -> jdbcUtils.queryList("SELECT * FROM users", new BeanRowMapper<>(User.class), "aaa"));
    }

    @Test
    public void test9() {
        List<User> users = jdbcUtils.queryList("SELECT * FROM users", User.class);

        assertNotNull(users);
        assertEquals(5, users.size());
    }

    @Test
    public void test10() {
        List<User> users = jdbcUtils.queryList("SELECT * FROM users WHERE password = ?", User.class, "456");

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void test11() {
        List<User> users = jdbcUtils.queryList("SELECT * FROM users WHERE password = 10086", User.class);

        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test
    public void test12() {
        assertThrows(RuntimeException.class, () -> jdbcUtils.queryList("SELECT * FROM", User.class));
    }

    @Test
    public void test13() {
        assertThrows(RuntimeException.class, () -> jdbcUtils.queryList("SELECT * FROM users", User.class, "aaa", "123"));
    }
}
