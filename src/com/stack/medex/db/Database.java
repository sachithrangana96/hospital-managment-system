package com.stack.medex.db;

import com.stack.medex.dto.UserDto;
import com.stack.medex.enums.AccountType;

import java.util.ArrayList;

public class Database {
//    ...........................
    public static ArrayList<UserDto> userTable = new ArrayList();
//    ............................


    static {
//        ......................
        userTable.add(new UserDto("sachith","rangana","srs@gmail.com",
                "12345", AccountType.PATIENT));
        userTable.add(new UserDto("sr","pr","rs@gmail.com",
                "12345", AccountType.DOCKTER));
//        ......................
    }
}
