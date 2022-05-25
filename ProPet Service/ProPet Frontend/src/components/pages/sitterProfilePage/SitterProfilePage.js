import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import { getAccessToken, getFullUserInfo, getUser } from "../../../services/user.service"
import Image from "../../atoms/image"
import OutputItem from "../../atoms/outputItem/OutputItem"
import Template from "../template"
import profilePic from "../../../img/profile_pic.png";
import ReactStars from "react-rating-stars-component";
import TextArea from "../../atoms/textArea/TextArea"
import Comment from "../../molecules/comment/Comment"
import paths from "../../../configs/paths"

const SitterProfilePate = (props) => {
  const navigate = useNavigate()
  const { sitterId } = useParams()
  const [sitter, setSitter] = useState({})
  const [isLoading, setIsLoading] = useState(true)

  const [comments, setComments] = useState([])

  useEffect(() => {
    getFullUserInfo(sitterId, getAccessToken(), handleError, handleSuccessGetUser, "id")
  }, [])

  const handleError = (error) => {
    if (error.status) {
      navigate(paths.NOT_FOUND)
    } else {
      alert('Что-то пошло не так')
      console.log("Error: " + JSON.stringify(error))
      setIsLoading(false)
    }

  }

  let rowsFotAboutTextarea
  const handleSuccessGetUser = (response) => {
    setSitter(response.sitter)
    setComments(response.comments)
    rowsFotAboutTextarea = response.sitter.sitterInfoDto.infoAbout ? Math.ceil(response.sitter.sitterInfoDto.infoAbout.length / 80) : 0
    setIsLoading(false)
  }

  const body = isLoading === false ?
    <div className="profileContainer">
      <div>
        <div style={{ display: 'flex' }}>
          <div>
            <Image image={profilePic} width="15vw" />
          </div>
          <div>
            <div style={{ height: '100%' }}>
              <div style={{ display: 'flex' }}>
                <div>
                  <OutputItem>Имя: {sitter.name ? sitter.name : 'Не указано'}</OutputItem>
                  <OutputItem>Город: {sitter.city}</OutputItem>
                </div>
                <div>
                  <OutputItem>Фамилия: {sitter.surname ? sitter.surname : 'Не указана'}</OutputItem>
                  <OutputItem>Возраст: {sitter.age ? sitter.age : 'Не указан'}</OutputItem>
                </div>
              </div>
              <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-evenly', fontSize: '1.3vw' }} >
                Рейтинг:  <ReactStars size={50} edit={false} isHalf={true} value={sitter.sitterInfoDto.rating} /> Отзывов: {sitter.sitterInfoDto.rating}
              </div>
            </div>
            <hr style={{ height: '1px', background: '#333', border: 'none', backgroundImage: 'linear-gradient(to right, #ccc, #333, #ccc)', margin: '0' }} />
          </div>
        </div>
      </div>
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', paddingTop: '2vw', width: '100%' }}>
        <div style={{ paddingBottom: '1vw', fontSize: '1.5vw' }}>О себе:</div>
        <TextArea readOnly={true} cols={"80"} rows={rowsFotAboutTextarea}>{sitter.sitterInfoDto.infoAbout}</TextArea>
      </div>
      {comments.length > 0 ?
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }} >
          <div>Комменарии:</div>
          {comments.map(comment => {
            return (<Comment comment={comment} key={comment.id} />)
          })}
        </div>
        :
        ''}
    </div>
    :
    <div>
      loading....
    </div>

  return (
    <Template body={body} />
  )
}

export default SitterProfilePate