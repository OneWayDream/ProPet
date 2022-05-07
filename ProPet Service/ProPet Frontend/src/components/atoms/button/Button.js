import styled from "styled-components";

const StyledBytton = styled.button(({color, background, width}) => `
  color: ${color};
  background: ${background};
  width: ${width};
  padding: 1.3vw;
  border-radius: 60px;
  font-size: 1.6vw;
  border: 1px solid black;
  margin-top: 2vw;

  &:hover {
    cursor: pointer;
    background-color: rgb(255,175,35);		
    transition: all .15s;
  }
`)


const Button = (props) => {
  if (props.style === 'orange') {
    return <StyledBytton color="black" background="linear-gradient(90deg, rgba(255,175,35,1) 15%, rgba(255,255,255,0.2) 100%)" width={props.width} onClick={props.onClick}>{props.children}</StyledBytton>
  }
  return <StyledBytton props>{props.children}</StyledBytton>
}

export default Button;