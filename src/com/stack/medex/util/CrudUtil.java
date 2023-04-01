package com.stack.medex.util;

import com.stack.medex.db.DBconnection;
import com.sun.org.apache.bcel.internal.generic.RET;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {


    public static <T>T executr(String sql,Object...params) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DBconnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstm.setObject((i + 1), params[i]);
        }

        if(sql.startsWith("SELECT")){
            return (T) pstm.executeQuery();
        }
        return (T) (Boolean) (pstm.executeUpdate() > 0);
    }



    /*private static PreparedStatement execute(String sql,Object...params) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DBconnection.getInstance().getConnection().prepareStatement(sql);
        for (int i=0;i<params.length;i++){
            pstm.setObject((i+1),params[i]);
        }
        return pstm;
    }

    public  static  boolean executeUpdate(String sql,Object...param) throws SQLException, ClassNotFoundException {
        return execute(sql,param).executeUpdate() > 0;
    }

    public static ResultSet executeQuery(String sql,Object...param) throws SQLException, ClassNotFoundException {
        return execute(sql,param).executeQuery();
    }*/
}
