package com.cleanweather.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    /**
     * Default DB file name
     */
    private static final String DB_FILE_NAME = "base.db";
    
    private static final String INIT_CREATE_SQL = "db_init";

    /**
     * SQLiteDatabase object
     */
    private SQLiteDatabase dbObj;

    /**
     * Context object
     */
    private Context context;

    public DBHelper(Context context)
    {
        super(context, DB_FILE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public DBHelper(Context context, String dbName)
    {
        super(context, dbName, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        int pointer = context.getResources().getIdentifier(INIT_CREATE_SQL, "string", context.getPackageName());
        if (pointer == 0) {
            System.out.println("INIT_CREATE_SQL  not defined");
        } else {
            String[] createTabelSqls = context.getResources().getString(pointer).split(";");
            for (String sql : createTabelSqls) {
                db.execSQL(sql + ";");
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    public Map<String, Object> executeForMap(String sqlId, Map<String, Object> bindParams)
    {
        List<Map<String, Object>> list = executeForMapList(sqlId, bindParams);
        if (list.size() <= 0)
        {
            return null;
        }
        else
        {
            return list.get(0);
        }
    }

    public List<Map<String, Object>> executeForMapList(String sqlId, Map<String, Object> bindParams)
    {
        getDbObject();
        int pointer = context.getResources().getIdentifier(sqlId, "string", context.getPackageName());
        if (pointer == 0)
        {
            System.out.println("undefined sql id");  // 改为Adnroid的LOG
            return null;
        }
        String sql = context.getResources().getString(pointer);
        if (bindParams != null)
        {
            Iterator<String> mapIterator = bindParams.keySet().iterator();
            while (mapIterator.hasNext())
            {
                String key = mapIterator.next();
                Object value = bindParams.get(key);
                sql = sql.replaceAll("#" + key + "#", value == null ? null : value.toString());
            }
        }
        if (sql.indexOf('#') != -1)
        {
            System.out.println("undefined parameter");
            return null;
        }
        Cursor cursor = dbObj.rawQuery(sql, null);
        if (cursor == null)
        {
            return null;
        }
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        String[] columnNames = cursor.getColumnNames();
        while (cursor.moveToNext())
        {
            Map<String, Object> map = new HashMap<String, Object>();
            int i = 0;
            for (String columnName : columnNames)
            {
                map.put(columnName, cursor.getString(i));
                i++;
            }
            mapList.add(map);
        }
        cursor.close();
        dbObj.close();
        return mapList;
    }

    @SuppressWarnings(
    { "rawtypes" })
    public <T> T executeForBean(String sqlId, Map<String, Object> bindParams, Class bean)
    {
        List<T> objectList = executeForBeanList(sqlId, bindParams, bean);
        if( objectList.size() <= 0)
        {
            return null;
        }
        else
        {
            return objectList.get(0);
        }
    }

    @SuppressWarnings(
    { "unchecked", "rawtypes" })
    public <T> ArrayList<T> executeForBeanList(String sqlId, Map<String, Object> bindParams, Class bean)
    {
        getDbObject();
        int pointer = context.getResources().getIdentifier(sqlId, "string", context.getPackageName());
        if (pointer == 0)
        {
            System.out.println("undefined sql id");
            return null;
        }
        String sql = context.getResources().getString(pointer);
        if (bindParams != null)
        {
            Iterator<String> mapIterator = bindParams.keySet().iterator();
            while (mapIterator.hasNext())
            {
                String key = mapIterator.next();
                Object value = bindParams.get(key);
                sql = sql.replaceAll("#" + key + "#", value == null ? null : value.toString());
                System.out.println("sql-->"+sql);    //////////////
            }
        }
        if (sql.indexOf('#') != -1)
        {
            System.out.println("undefined parameter");
            return null;
        }
        Cursor cursor = dbObj.rawQuery(sql, null);
        ArrayList<T> objectList = new ArrayList<T>();
        if (cursor == null)
        {
            return null;
        }
        String[] columnNames = cursor.getColumnNames();
        T beanObj = null;
        while (cursor.moveToNext())
        {
            try
            {
                beanObj = (T) bean.newInstance();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }

            int i = 0;
            for (String dataName : columnNames)
            {
                try
                {
                    Field field = bean.getDeclaredField(dataName);
                    field.setAccessible(true);
                    Class type = field.getType();
                    String typeName = type.getName();
                    if (typeName.equals("int"))
                    {
                        field.setInt(beanObj, cursor.getInt(i));
                    }
                    else if (typeName.equals("long"))
                    {
                        field.setLong(beanObj, cursor.getLong(i));
                    }
                    else if (typeName.equals("java.lang.String"))
                    {
                        field.set(beanObj, cursor.getString(i));
                    }
                    else if (typeName.equals("double"))
                    {
                        field.setDouble(beanObj, cursor.getDouble(i));
                    }
                    else if (typeName.equals("float"))
                    {
                        field.setFloat(beanObj, cursor.getFloat(i));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    i--;
                }
                i++;
            }
            objectList.add(beanObj);
        }
        cursor.close();
        dbObj.close();
        return objectList;
    }

    public int execute(String sqlId, Map<String, Object> bindParams)
    {
        getDbObject();
        int row = 0;
        int pointer = context.getResources().getIdentifier(sqlId, "string", context.getPackageName());
        if (pointer == 0)
        {
            System.out.println("undefined sql id");
            return row;
        }
        String sql = context.getResources().getString(pointer);
        if (bindParams != null)
        {
            Iterator<String> mapIterator = bindParams.keySet().iterator();
            while (mapIterator.hasNext())
            {
                String key = mapIterator.next();
                Object value = bindParams.get(key);
                sql = sql.replaceAll("#" + key + "#", value == null ? null :value.toString());
            }
        }
        if (sql.indexOf('#') != -1)
        {
            System.out.println("undefined parameter");
            return row;
        }
        try
        {
            dbObj.execSQL(sql);
            dbObj.close();
            row += 1;
        }
        catch (SQLException e)
        {
            return row;
        }
        return row;
    }

    private SQLiteDatabase getDbObject()
    {
        if (dbObj == null || !dbObj.isOpen())
        {
            dbObj = getWritableDatabase();
        }
        return dbObj;
    }
}
