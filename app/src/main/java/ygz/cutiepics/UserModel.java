package ygz.cutiepics;

import java.io.Serializable;

/**
 * Created by Raina on 2018-03-20.
 */

public class UserModel implements Serializable {
    private String email;

    public UserModel(String email) {this.email = email;}

    public String getId() {
        return email;
    }
}
