package xyz.b515.schedule.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import xyz.b515.schedule.entity.Course;


public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "schedule.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Course, Integer> courseDao = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Course.class);
        } catch (SQLException e) {
            Log.e(DBHelper.class.getName(), "onCreate", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Course.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DBHelper.class.getName(), "onUpgrade", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Course, Integer> getCourseDao() throws SQLException {
        if (courseDao == null) {
            courseDao = getDao(Course.class);
        }
        return courseDao;
    }

    @Override
    public void close() {
        super.close();
        courseDao = null;
    }
}
