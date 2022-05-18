import styled from "styled-components";

const StyledCheckbox = styled.input.attrs({ type: 'checkbox' })`
  width: 1.1vw;
  height: 1.1vw;
  cursor: pointer;
`

const Checkbox = (props) => {
  return <StyledCheckbox onChange={props.onChange} />
}

export default Checkbox;