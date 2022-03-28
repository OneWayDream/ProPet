import logo from '../../../img/logo.jpg'
import styled from 'styled-components';

const StyledLogo = styled.img(({width, height, border_radius}) => `
  width: ${width ? width : 'auto'};
  height: ${height ? height : 'auto'};
  border-radius: ${border_radius ? border_radius : '0px'};
`)

const Logo = (props) => {
  const href = '/'
  return <a href={href}>
      <StyledLogo src={logo} 
        width={props.width} 
        height={props.height} 
        border_radius={props.border_radius}/>
    </a>
}


export default Logo