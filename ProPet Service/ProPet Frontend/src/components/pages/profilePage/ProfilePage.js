import Template from "../template/";
import Image from "../../atoms/image/";
import profilePic from "../../../img/profile_pic.png";
import settingPic from "../../../img/settings_picture.png";
import OutputItem from "../../atoms/outputItem";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import paths from "../../../configs/paths";
import { useEffect, useState } from "react";
import { getUser, getUserCredentials, isAuthenticated } from "../../../services/user.service";
import { logout } from "../../../services/auth.service";
import api from "../../../configs/api";
import axios from "axios";

const ProfilePage = () => {
  //Some usefull hooks
  const dispatch = useDispatch()
  const navigate = useNavigate()

  //const for user info
  const [user, setUser] = useState()

  //const for waiting server response
  const [loading, setLoading] = useState(true)

  //initial setup
  useEffect(() => {
    if (!isAuthenticated()) {
      navigate(paths.SIGN_IN)
    } else {
      const credentials = getUserCredentials()
      getUser(credentials.mail, credentials.accessToken, handleError, handleSuccess)
    }
  }, [])

  //if response is success
  const handleSuccess = (response) => {
    setUser(response.data)
    setLoading(false)
  }

  //if catch errors from server
  const handleError = (error) => {
    alert('Что-то пошло не так')
    console.log(error)
  }

  //logout
  const logOut = () => {
    // dispatch(logout())
    logout()
    navigate(paths.WELCOME)
  }

  const body = loading === false ?
    <div className="profileContainer">
      <div style={{ display: 'flex' }}>
        <div>
          <Image image={profilePic} width="15vw" />
        </div>
        <div style={{ padding: '0 1vw' }}>
          <Link to={paths.PROFILE_EDIT}><Image image={settingPic} width="3vw" /></Link>
        </div>
        <div>
          <div style={{ display: 'flex', height: '100%' }}>
            <div>
              <OutputItem>Логин: {user.login}</OutputItem>
              <OutputItem>Почта: {user.mail}</OutputItem>
              <OutputItem>Город: {user.city}</OutputItem>
              <OutputItem>Номер телефона: {user.phone ? user.phone : 'Не указан'}</OutputItem>
            </div>
            <div>
              <OutputItem>Имя: {user.name ? user.name : 'Не указано'}</OutputItem>
              <OutputItem>Фамилия: {user.surname ? user.surname : 'Не указано'}</OutputItem>
              <OutputItem>Статус сиделки: {user.sitterInfoDto.sitterStatus ? 'включен' : 'выключен'}</OutputItem>
            </div>
          </div>
          <hr style={{ height: '1px', background: '#333', border: 'none', backgroundImage: 'linear-gradient(to right, #ccc, #333, #ccc)', margin: '0' }} />
        </div>
      </div>
      <button onClick={logOut}>Logout</button>
    </div>
    :
    <div>
      loading....
    </div>

  return (
    <Template body={body} />
  )
}


export default ProfilePage;