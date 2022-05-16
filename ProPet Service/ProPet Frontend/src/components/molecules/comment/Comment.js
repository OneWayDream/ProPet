import ReactStars from "react-rating-stars-component";
import styled from "styled-components"
import profilePic from "../../../img/profile_pic.png";
import Image from "../../atoms/image";
const StyledComment = styled.div(() => `
  display: flex;
  align-items: center;
  padding: 1vw;
  margin: 1vw;
  width: 50%;
  border: 1px solid green;
  border-radius: 50px;
`)

const Comment = (props) => {
  const comment = props.comment
  return <>
    <StyledComment>
      <div>
        <Image image={profilePic} width="10vw" />
      </div>
      <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center', width: '100%' }} >
        <div>
          {comment.login}
        </div>
        <div>
          {comment.review}
        </div>
        <div>
          <ReactStars size={50} 
                    edit={false} 
                    isHalf={true} 
                    value={comment.rate} />
        </div>
      </div>
    </StyledComment>
  </>
}

export default Comment