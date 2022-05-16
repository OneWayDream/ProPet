import styled from "styled-components";

const StyledTextArea = styled.textarea(() => `
  background: rgba(255,255,255,0.8);
  border-radius: 25px;
  font-size: 1.1vw;
  padding: 1.3vw;
  text-align: center;
  margin-right: 1vw;
  margin-bottom: 1.5vw;
  min-height: 1.2vw;
  resize: none;
  &:hover {
    cursor: default;
  }
`)

const TextArea = (props) => {
  return <StyledTextArea readOnly={props.readOnly} cols={props.cols} rows={props.rows} defaultValue={props.children} />
}

export default TextArea