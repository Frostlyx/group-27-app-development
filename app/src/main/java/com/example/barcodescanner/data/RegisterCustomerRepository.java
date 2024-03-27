package com.example.barcodescanner.data;

import com.example.barcodescanner.data.model.User;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class RegisterCustomerRepository {

    private static volatile RegisterCustomerRepository instance;

    private RegisterCustomerDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private RegisterCustomerRepository(RegisterCustomerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegisterCustomerRepository getInstance(RegisterCustomerDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterCustomerRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<User> register(String username, String password) {
        // handle register
        Result<User> result = dataSource.register(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<User>) result).getData());
        }
        return result;
    }
}