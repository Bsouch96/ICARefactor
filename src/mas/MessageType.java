/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mas;

import java.io.Serializable;

/**
 *
 * @author V8178742
 */
public enum MessageType implements Serializable
{
    USERMESSAGE,
    DELETEUSERMESSAGE,
    ADDUSERMESSAGE,
    SHAREROUTINGTABLE,
    HELLO,
    HELLOACK
}
