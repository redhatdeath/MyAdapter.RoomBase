package ru.shanin.app;

import android.app.Application;

import ru.shanin.data.db_room.database.LocalDatabase;
import ru.shanin.data.repositoryImpl.PeopleRoomRepositoryImpl;
import ru.shanin.domain.repository.PeopleDomainRepository;
import ru.shanin.domain.usecases.PeopleAddNewUseCases;
import ru.shanin.domain.usecases.PeopleDeleteByIdUseCase;
import ru.shanin.domain.usecases.PeopleGetByAllUseCase;
import ru.shanin.domain.usecases.PeopleGetByIdUseCase;

public class AppStart extends Application {

    private PeopleDomainRepository impl;
    private PeopleGetByIdUseCase getById;
    private PeopleAddNewUseCases addNew;
    private PeopleDeleteByIdUseCase delete;
    private PeopleGetByAllUseCase getAll;

    public PeopleGetByIdUseCase getGetById() {
        return getById;
    }

    public PeopleAddNewUseCases getAddNew() {
        return addNew;
    }

    public PeopleDeleteByIdUseCase getDelete() {
        return delete;
    }

    public PeopleGetByAllUseCase getGetAll() {
        return getAll;
    }

    private static AppStart instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        impl = setupImpl();
        setupUseCases(impl);
    }

    private PeopleDomainRepository setupImpl() {
        // work with local database records
        LocalDatabase database = LocalDatabase.getInstance(this);
        return new PeopleRoomRepositoryImpl(database.roomEntityDao());

        // work with temp records
        //return new PeopleArrayListRepositoryImpl();
    }

    private void setupUseCases(PeopleDomainRepository impl) {
        getById = new PeopleGetByIdUseCase(impl);
        addNew = new PeopleAddNewUseCases(impl);
        delete = new PeopleDeleteByIdUseCase(impl);
        getAll = new PeopleGetByAllUseCase(impl);
    }


    public static AppStart getInstance() {
        return instance;
    }
}
