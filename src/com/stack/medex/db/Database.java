package com.stack.medex.db;

import com.stack.medex.dto.DocktorDto;
import com.stack.medex.dto.PatientDto;
import com.stack.medex.dto.User;
import com.stack.medex.enums.AccountType;
import com.stack.medex.enums.GenderType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Database {
//    ...........................
    public static ArrayList<User> userTable = new ArrayList();
    public static ArrayList<DocktorDto>  doctorTable = new ArrayList<>();
    public static ArrayList<PatientDto>  patientTable  = new ArrayList<>();
//    ............................


    static {
//        ......................
        userTable.add(new User("sachith","rangana","srs@gmail.com",
                "12345", AccountType.PATIENT));
        userTable.add(new User("sr","pr","rs@gmail.com",
                "12345", AccountType.DOCTOR));
//        ......................


//        ...........................
         doctorTable.add(
            new DocktorDto(
                    "sr",
                    "pr",
                    "5477389",
                    "+64577",
                    "rs@gmail.com",
                    "Sample1",
                    "Colombo",
                    GenderType.MALE


            )
         );

//        ..........................



//        ...............................
        try {
            patientTable.add(
                    new PatientDto("95","Hasika","Sandaruwan",
                            new SimpleDateFormat("yyyy-MM-dd").parse("1880-10-16")
                            ,GenderType.MALE,"Galle","hasika@gmail.com"));
            patientTable.add(
                    new PatientDto("124","Samantha","Bandara",
                            new SimpleDateFormat("yyyy-MM-dd").parse("1880-10-16")
                            ,GenderType.MALE,"Aluthgama","samantha@gmail.com"));
            patientTable.add(
                    new PatientDto("452","Namal","Chandana",
                            new SimpleDateFormat("yyyy-MM-dd").parse("1880-10-16")
                            ,GenderType.MALE,"Kalutara","namal@gmail.com"));
            patientTable.add(
                    new PatientDto("457","Wasantha","nihal",
                            new SimpleDateFormat("yyyy-MM-dd").parse("1880-10-16")
                            ,GenderType.MALE,"Wadduwa","wasantha@gmail.com"));
            patientTable.add(
                    new PatientDto("8745","Banda","samanmal",
                            new SimpleDateFormat("yyyy-MM-dd").parse("1880-10-16")
                            ,GenderType.MALE,"Panadura","banda@gmail.com"));
        }catch (Exception e){
            e.printStackTrace();
        }
//        ..............................
    }
}
