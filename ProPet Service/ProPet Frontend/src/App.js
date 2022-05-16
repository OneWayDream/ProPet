import './App.css';
import './css/'
import MainPage from './components/pages/mainPage';
import ProfilePage from './components/pages/profilePage';
import SignInPage from './components/pages/signInPage/';
import SignUpPage from './components/pages/signUpPage';
import SearchPage from './components/pages/searchPage';
import ProfileEditPage from './components/pages/profileEditPage';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import paths from './configs/paths';
import { Provider } from 'react-redux';
import { store } from './reducers';
import SitterProfilePate from './components/pages/sitterProfilePage/SitterProfilePage';
import NotFoundElement from './components/pages/notFoundElement/NotFoundElement';


function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Routes>
          <Route path='*' element={<NotFoundElement />} />

          <Route path={paths.DEFAULT} element={<MainPage />} />
          <Route path={paths.SIGN_IN} element={<SignInPage />} />
          <Route path={paths.SIGN_UP} element={<SignUpPage />} />
          <Route path={paths.SEARCH} element={<SearchPage />} />
          <Route path={paths.PROFILE} element={<ProfilePage />} />
          <Route path={paths.PROFILE_EDIT} element={<ProfileEditPage />} />
          <Route path={paths.SITTER_PROFILE + '/:sitterId'} element={<SitterProfilePate />} />
        </Routes>
      </BrowserRouter>
    </Provider>
  );
}

export default App;
