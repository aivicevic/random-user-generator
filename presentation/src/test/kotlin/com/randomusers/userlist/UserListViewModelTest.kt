package com.randomusers.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.domain.Response
import com.domain.Status
import com.domain.model.user.User
import com.domain.model.user.UsersResponse
import com.domain.repository.UserRepository
import com.nhaarman.mockitokotlin2.*
import com.randomusers.getFakeUser
import com.randomusers.rule.CoroutineRule
import com.randomusers.rule.CoroutineTest
import com.randomusers.ui.userlist.ToggleFavoriteStatus
import com.randomusers.ui.userlist.UserListViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@SuppressWarnings("kotlin:S100")
class UserListViewModelTest {

    @Rule @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    @Rule @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
    @Rule @JvmField
    val coroutineRule = CoroutineRule()

    @Mock private lateinit var userRepository: UserRepository
    @InjectMocks private lateinit var userListViewModel: UserListViewModel

    // TODO: Find a way to assert loading status when data calls invoked?

    @Test
    @CoroutineTest
    fun `Updating user list updates corresponding live data (success)`() {
        val mockUserResponseObserver: Observer<Response<UsersResponse>> = mock()
        val mockUsersResponse: UsersResponse = mock()

        runBlockingTest {
            whenever(userRepository.getUsers(100)) doReturn mockUsersResponse
            userListViewModel.run {
                usersResponseLiveData.observeForever(mockUserResponseObserver)
                updateUserList()
                // assert(usersResponseLiveData.value?.status == Status.LOADING)
            }

            verify(mockUserResponseObserver, times(2)).onChanged(any())
            assert(userListViewModel.usersResponseLiveData.value?.run {
                status == Status.SUCCESS && data == mockUsersResponse
            } ?: false)
        }
    }

    @Test
    @CoroutineTest
    fun `Updating user list updates corresponding live data (error)`() {
        val mockUserResponseObserver: Observer<Response<UsersResponse>> = mock()
        val mockThrowable: Throwable = mock()

        runBlockingTest {
            whenever(userRepository.getUsers(100)) doThrow mockThrowable

            userListViewModel.run {
                usersResponseLiveData.observeForever(mockUserResponseObserver)
                updateUserList()
                // assert(usersResponseLiveData.value?.status == Status.LOADING)
            }

            verify(mockUserResponseObserver, times(2)).onChanged(any())
            assert(userListViewModel.usersResponseLiveData.value?.run {
                status == Status.ERROR && throwable == mockThrowable
            } ?: false)
        }
    }

    @Test
    fun `Restoring user list updates corresponding live data with existing user list (data exists)`() {
        val mockUserResponseObserver: Observer<Response<UsersResponse>> = mock()
        val fakeUserList = listOf(getFakeUser(), getFakeUser())
        val mockUsersResponse = UsersResponse(fakeUserList)

        userListViewModel.run {
            usersResponseLiveData.value = Response.success(mockUsersResponse)
            usersResponseLiveData.observeForever(mockUserResponseObserver)
            restoreUserList()
        }

        verify(mockUserResponseObserver, times(2)).onChanged(any())
        assert(userListViewModel.usersResponseLiveData.value?.run {
            status == Status.SUCCESS && data?.results == fakeUserList
        } ?: false)
    }

    @Test
    fun `Restoring user list updates corresponding live data with empty user list (data does not exist)`() {
        val mockUserResponseObserver: Observer<Response<UsersResponse>> = mock()

        userListViewModel.run {
            usersResponseLiveData.observeForever(mockUserResponseObserver)
            restoreUserList()
        }

        verify(mockUserResponseObserver).onChanged(any())
        assert(userListViewModel.usersResponseLiveData.value?.run {
            status == Status.SUCCESS && data?.results?.isEmpty() ?: false
        } ?: false)
    }

    @Test
    @CoroutineTest
    fun `Toggling favorite when 'isFavorite' is false adds user to local db via userRepository`() {
        val fakeUser: User = getFakeUser(isFavorite = false)

        userListViewModel.toggleFavorite(fakeUser)
        verifyBlocking(userRepository) {
            saveFavoriteToDb(fakeUser)
        }
    }

    @Test
    @CoroutineTest
    fun `Toggling favorite when 'isFavorite' is true removes user from local db via userRepository`() {
        val fakeUser: User = getFakeUser(isFavorite = true)

        userListViewModel.toggleFavorite(fakeUser)
        verifyBlocking(userRepository) {
            deleteFavoriteFromDb(fakeUser)
        }
    }

    @Test
    @CoroutineTest
    fun `Toggling favorite updates toggle favorite status live data (success)`() {
        val mockToggleFavoriteStatusObserver: Observer<ToggleFavoriteStatus> = mock()
        val fakeUser: User = getFakeUser(isFavorite = false)

        runBlockingTest {
            userListViewModel.run {
                toggleFavoriteStatusLiveData.observeForever(mockToggleFavoriteStatusObserver)
                toggleFavorite(fakeUser)
            }

            verify(mockToggleFavoriteStatusObserver, times(3)).onChanged(any())
            assert(userListViewModel.toggleFavoriteStatusLiveData.value?.run {
                !hasError && isEnabled
            } ?: false)
        }
    }

    @Test
    @CoroutineTest
    fun `Toggling favorite updates toggle favorite status live data (error)`() {
        val mockToggleFavoriteStatusObserver: Observer<ToggleFavoriteStatus> = mock()
        val mockThrowable: Throwable = mock()
        val fakeUser: User = getFakeUser(isFavorite = false)

        runBlockingTest {
            whenever(userRepository.saveFavoriteToDb(fakeUser)) doThrow mockThrowable

            userListViewModel.run {
                toggleFavoriteStatusLiveData.observeForever(mockToggleFavoriteStatusObserver)
                toggleFavorite(fakeUser)
            }

            verify(mockToggleFavoriteStatusObserver, times(3)).onChanged(any())
            assert(userListViewModel.toggleFavoriteStatusLiveData.value?.run {
                hasError && isEnabled
            } ?: false)
        }
    }

    @Test
    @CoroutineTest
    fun `Toggling favorite when 'isFavorite' is true updates user list live data (favorites list selected)`() {
        val mockUserResponseObserver: Observer<Response<UsersResponse>> = mock()
        val fakeUser: User = getFakeUser(isFavorite = true)

        userListViewModel.run {
            usersResponseLiveData.value = Response.success(UsersResponse(listOf(fakeUser)))
            usersResponseLiveData.observeForever(mockUserResponseObserver)
            toggleFavorite(fakeUser, true)
        }

        verify(mockUserResponseObserver, times(2)).onChanged(any())
    }

    @Test
    fun `Favorites list is populated from local db via userRepository`() {
        val mockFavoritesLiveData: MutableLiveData<List<User>> = mock()

        whenever(userRepository.getFavoritesFromDb()) doReturn mockFavoritesLiveData

        userListViewModel.reinitializeFavoritesList()
        verify(userRepository, atLeastOnce()).getFavoritesFromDb()
    }
}