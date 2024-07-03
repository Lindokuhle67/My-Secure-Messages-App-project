/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.message;

/**
 *
 * @author Lindokuhle
 */
public class Message {
    private String plainMsg;

    public Message(String plainMsg) {
        this.plainMsg = plainMsg;
    }

    public String getPlainMsg() {
        return plainMsg;
    }

    public void setPlainMsg(String plainMsg) {
        this.plainMsg = plainMsg;
    }

    @Override
    public String toString() {
        return plainMsg;
    }
    
}
