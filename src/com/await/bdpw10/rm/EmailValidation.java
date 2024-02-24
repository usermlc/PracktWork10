package com.await.bdpw10.rm;

import com.await.bdpw10.da.entity.User;

public class EmailValidation implements Handler {
    private Handler nextHandler;

    @Override
    public boolean validate(User user){
        if (!user.getEmail().matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")) {
            System.out.println("Емайл введено не коректно.");
            return false;
        }
        return true;
    }

    @Override
    public void setNextCHandler(Handler handler){
        nextHandler = handler;
    }
}
