package ro.teamnet.zth.api.database;

import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;

/**
 * Author: Ovidiu
 * Date:   4/28/2015
 */
public class DBManagerTest {

    @Test
    public void testGetConnection() {
        System.out.println("Testing DBManager.getConnection()... ");

        Connection connection = DBManager.getConnection();

        Assert.assertNotNull("Failed to open database connection!", connection);
    }

    @Test
    public void testCheckConnection() {
        System.out.println("Testing DBManager.checkConnection()... ");

        Connection connection = DBManager.getConnection();

        Assert.assertTrue(DBManager.checkConnection(connection));
    }

}
