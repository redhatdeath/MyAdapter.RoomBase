package ru.shanin.data.repositoryImpl;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ru.shanin.data.db_room.dao.RoomEntityDao;
import ru.shanin.data.db_room.entity.RoomEntity;
import ru.shanin.data.mapper.Mapper;
import ru.shanin.domain.entity.People;
import ru.shanin.domain.repository.PeopleDomainRepository;


public class PeopleRoomRepositoryImpl implements PeopleDomainRepository {
    private final RoomEntityDao roomEntityDao;
    private final MutableLiveData<ArrayList<People>> peoplesListLiveData;
    private final ArrayList<People> peopleArrayList;
    private final MutableLiveData<People> peopleLiveData;

    public PeopleRoomRepositoryImpl(
            RoomEntityDao roomEntityDao
    ) {
        this.roomEntityDao = roomEntityDao;
        peoplesListLiveData = new MutableLiveData<>();
        peopleArrayList = new ArrayList<>();
        peopleLiveData = new MutableLiveData<>();
        updatePeopleListAsyncTask();
    }


    @Override
    public void peopleAddNew(People people) {
        AsyncTask.execute(() -> {
            RoomEntity roomEntity = Mapper.toRoomEntity(people);
            synchronized (roomEntityDao) {
                roomEntityDao.RoomEntityAddNew(roomEntity);
            }
        });
        updatePeopleListAsyncTask();
    }

    @Override
    public void peopleDeleteById(People people) {
        AsyncTask.execute(() -> {
            synchronized (roomEntityDao) {
                roomEntityDao.RoomEntityDeleteById(people.get_id());
            }
        });
        updatePeopleListAsyncTask();
    }

    @Override
    public void peopleEditById(People people) {
        AsyncTask.execute(() -> {
            People people_new = new People(people.getPeopleInfo());
            RoomEntity roomEntity_new = Mapper.toRoomEntity(people_new);
            synchronized (roomEntityDao) {
                roomEntityDao.RoomEntityDeleteById(people.get_id());
                roomEntityDao.RoomEntityAddNew(roomEntity_new);
            }
        });
        updatePeopleListAsyncTask();
    }

    private void updatePeopleListAsyncTask() {
        AsyncTask.execute(() -> {
            List<RoomEntity> roomPeopleData = roomEntityDao.RoomEntityGetAll();
            for (RoomEntity roomPeople : roomPeopleData)
                peopleArrayList.add(Mapper.toPeople(roomPeople));
            peoplesListLiveData.postValue(new ArrayList<>(peopleArrayList));
            peopleArrayList.clear();
        });
    }

    private void findPeopleById(String _id) {
        AsyncTask.execute(() -> {
            RoomEntity roomEntity;
            synchronized (roomEntityDao) {
                roomEntity = roomEntityDao.RoomEntityGetById(_id);
            }
            peopleLiveData.postValue(Mapper.toPeople(roomEntity));
        });
    }

    @Override
    public MutableLiveData<ArrayList<People>> peopleGetAll() {
        return peoplesListLiveData;
    }

    @Override
    public MutableLiveData<People> peopleGetById(String _id) {
        findPeopleById(_id);
        return peopleLiveData;
    }
}
