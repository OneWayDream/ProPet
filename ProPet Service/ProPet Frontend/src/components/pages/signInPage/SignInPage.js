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
  
  const body =
    <form>
      <div className="signInContainer">
        <div style={{ fontSize: '2vw', paddingBottom: '5px' }}>
          Have an account?
        </div>
        <Input image={ProfilePic} width='2vw' placeholder='Username' onChange={setUsername}/>
        <Input image={Key} width='2vw' placeholder='Password' type='password' onChange={setPassword}/>
        <a href="/forgot" style={{ color: 'black', fontSize: '1.5vw' }}>I forgot my password</a>
        <Button style='orange' width='20vw'>Sign In</Button>
      </div>
    </form> 
  return <Template body={body} />
}

export default SignInPage;