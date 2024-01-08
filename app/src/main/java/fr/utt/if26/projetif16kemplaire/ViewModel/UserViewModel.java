package fr.utt.if26.projetif16kemplaire.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.utt.if26.projetif16kemplaire.Model.Database.UserEntity;
import fr.utt.if26.projetif16kemplaire.Repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository uRepository;

    private final LiveData<List<UserEntity>> allUsers;

    public UserViewModel (Application application) {
        super(application);
        uRepository = new UserRepository(application);
        allUsers = uRepository.getAllUsers();
    }

    public LiveData<List<UserEntity>> getAllUsers() { return allUsers; }

    public UserEntity getUserById(int userId) {
        return uRepository.getUserById(userId);
    }

    public void insert(UserEntity user) { uRepository.insert(user); }

    public void update(UserEntity user) { uRepository.update(user); }

    public void updateInfos(UserEntity user) {
        uRepository.updateInfos(user);
    }

    public boolean checkValidLogin(int userId, String password) {
        return uRepository.isValidAccount(userId, password);
    }

    public void deleteUser(UserEntity user) {
        uRepository.deleteUser(user);
    }

}
