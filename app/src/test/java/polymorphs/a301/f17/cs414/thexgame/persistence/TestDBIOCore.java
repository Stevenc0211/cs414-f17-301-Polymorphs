package polymorphs.a301.f17.cs414.thexgame.persistence;

/**
 * Created by Miles on 10/12/2017.
 * This preforms unit test for the DBIOCore class
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;


import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})

public class TestDBIOCore {
    private static String userGName = "test";
    private static String userEmail = "test@email.com";
    private static String username = "tester1";

    private static User user;


    public static UserObserver currentUser = new UserObserver() {
        @Override
        public void userUpdated(User u) {
            user = u;
        }
    };

    private static DatabaseReference mockedDatabaseReference;

    @BeforeClass
    public static void setUpTests() {
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);

        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);
        when(mockedDatabaseReference.push()).thenReturn(mockedDatabaseReference);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                //when(mockedDataSnapshot.getValue(User.class)).thenReturn(testOrMockedUser)

                valueEventListener.onDataChange(mockedDataSnapshot);
                //valueEventListener.onCancelled(...);

                return null;
            }
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                //when(mockedDataSnapshot.getValue(User.class)).thenReturn(testOrMockedUser)

                valueEventListener.onDataChange(mockedDataSnapshot);
                //valueEventListener.onCancelled(...);

                return null;
            }
        }).when(mockedDatabaseReference).addValueEventListener(any(ValueEventListener.class));

        DBIOCore.setDatabase(mockedFirebaseDatabase);
        DBIOCore.setCurrentUser(userGName, userEmail);
        DBIOCore.setCurrentUserUsername(username);
        DBIOCore.registerToCurrentUser(currentUser);

    }

    @Test
    public void testSetUser() {
        assertTrue("ERROR: user failed to be set", user.getName() == userGName && user.getEmail() == userEmail);
    }

    @Test
    public void testSetCurrentUserUsername() {
        assertTrue("ERROR: username failed to be set", user.getNickname() == username);
    }
}
