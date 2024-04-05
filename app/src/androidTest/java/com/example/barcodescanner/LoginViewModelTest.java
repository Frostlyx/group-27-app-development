package com.example.barcodescanner;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import androidx.lifecycle.Observer;

import com.example.barcodescanner.ui.login.LoginFormState;
import com.example.barcodescanner.ui.login.LoginViewModel;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.mockito.Mock;

public class LoginViewModelTest extends TestCase {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private Observer<LoginFormState> observer;

    private LoginViewModel loginViewModel;

    @Before
    public void setUp() {
        loginViewModel = new LoginViewModel();
    }

    public void testGetFormErrors() {
        Integer[] errors = loginViewModel.getFormErrors("username", "Password");
        assertNull(errors[0]);
        assertNull(errors[1]);
        errors = loginViewModel.getFormErrors("", "");
        assertNotNull(errors[0]);
        assertNotNull(errors[1]);
    }
}