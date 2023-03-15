package com.example.labshop.mapper;

import com.example.labshop.enumeration.UserRole;
import com.example.labshop.model.UserModel;
import com.example.labshop.shop_db.tables.records.UsersRecord;

public class UserMapper implements Mapper<UsersRecord, UserModel> {

    @Override
    public UsersRecord toRecord(UserModel userModel) {
        UsersRecord record = new UsersRecord();
        record.setId(userModel.getId());
        record.setUserName(userModel.getUsername());
        record.setEmail(userModel.getEmail());
        record.setPassword(userModel.getPassword());
        record.setRole(userModel.getRole().name());
        return record;
    }

    @Override
    public UserModel toModel(UsersRecord usersRecord) {
        return new UserModel(usersRecord.getId(),
                usersRecord.getUserName(),
                usersRecord.getEmail(),
                usersRecord.getPassword(),
                UserRole.valueOf(usersRecord.getRole())
        );
    }
}
