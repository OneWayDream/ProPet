import styled from "styled-components";
import Image from "../image";

const StyledDiv = styled.div((props) => `
  background: rgba(77,77,77, 0.5);
  border: 1px solid black;
  border-radius: 15px;
  padding: 20px;
  margin: 15px;
  width: 27vw;
  display: flex;
`)

const StyledInput = styled.input((props) => `
  margin-left: 10px;
  color: white;
  border: none;
  background: transparent;
  outline: 0;
  font-size: 1.5vw;
  
  &::focus {
    outline: none !important
    border: none;
  }
  
  &::placeholder {
    color: white;
  };
`)

const Input = (props) => {
  const handleChange = (ev) => {
    props.onChange(ev.target.value);
  };
  return <>
    <StyledDiv>
      <Image image={props.image} width={props.width}></Image>
      <StyledInput onChange={handleChange} placeholder={props.placeholder} type={props.type} props={props}>{props.children}</StyledInput>
    </StyledDiv>
  </>

}

export default Input;