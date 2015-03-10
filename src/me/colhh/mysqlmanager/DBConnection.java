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

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Lahiru Udana <lahirukaka@gmail.com>
 */
public class DBConnection {

    private static DBConnection instance;
    private final Connection conn;
    private Properties prop;
    
    private DBConnection() throws ClassNotFoundException, IOException, 
            SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        loadProps();
        conn = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty(
                "host", "localhost") + ":" + prop.getProperty("port","3306") + 
                "/" + prop.getProperty("dbname"), prop.getProperty("user"), 
                prop.getProperty("psw"));
        prop.clear();
    }
    
    /**
     * Get the only instance of DBConnection
     * @return DBConnection
     * @throws ClassNotFoundException 
     * @throws java.io.IOException 
     * @throws java.sql.SQLException 
     */
    public static synchronized DBConnection getInstance() throws 
            ClassNotFoundException, IOException, SQLException
    {
        if(instance == null) instance = new DBConnection();
        return instance;
    }
    
    private void loadProps() throws IOException
    {
        FileInputStream in = new FileInputStream("Resources/db.properties");
        prop = new Properties();
        prop.load(in);
        in.close();
    }
    
    /**
     * Get DB Connection
     * @return Connection
     */
    public Connection getConnection()
    {
        return conn;
    }
}
