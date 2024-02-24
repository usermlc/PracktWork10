package com.await.bdpw10.rm;

import com.await.bdpw10.da.entity.User;

public interface Handler {
    public boolean validate(User user);
    public void setNextCHandler(Handler handler);
}
