package com.stack.medex.util;

import java.sql.*;

public class IdGenerate {

      public  int generatedId(){
          try {
              ResultSet rst = CrudUtil.executr("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1");
              if(rst.next()){
                  return rst.getInt(1) + 1;
              }


          } catch (ClassNotFoundException | SQLException e) {
              e.printStackTrace();
          }
          return 1;
      }

}
