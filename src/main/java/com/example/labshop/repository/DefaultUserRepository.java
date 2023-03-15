package com.example.labshop.repository;

import com.example.labshop.mapper.Mapper;
import com.example.labshop.model.UserModel;
import com.example.labshop.shop_db.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.labshop.shop_db.Tables.USERS;

@Repository
public class DefaultUserRepository implements UserRepository{

    private final DSLContext dslContext;
    private final Mapper<UsersRecord, UserModel> mapper;

    private static final int SUCCESS_INDICATOR = 1;

    public DefaultUserRepository(DSLContext dslContext, Mapper<UsersRecord, UserModel> mapper) {
        this.dslContext = dslContext;
        this.mapper = mapper;
    }

    @Override
    public boolean saveUser(UserModel userModel) {
        return dslContext.insertInto(USERS)
                .set(mapper.toRecord(userModel))
                .execute() == SUCCESS_INDICATOR;
    }

    @Override
    public boolean updateUser(UserModel userModel) {
        return dslContext
                .update(USERS)
                .set(USERS.USER_NAME, userModel.getUsername())
                .set(USERS.EMAIL, userModel.getEmail())
                .set(USERS.PASSWORD, userModel.getPassword())
                .set(USERS.ROLE, userModel.getRole().name())
                .where(USERS.EMAIL.eq(userModel.getEmail()))
                .execute() == SUCCESS_INDICATOR;
    }

    @Override
    public boolean deleteUser(Long userId) {
        return dslContext.deleteFrom(USERS)
                .where(USERS.ID.eq(userId))
                .execute() == SUCCESS_INDICATOR;
    }

    @Override
    public UserModel findUserById(Long userId) {
        UsersRecord record = dslContext.selectFrom(USERS)
                .where(USERS.ID.eq(userId))
                .fetchOneInto(UsersRecord.class);
        return mapper.toModel(record);
    }

    @Override
    public UserModel findUserByEmail(String email) {
        UsersRecord record = dslContext.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOneInto(UsersRecord.class);
        return record != null ? mapper.toModel(record): null;
    }

    @Override
    public UserModel findUserByNickname(String nickname) {
        UsersRecord record = dslContext.selectFrom(USERS)
                .where(USERS.USER_NAME.eq(nickname))
                .fetchOneInto(UsersRecord.class);
        return mapper.toModel(record);
    }

    @Override
    public List<UserModel> findAll() {
        List<UsersRecord> usersRecords = dslContext
                .selectFrom(USERS)
                .fetch();
        return usersRecords
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
