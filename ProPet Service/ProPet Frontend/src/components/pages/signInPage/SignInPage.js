import { useEffect, useState } from "react";
import styled from "styled-components";
import Button from "../../atoms/button";
import Input from "../../atoms/input";
import Template from "../template";
import ProfilePic from "../../../img/profile_pic.png"
import Key from "../../../img/key.png"
import { Link, useLocation, useNavigate } from "react-router-dom";
import paths from "../../../configs/paths";
import { authenticate } from "../../../services/auth.service";
import { useDispatch, useSelector } from "react-redux";

const SignInPage = (props) => {
  // const handleKeypress = (e) => {
  //   console.log(e.keyCode)
  // }


  //usefull hooks
  const location = useLocation()
  const navigate = useNavigate()
  const dispatch = useDispatch()

  //const for auth
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  //const for success messages
  const [successMessage, setSuccessMessage] = useState('')

  //const for errors messages
  const [errorMessage, setErrorMessage] = useState({})

  //set on startup
  useEffect(() => {
    //check if user is authenticated
    if (localStorage.getItem('user')) {
      navigate(paths.PROFILE)
    }
    //check if user after registration to show success register message
    if (location.state != null) {
      switch (location.state.action) {
        case 'afterRegistration':
          setSuccessMessage(
            <div>
              Регистрация прошла успешно
            </div>
          )
          setUsername(location.state.mail)
          break;
      }
    }

    //Add enter key press listener
    // const listener = (e) => {
    //   if (e.code === "Enter") {
    //     e.preventDefault();
    //     console.log(username)
    //     handleLogin();
    //   }
    // };
    // document.addEventListener("keydown", listener);
    // return () => {
    //   document.removeEventListener("keydown", listener);
    // };
  }, [])

  //actions for click sign in button
  const handleLogin = () => {
    if (validate()) {
      dispatch(authenticate(username, password))
    }
  }

  //validate date
  const validate = () => {
    let errors = {}
    if (password.length < 5) {
      errors.password = 'Пароль слишком короткий'
    }

    const usernameRegex = RegExp('^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z]+$')
    if (!usernameRegex.test(username)) {
      errors.username = 'Неправильная почта'
    }
    setErrorMessage(errors)
    return isEmpty(errors)
  }

  //check if object is empty
  const isEmpty = (object) => {
    return Object.keys(object).length == 0
  }


  const body = <>
    <div className="signInContainer">
      <div className='signInMessageSuccess' style={{ display: successMessage ? "block" : "none" }} >
        {successMessage}
      </div>
      <div style={{ fontSize: '2vw', paddingBottom: '5px' }}>
        Войдите в аккаунт
      </div>
      <div className='signInMessageError' style={{ display: errorMessage.username ? "block" : "none" }} >
        {errorMessage.username}
      </div>
      <Input image={ProfilePic} width='2vw' placeholder='Логин' value={username} onChange={setUsername} />
      <div className='signInMessageError' style={{ display: errorMessage.password ? "block" : "none" }} >
        {errorMessage.password}
      </div>
      <Input image={Key} width='2vw' placeholder='Пароль' type='password' onChange={setPassword} />
      <Link to="/forgot" style={{ color: 'black', fontSize: '1.5vw' }}>Я забыл пароль</Link>
      <Button style='orange' width='20vw' onClick={handleLogin}>Войти</Button>
    </div>
    <Link to={paths.SIGN_UP} style={{ textDecoration: 'none' }}><div style={{ color: 'rgb(43, 43, 43)', fontSize: '1.5vw', marginTop: '1.5vw', textAlign: 'center' }}>Нет аккаунта?</div></Link>
  </>
  return <Template body={body} />
}

export default SignInPage;