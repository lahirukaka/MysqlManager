/*
 * Copyright (c) 2015 Lahiru Udana <lahirukaka@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Lahiru Udana <lahirukaka@gmail.com> - initial API and implementation and/or initial documentation
 */

package me.colhh.mysqlmanager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lahiru Udana <lahirukaka@gmail.com>
 */
public class MysqlAdapter {

    public static Connection getConnection() throws 
            ClassNotFoundException, IOException, SQLException
    {
        return DBConnection.getInstance().getConnection();
    }
    
    public static PreparedStatement prepareQuery(String sql,Object... params) 
            throws SQLException, ClassNotFoundException, IOException
    {
        PreparedStatement pst = getConnection().prepareStatement(sql);
        int len = params.length;
        for(int x=1;len>=x;x++)
        {
            pst.setObject(x, params[x-1]);
        }
        return pst;
    }
    
    public static int executeSql(String sql,Object... params) throws 
            ClassNotFoundException, IOException, SQLException
    {
        if(params.length < 1)
        {
            Statement st = getConnection().createStatement();
            return st.executeUpdate(sql);
        }else
        {
            return prepareQuery(sql, params).executeUpdate();
        }
    }
    
    public static ResultSet querySql(String sql, Object... params) 
            throws SQLException, ClassNotFoundException, IOException
    {
        ResultSet result;
        if(params.length < 1)
        {
            Statement st = getConnection().createStatement();
            result = st.executeQuery(sql);
        }else
        {
            result = prepareQuery(sql, params).executeQuery();
        }
        return (result.first()) ? result : null;
    }
    
    public static boolean exists(String sql, Object... params) throws 
            SQLException, ClassNotFoundException, IOException
    {
        return (querySql(sql, params) != null);
    }
}
