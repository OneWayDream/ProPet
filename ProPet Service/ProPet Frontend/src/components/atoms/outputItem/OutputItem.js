import styled from "styled-components";

const StyledOutputItem = styled.div(() => `
  background: rgba(255,255,255,0.8);
  border-radius: 50px;
  font-size: 1.1vw;
  width: 20vw;
  padding: 15px;
  text-align: center;
  margin-right: 1vw;
  margin-bottom: 1.5vw;
  min-height: 1.2vw;
`)

const OutputItem = (props) => {
  return <StyledOutputItem>{props.children}</StyledOutputItem>
}

export default OutputItem;