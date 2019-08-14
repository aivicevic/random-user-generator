package com.randomusers.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.domain.Response
import com.domain.Status
import com.domain.model.user.User
import com.domain.model.user.UsersResponse
import com.domain.repository.UserRepository
import com.domain.scheduler.SchedulerProvider
import com.nhaarman.mockitokotlin2.*
import com.randomusers.TestSchedulerProviderImpl
import com.randomusers.getFakeUser
import com.randomusers.triggerScheduledActions
import com.randomusers.ui.userlist.UserListViewModel
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class UserListViewModelTest {

    @Rule @JvmField val instantExecutorRule = InstantTaskExecutorRule()
    @Rule @JvmField val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock private lateinit var userRepository: UserRepository
    private val schedulerProvider: SchedulerProvider = TestSchedulerProviderImpl(TestScheduler())
    private val userListViewModel by lazy { UserListViewModel(schedulerProvider, userRepository) }

    @Test
    fun `Updating user list updates corresponding live data (success)`() {
        val mockUserResponseObserver: Observer<Response<UsersResponse>> = mock()
        val mockUsersResponse: UsersResponse = mock()

        whenever(userRepository.getUsers(100)) doReturn Single.just(mockUsersResponse)

        userListViewModel.run {
            usersResponseLiveData.observeForever(mockUserResponseObserver)
            updateUserList()
            assert(usersResponseLiveData.value?.status == Status.LOADING)
        }

        triggerScheduledActions(schedulerProvider)
        verify(mockUserResponseObserver, times(2)).onChanged(any())
        assert(userListViewModel.usersResponseLiveData.value?.run {
            status == Status.SUCCESS && data == mockUsersResponse
        } ?: false)
    }

    @Test
    fun `Updating user list updates corresponding live data (error)`() {
        val mockUserResponseObserver: Observer<Response<UsersResponse>> = mock()
        val mockThrowable: Throwable = mock()

        whenever(userRepository.getUsers(100)) doReturn Single.error(mockThrowable)

        userListViewModel.run {
            usersResponseLiveData.observeForever(mockUserResponseObserver)
            updateUserList()
            assert(usersResponseLiveData.value?.status == Status.LOADING)
        }

        triggerScheduledActions(schedulerProvider)
        verify(mockUserResponseObserver, times(2)).onChanged(any())
        assert(userListViewModel.usersResponseLiveData.value?.run {
            status == Status.ERROR && throwable == mockThrowable
        } ?: false)
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
    fun `Toggling favorite when 'isFavorite' is false adds user to local db via userRepository`() {
        val fakeUser: User = getFakeUser(isFavorite = false)

        userListViewModel.toggleFavorite(fakeUser)
        verify(userRepository).saveFavoriteToDb(fakeUser)
    }

    @Test
    fun `Toggling favorite when 'isFavorite' is true removes user from local db via userRepository`() {
        val fakeUser: User = getFakeUser(isFavorite = true)

        userListViewModel.toggleFavorite(fakeUser)
        verify(userRepository).deleteFavoriteFromDb(fakeUser)
    }

    @Test
    fun `Toggling favorite when 'isFavorite' is true updates user list live data`() {
        val mockUserResponseObserver: Observer<Response<UsersResponse>> = mock()
        val fakeUser: User = getFakeUser(isFavorite = true)

        userListViewModel.run {
            usersResponseLiveData.value = Response.success(UsersResponse(listOf(fakeUser)))
            usersResponseLiveData.observeForever(mockUserResponseObserver)
            toggleFavorite(fakeUser)
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