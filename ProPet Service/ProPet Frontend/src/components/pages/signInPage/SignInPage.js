import { useState } from "react";
import styled from "styled-components";
import Button from "../../atoms/button";
import Input from "../../atoms/input";
import Template from "../template";
import ProfilePic from "../../../img/profile_pic.png"
import Key from "../../../img/key.png"

const InputWrapper = styled.input((props) => `

`)

const SignInPage = () => {
  const[username, setUsername] = useState('');
  const[password, setPassword] = useState('');
  
  const body = <>
    <form>
      <div className="signInContainer">
        <div style={{ fontSize: '2vw', paddingBottom: '5px' }}>
          Войдите в аккаунт
        </div>
        <Input image={ProfilePic} width='2vw' placeholder='Логин' onChange={setUsername}/>
        <Input image={Key} width='2vw' placeholder='Пароль' type='password' onChange={setPassword}/>
        <a href="/forgot" style={{ color: 'black', fontSize: '1.5vw' }}>Я забыл пароль</a>
        <Button style='orange' width='20vw'>Войти</Button>
      </div>
    </form> 
    <a href="/signUp" style={{ textDecoration: 'none' }}><div style={{ color: 'rgb(43, 43, 43)', fontSize: '1.5vw',  marginTop: '1.5vw', textAlign: 'center' }}>Нет аккаунта?</div></a>
    </>
  return <Template body={body} />
}

export default SignInPage;