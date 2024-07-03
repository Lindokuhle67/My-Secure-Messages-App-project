/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.encryption;

import za.ac.tut.message.Message;

/**
 *
 * @author Lindokuhle
 */
public class MessageEncryptor {
    public MessageEncryptor(){
    }
    
   
    public Message encrypt(Message plainMessage){
      
        char charAIndex, repalcementChar;
        String plainMsg = plainMessage.getPlainMsg();
        String encryptedMsg = "";
        Message message;
        
        for(int i = 0; i < plainMsg.length();i++){
            charAIndex = plainMsg.charAt(i);
            
            if(Character.isLetter(charAIndex)){
                switch(charAIndex){
                    case 'a':
                    case 'A':
                        repalcementChar = '@';
                        break;
                    case 'e':
                    case 'E':
                        repalcementChar = '#';
                        break;     
                    case 'i':
                    case 'I':
                        repalcementChar = '$';
                        break;
                    case 'o':
                    case 'O':
                        repalcementChar = '%';
                        break;  
                    case 'u':
                    case 'U':
                        repalcementChar = '&';
                        break;
                    default:
                        repalcementChar = charAIndex;
                }
                
                encryptedMsg = encryptedMsg + Character.toString(repalcementChar);
            } else {
                encryptedMsg = encryptedMsg + Character.toString(charAIndex);
            }
        }
        
        message = new Message(encryptedMsg);
        
        return message;
    }
}
