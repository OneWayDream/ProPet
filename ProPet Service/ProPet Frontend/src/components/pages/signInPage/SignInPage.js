import { useEffect, useState } from "react";
import Button from "../../atoms/button";
import Input from "../../atoms/input";
import Template from "../template";
import MailPic from "../../../img/mail.png"
import Key from "../../../img/key.png"
import { Link, useLocation, useNavigate } from "react-router-dom";
import paths from "../../../configs/paths";
import { authenticate } from "../../../services/auth.service";
import { useDispatch } from "react-redux";
import { regexs } from "../../../configs/regexp";

const SignInPage = (props) => {
  //usefull hooks
  const location = useLocation()
  const navigate = useNavigate()
  const dispatch = useDispatch()

  //const for auth
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  //const for success messages
  const [successMessage, setSuccessMessage] = useState('')

  //const for errors messages on input(invalid email or password) 
  const [errorMessage, setErrorMessage] = useState({})

  //const for errors from server(no user with received login etc)
  const [errorServerMessage, setErrorServerMessage] = useState('')

  //const for waiting server response
  const [isLoadint, setIsLoading] = useState(false)

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
        case 'afterDeleteAccount':
          setSuccessMessage(
            <div>
              Аккаунт удален
            </div>
          )
          break
      }
    }
  }, [])

  //success callback on authenticate()   
  const handleSuccess = () => {
    navigate(paths.PROFILE)
  }

  //actions for click sign in button
  const handleLogin = () => {
    if (validate()) {
      setIsLoading(true)
      // dispatch(authenticate(username, password, handleErrorFromServer, forwardToProfile))
      authenticate(username, password, handleErrorFromServer, handleSuccess)
    }
  }

  //catch errors from server, callback on authenticate()
  const handleErrorFromServer = (response) => {
    switch (response.status) {
      case 456:
        setErrorServerMessage('Неверная почта или пароль')
        break
      case 418:
      case 403:
      default:
        setErrorServerMessage('Что-то пошло не так')
        console.log(response)
        break
    }
    setIsLoading(false)
  }

  //validate date
  const validate = () => {
    let errors = {}
    if (!regexs.passwordRegex.test(password)) {
      errors.password = 'Пароль слишком короткий'
    }

    if (!regexs.emailRegex.test(username)) {
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

      <div className='signInMessageError' style={{ display: errorServerMessage ? "block" : "none" }} >
        {errorServerMessage}
      </div>

      <div style={{ fontSize: '2vw', paddingBottom: '5px' }}>
        Войдите в аккаунт
      </div>
      <div className='signInMessageError' style={{ display: errorMessage.username ? "block" : "none" }} >
        {errorMessage.username}
      </div>
      <Input image={MailPic} width='2vw' placeholder='Почта' value={username} onChange={setUsername} />
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