import styled from "styled-components";

const StyledBytton = styled.button(({ color, background, width, fontSize, hoverBackgroundColor }) => `
  color: ${color};
  background: ${background};
  width: ${width};
  padding: 1.3vw;
  border-radius: 60px;
  font-size: ${fontSize ? fontSize : '1.6vw'};
  border: 1px solid black;
  margin-top: 2vw;

  &:hover {
    cursor: pointer;
    background-color: ${hoverBackgroundColor};		
    transition: all .15s;
  }
`)


const Button = (props) => {
  if (props.style === 'orange') {
    return <StyledBytton color="black" 
                    background="linear-gradient(90deg, rgba(255,175,35,1) 15%, rgba(255,255,255,0.2) 100%)" 
                    width={props.width} 
                    onClick={props.onClick} 
                    fontSize={props.fontSize}
                    hoverBackgroundColor='rgb(255,175,35)'>{props.children}</StyledBytton>
  }
  if (props.style === 'red') {
    return <StyledBytton color="black" 
                    background="linear-gradient(90deg, rgba(255,0,0,1) 15%, rgba(249,179,179,0.2) 100%);" 
                    width={props.width} 
                    onClick={props.onClick} 
                    fontSize={props.fontSize} 
                    hoverBackgroundColor='red'>{props.children}</StyledBytton>
  }
  return <StyledBytton props>{props.children}</StyledBytton>
}

export default Button;