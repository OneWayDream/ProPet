import { useEffect, useState } from "react";
import Template from "../template";
import Input from "../../atoms/input";
import ProfilePic from "../../../img/profile_pic.png"
import Key from "../../../img/key.png"
import Mail from "../../../img/mail.png"
import Button from "../../atoms/button";
import Checkbox from "../../atoms/checkbox/";
import { Link, useNavigate } from "react-router-dom";
import { registration } from "../../../services/auth.service";
import paths from "../../../configs/paths";
import { regexs } from "../../../configs/regexp";

const SignUpPage = () => {
  //Some usefull hooks
  const navigate = useNavigate()

  //const fields for registration
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [mail, setMail] = useState('');
  const [repeatedPassword, setRepeatedPassword] = useState('');
  const [checkPolicy, setCheckPolicy] = useState(false)

  //const for error messages in input (invalid email, short password etc)
  const [errorMessage, setErrorMessage] = useState({})

  //const for error messages from server (mail already in use, login already in use etc)
  const [errorServerMessage, setErrorServerMessage] = useState('')

  //const to show for waiting response from server 
  const [isLoading, setIsLoading] = useState(false)

  //set on startup
  useEffect(() => {
    if (localStorage.getItem('token')) {
      navigate(paths.PROFILE)
    }
  }, [])


  const handleRegister = () => {
    if (validate()) {
      setIsLoading(true)
      registration(login, mail, password, repeatedPassword, 'Казань', handleErrorRegister, handleSuccessRegister)
    }
  }

  //If register success
  const handleSuccessRegister = (response) => {
    navigate(paths.SIGN_IN, {
      state: {
        action: 'afterRegistration',
        mail: mail
      }
    })
  }

  //If server send errors
  const handleErrorRegister = (response) => {
    switch (response.status) {
      case 456:
        setErrorServerMessage('Такой логин уже используется')
        break
      case 457:
        setErrorServerMessage('Такая почта уже используется')
        break
      case 453:
        setErrorServerMessage('Пароли не совпадают')
        break
      case 502:
      case 403:
      case 418:
      default:
        setErrorServerMessage('Что-то пошло не так')
        console.log(response)
        break
    }
  }

  //validate date before send to server
  const validate = () => {
    let errors = {}
    if (!regexs.loginRegex.test(login)) {
      errors.login = 'Неверный логин'
    }

    if (!regexs.emailRegex.test(mail)) {
      errors.mail = 'Неправильная почта'
    }

    if (password.length < 5) {
      errors.password = 'Пароль слишком короткий'
    }

    if (repeatedPassword !== password) {
      errors.repeatedPassword = 'Пароли не совпадают'
    }

    if (!checkPolicy) {
      errors.checkPolicy = 'Нужно принять политику конфиденциальности'
    }

    setErrorMessage(errors)
    return isEmpty(errors)
  }

  //check if object is empty
  const isEmpty = (object) => {
    return Object.keys(object).length == 0
  }


  const body = <>
    <div className="signUpContainer">
      {/* <div className="signUpMessageError" style={{ display: errorMessage != '' ? "block" : "none" }}>
        {errorMessage}
      </div> */}
      <div className='signInMessageError' style={{ display: errorServerMessage ? "block" : "none" }} >
        {errorServerMessage}
      </div>
      <div style={{ fontSize: '2vw', paddingBottom: '5px' }}>Регистрация</div>
      <div style={{ display: 'flex' }}>
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <div className='signInMessageError' style={{ display: errorMessage.login ? "block" : "none", width: '20vw' }} >
            {errorMessage.login}
          </div>
          <Input image={ProfilePic} width='2vw' placeholder='Логин' onChange={setLogin} validate={'[A-Za-z\d.-]{0,19}'} />
          <div className='signInMessageError' style={{ display: errorMessage.mail ? "block" : "none" }} >
            {errorMessage.mail}
          </div>
          <Input image={Mail} width='2vw' placeholder='Электронная почта' onChange={setMail} />
        </div>
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          <div className='signInMessageError' style={{ display: errorMessage.password ? "block" : "none" }} >
            {errorMessage.password}
          </div>
          <Input type='password' image={Key} width='2vw' placeholder='Пароль' onChange={setPassword} />
          <div className='signInMessageError' style={{ display: errorMessage.repeatedPassword ? "block" : "none" }} >
            {errorMessage.repeatedPassword}
          </div>
          <Input type='password' image={Key} width='2vw' placeholder='Повторите пароль' onChange={setRepeatedPassword} />
        </div>
      </div>
      <div style={{ fontSize: '1.3vw' }}>
        <div className='signInMessageError' style={{ display: errorMessage.checkPolicy ? "block" : "none" }} >
          {errorMessage.checkPolicy}
        </div>
        <div style={{ marginBottom: '0.5vw' }}>
          <Checkbox onChange={(e) => { setCheckPolicy(e.target.checked) }} /><label>Согласен с <Link to="/policy">политикой конфиденциальности</Link></label>
        </div>
      </div>
      <Button style='orange' width='20vw' onClick={handleRegister}>Зарегистрироваться</Button>
    </div>
    <Link to="/signIn" style={{ textDecoration: 'none' }}><div style={{ color: 'rgb(43, 43, 43)', fontSize: '1.5vw', marginTop: '1.5vw', textAlign: 'center' }}>Уже зарегистрированы?</div></Link>
  </>

  return <Template body={body} />
}

export default SignUpPage;