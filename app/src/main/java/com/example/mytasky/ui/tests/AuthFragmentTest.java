//package com.example.mytasky.ui.tests;
//
//import androidx.fragment.app.testing.FragmentScenario;
//import androidx.test.espresso.matcher.ViewMatchers;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.LargeTest;
//
//import com.example.mytasky.R;
//import com.example.mytasky.data.database.entity.UsersLogin;
//import com.example.mytasky.ui.fragments.AuthFragment;
//import com.example.mytasky.ui.stateholders.viewModels.AuthViewModel;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//
//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.action.ViewActions.click;
//import static androidx.test.espresso.action.ViewActions.replaceText;
//import static androidx.test.espresso.assertion.ViewAssertions.matches;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class AuthFragmentTest {
//
//    @Mock
//    AuthViewModel viewModel;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testLoginAccount_success() {
//        Mockito.when(viewModel.loginAccount(anyString(), anyString())).thenReturn(true);
//
//        FragmentScenario<AuthFragment> scenario = FragmentScenario.launchInContainer(AuthFragment.class);
//        scenario.onFragment(fragment -> {
//            fragment.viewModel = viewModel;
//        });
//
//        onView(withId(R.id.Login_text))
//                .perform(replaceText("test_user"));
//
//        onView(withId(R.id.Password_text))
//                .perform(replaceText("test_password"));
//
//        onView(withId(R.id.Login_button))
//                .perform(click());
//
//        onView(withId(R.id.menu_fragment_container))
//                .check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testLoginAccount_failure() {
//        Mockito.when(viewModel.loginAccount(anyString(), anyString())).thenReturn(false);
//
//        FragmentScenario<AuthFragment> scenario = FragmentScenario.launchInContainer(AuthFragment.class);
//        scenario.onFragment(fragment -> {
//            fragment.viewModel = viewModel;
//        });
//
//        onView(withId(R.id.Login_text))
//                .perform(replaceText("test_user"));
//
//        onView(withId(R.id.Password_text))
//                .perform(replaceText("test_password"));
//
//        onView(withId(R.id.Login_button))
//                .perform(click());
//
//        onView(withId(R.id.error_message))
//                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
//    }
//}