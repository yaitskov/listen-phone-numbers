package com.githug.listenumbers;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.github.listenumbers.db.DbHelper;
import com.github.listenumbers.db.JavaClassType;
import com.github.listenumbers.db.dao.OAuthApiConfigDao;
import com.github.listenumbers.db.entity.OAuthApiConfig;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import org.junit.Assert;

import java.sql.SQLException;
import java.util.concurrent.Callable;

public class OrmLiteTest extends ApplicationTestCase {
    public static final String REDIRECT = "http://localhost";
    public static final String API_KEY = "12312312";
    public static final String API_SECRET = "12312312";
    public static final String ID = "o1";
    public static final String ID2 = "o2";
    private DbHelper db;
    private OAuthApiConfigDao dao;

    public OrmLiteTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        db = new DbHelper(getContext());
        dao =  db.getOAuthApiConfigDao();
        TableUtils.clearTable(dao.getConnectionSource(), OAuthApiConfig.class);
    }

    public void testInit() throws Exception {
        Assert.assertNull(dao.queryForId(ID));
        dao.create(new OAuthApiConfig(ID, REDIRECT, API_KEY, API_SECRET));
        Dao<OAuthApiConfig, String> dao2 =  db.getOAuthApiConfigDao();
        Assert.assertEquals(dao2, dao);

        db.close();
        db = new DbHelper(getContext());

        dao2 = db.getOAuthApiConfigDao();
        Assert.assertNotEquals(dao2, dao);
        OAuthApiConfig config = dao2.queryForId(ID);

        Assert.assertEquals(ID, config.getName());
        Assert.assertEquals(REDIRECT, config.getRedirect());
        Assert.assertEquals(API_KEY, config.getApiKey());
        dao2.deleteById(ID);

        Assert.assertNull(dao2.queryForId(ID));
        Log.d("TEST", "COOL");
    }

    public void testTransaction() throws SQLException {
        Assert.assertNull(dao.queryForId(ID));
        Assert.assertNull(dao.queryForId(ID2));
        try {
            dao.callBatchTasks(new Callable<Integer>() {
                public Integer call() throws Exception {
                    dao.create(new OAuthApiConfig(ID, REDIRECT, API_KEY, API_SECRET));
                    int x = 0;
                    x = 1 / x;
                    dao.create(new OAuthApiConfig(ID2, REDIRECT, API_KEY, API_SECRET));
                    return x;
                }
            });
            Assert.fail();
        } catch (SQLException e) {
            Assert.assertNull(dao.queryForId(ID));
            Assert.assertNull(dao.queryForId(ID2));
            Assert.assertEquals(ArithmeticException.class, e.getCause().getClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void testJavaClassType() throws SQLException {
        dao.create(new OAuthApiConfig(ID, REDIRECT, API_KEY, API_SECRET,
                null, null, null, null, JavaClassType.class));
        dao.clearObjectCache();
        Assert.assertEquals(JavaClassType.class, dao.queryForId(ID).getApiClass());
    }

    public void testQueryForIdNull() throws SQLException {
        try {
            dao.queryForId(null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // ok
        }
        Assert.assertNull(dao.queryForId(""));
    }

    public void tearDown() {
        db.close();
    }
}
