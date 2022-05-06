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
  const location = useLocation()
  const navigate = useNavigate()

  // if (localStorage.getItem('user')) {
  // navigate(paths.PROFILE)

  // }

  useEffect(() => {
    if (localStorage.getItem('user')) {
      navigate(paths.PROFILE)
    }
  }, [])

  let message = '';
  let showSuccessMessage = false;
  let mail = '';
  if (location.state != null) {
    switch (location.state.action) {
      case 'afterRegistration':
        showSuccessMessage = true;
        mail = location.state.mail;
        console.log(mail)
        message =
          <>
            <div>
              Регистрация прошла успешно
            </div>
          </>
    }
  }

  const [username, setUsername] = useState(mail);
  const [password, setPassword] = useState('');

  const [showMessage, setShowMessage] = useState(showSuccessMessage)

  const dispatch = useDispatch()


  const handleLogin = () => {
    dispatch(authenticate(username, password))
    if (localStorage.getItem('user')) {
      navigate(paths.PROFILE)
    }
  }

  const handleKeypress = (e) => {
    console.log(e.keyCode)
  }

  // useEffect(() => {
  //   const listener = event => {
  //     if (event.code === "Enter" || event.code === "NumpadEnter") {
  //       console.log("Enter key was pressed. Run your function.");
  //       event.preventDefault();
  //       // callMyFunction();
  //     }
  //   };
  //   document.addEventListener("keydown", listener);
  //   return () => {
  //     document.removeEventListener("keydown", listener);
  //   };
  // }, []);

  const body = <>
    <div className="signInContainer">
      <div className="signInMessageSuccess" style={{ display: showMessage ? "block" : "none" }}>
        {message}
      </div>
      <div style={{ fontSize: '2vw', paddingBottom: '5px' }}>
        Войдите в аккаунт
      </div>
      <Input image={ProfilePic} width='2vw' placeholder='Логин' value={username} onChange={setUsername} />
      <Input image={Key} width='2vw' placeholder='Пароль' type='password' onChange={setPassword} />
      <Link to="/forgot" style={{ color: 'black', fontSize: '1.5vw' }}>Я забыл пароль</Link>
      <Button style='orange' width='20vw' onClick={handleLogin}>Войти</Button>
    </div>
    <Link to={paths.SIGN_UP} style={{ textDecoration: 'none' }}><div style={{ color: 'rgb(43, 43, 43)', fontSize: '1.5vw', marginTop: '1.5vw', textAlign: 'center' }}>Нет аккаунта?</div></Link>
  </>
  return <Template body={body} />
}

export default SignInPage;