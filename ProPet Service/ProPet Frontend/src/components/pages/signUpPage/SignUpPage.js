import { useState } from "react";
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

const SignUpPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [mail, setMail] = useState('');
  const [repeatedPassword, setRepeatedPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('')

  const navigate = useNavigate()

  if (localStorage.getItem('token')) {
    navigate(paths.PROFILE)
  }

  const handleRegister = async () => {
    if (validate()) {
      let isLoading = true;
      const response = await registration(username, mail, password, repeatedPassword, 'Казань').then((response) => {
        navigate(paths.SIGN_IN, {
          state: {
        action: 'afterRegistration',
        mail: mail
          }
        })
      }).catch(e => {
        if (e.response) {
          console.log(e.response.data)
          console.log(e.response.status)
          console.log(e.response.headers)
        }
      }).finally(() => {
        isLoading = false;
      });
    }
  }

  const validate = () => {
    const validLogin = new RegExp('[A-Za-z\d.-]{0,19}');
    const validEmail = new RegExp('/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/');

    // console.log(validEmail.test(mail))
    // setErrorMessage('test')
    return true
  }

  const body = <>
    <div className="signUpContainer">
      <div className="signUpMessageError" style={{ display: errorMessage != '' ? "block" : "none" }}>
        {errorMessage}
      </div>
      <div style={{ fontSize: '2vw', paddingBottom: '5px' }}>Регистрация</div>
      <div style={{ display: 'flex' }}>
        <div>
          <Input image={ProfilePic} width='2vw' placeholder='Логин' onChange={setUsername} validate={'[A-Za-z\d.-]{0,19}'} />
          <Input image={Mail} width='2vw' placeholder='Электронная почта' onChange={setMail} />
        </div>
        <div>
          <Input type='password' image={Key} width='2vw' placeholder='Пароль' onChange={setPassword} />
          <Input type='password' image={Key} width='2vw' placeholder='Повторите пароль' onChange={setRepeatedPassword} />
        </div>
      </div>
      <div style={{ fontSize: '1.3vw' }}>
        <div style={{ marginBottom: '0.5vw' }}>
          <Checkbox /><label>Согласен с <Link to="/policy">политикой конфиденциальности</Link></label>
        </div>
        {/* <div>
          <Checkbox /><label>Согласен получать новости и обновления ProPet на электронную почту</label>
        </div> */}
      </div>
      <Button style='orange' width='20vw' onClick={handleRegister}>Зарегистрироваться</Button>
    </div>
    <Link to="/signIn" style={{ textDecoration: 'none' }}><div style={{ color: 'rgb(43, 43, 43)', fontSize: '1.5vw', marginTop: '1.5vw', textAlign: 'center' }}>Уже зарегистрированы?</div></Link>
  </>

  return <Template body={body} />
}

export default SignUpPage;