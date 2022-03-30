import styled from 'styled-components';

const StyledLogo = styled.img(({width, height, border, border_radius}) => `
  display: block;
  width: ${width ? width : 'auto'};
  height: ${height ? height : 'auto'};
  border-radius: ${border_radius ? border_radius : '0px'};
  border: ${border ? border : 'none'};
`)

const Image = (props) => {
  return <StyledLogo 
        src={props.image} 
        width={props.width} 
        height={props.height}
        border={props.border}
        border_radius={props.border_radius}
        />
}


export default Image 