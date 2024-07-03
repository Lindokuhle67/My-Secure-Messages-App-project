/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.in;

import java.sql.SQLException;

/**
 *
 * @author Lindokuhle
 */
public interface DOB {
    String add(String plain,String encrpted)throws SQLException;
}
