import Template from "../template/";
import Image from "../../atoms/image/";
import profilePic from "../../../img/profile_pic.png";
import settingPic from "../../../img/settings_picture.png";
import OutputItem from "../../atoms/outputItem";
import { logout } from "../../../reducers/userReducer";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import paths from "../../../configs/paths";
import { useEffect, useState } from "react";
import { getUser } from "../../../services/user.service";
import api from "../../../configs/api";
import axios from "axios";

const ProfilePage = () => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const [user, setUser] = useState()
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    if (!localStorage.getItem('user')) {
      navigate(paths.SIGN_IN)
    } else {
      const credentials = JSON.parse(localStorage.getItem('user'))
      getUser(credentials.mail, credentials.accessToken).then((response) => {
        setUser(response.data)
        setLoading(false)
      })
    }
  }, [])

  const logOut = () => {
    dispatch(logout())
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
          {/* <a href="/settings"><Image image={settingPic} width="3vw" /></a> */}
        </div>
        <div>
          <div style={{ display: 'flex', height: '100%' }}>
            <div>
              <OutputItem>Логин: {user.login}</OutputItem>
              <OutputItem>Почта: {user.mail}</OutputItem>
              <OutputItem>Город: {user.country}</OutputItem>
              <OutputItem>Номер телефона: +7 (800) 555-35-35</OutputItem>
            </div>
            <div>
              <OutputItem>Имя: Тестовое имя</OutputItem>
              <OutputItem>Фамилия: Тестовая фамилия</OutputItem>
              <OutputItem>Статус сиделки: включен</OutputItem>
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