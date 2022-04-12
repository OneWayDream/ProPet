import { useState } from "react";
import Template from "../template";
import Input from "../../atoms/input";
import ProfilePic from "../../../img/profile_pic.png"
import Key from "../../../img/key.png"
import Mail from "../../../img/mail.png"
import Button from "../../atoms/button";
import Checkbox from "../../atoms/checkbox/";

const SignUpPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [mail, setMail] = useState('');

  const body = <>
    <form>
      <div className="signUpContainer">
        <div style={{ fontSize: '2vw', paddingBottom: '5px' }}>Регистрация</div>
        <div style={{ display: 'flex' }}>
          <div>
            <Input image={ProfilePic} width='2vw' placeholder='Логин' onChange={setUsername} />
            <Input image={Mail} width='2vw' placeholder='Электронная почта' onChange={setUsername} />
          </div>
          <div>
            <Input image={Key} width='2vw' placeholder='Пароль' onChange={setUsername} />
            <Input image={Key} width='2vw' placeholder='Повторите пароль' onChange={setUsername} />
          </div>
        </div>
        <div style={{ fontSize: '1.3vw' }}>
          <div style={{ marginBottom: '0.5vw' }}>
            <Checkbox/><label>Согласен с <a href="/policy">политикой конфиденциальности</a></label>
          </div>
          <div>
            <Checkbox/><label>Согласен получать новости и обновления ProPet на электронную почту</label>
          </div>
        </div>
        <Button style='orange' width='20vw'>Войти</Button>
      </div>
    </form>
    <a href="/signIn" style={{ textDecoration: 'none' }}><div style={{ color: 'rgb(43, 43, 43)', fontSize: '1.5vw', marginTop: '1.5vw', textAlign: 'center' }}>Уже зарегистрированы?</div></a>
  </>

  return <Template body={body} />
}

export default SignUpPage;