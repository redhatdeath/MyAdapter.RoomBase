package ru.shanin.data.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import ru.shanin.data.db_room.entity.RoomEntity;
import ru.shanin.domain.entity.People;
import ru.shanin.domain.entity.PeopleInfo;


public class Mapper {
    public static RoomEntity toRoomEntity(People people) {
        return new RoomEntity(
                people.get_id() + "",
                people.getPeopleInfo().getLastName() + "",
                people.getPeopleInfo().getFirstName() + "",
                people.getPeopleInfo().getSecondName() + "",
                people.getPeopleInfo().getAge() + 0,
                people.getPeopleInfo().getPhone() + "",
                people.getPeopleInfo().getEmail() + "",
                people.getPeopleInfo().getPathToPhoto() + "",
                (new Gson()).toJson(people.getPeopleInfo().getListOfKnowledge()) + ""
        );
    }

    public static People toPeople(RoomEntity roomEntity) {
        return new People(
                roomEntity.get_id(),
                new PeopleInfo(
                        roomEntity.getLastName(),
                        roomEntity.getFirstName() + "",
                        roomEntity.getSecondName() + "",
                        roomEntity.getAge() + 0,
                        roomEntity.getEmail() + "",
                        roomEntity.getPhone() + "",
                        roomEntity.getPathToPhoto() + "",
                        (new Gson()).fromJson(
                                roomEntity.getListOfKnowledge(),
                                new TypeToken<ArrayList<String>>() {
                                }.getType()
                        )));
    }

}
