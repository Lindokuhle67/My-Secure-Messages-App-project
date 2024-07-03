/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.in;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import za.ac.tut.encryption.MessageEncryptor;
import za.ac.tut.message.Message;

/**
 *
 * @author Lindokuhle
 */
public class MessageDB implements DOB{

    private Connection connection;
    
     public MessageDB(String url,String username,String password) throws SQLException {
        connection = DriverManager.getConnection(url, username,password );
    }
    
    @Override
    public String add(String plain, String encyrpted) throws SQLException {
      
            
        String sql = "INSERT INTO msg_tbl(plainMsg,encryptedMsg,time) VALUES(?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,plain);
        ps.setString(2,encyrpted);
        
        ps.setTimestamp(3, Timestamp.from(Instant.now()));
        
        ps.executeUpdate();
        ps.close();
        
                
        return "Successful saved to database!!"; 
    }
    
}
