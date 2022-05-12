import Template from "../template";
import Image from "../../atoms/image";
import profilePic from "../../../img/profile_pic.png";
import settingPic from "../../../img/settings_picture.png";
import OutputItem from "../../atoms/outputItem";
import { logout } from "../../../reducers/userReducer";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import paths from "../../../configs/paths";
import { useEffect, useState } from "react";
import { getUser } from "../../../services/user.service";
import api from "../../../configs/api";
import axios from "axios";
import Input from "../../atoms/input";

const ProfileEditPage = () => {
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
        console.log(user)
        setLoading(false)
      })

    }
  }, [])

  const logOut = () => {
    dispatch(logout())
    navigate(paths.WELCOME)
  }

  const handleInput = (e) => {
    // console.log(e.target.name, " : ", e.target.value);
    if (e.target.type === 'checkbox') {
      setUser({ ...user, [e.target.name]: e.target.checked})
    } else {
      setUser({ ...user, [e.target.name]: e.target.value });
    }
  };

  const handleInputSitter = (e) => {
      
  }


  const handleSitter = () => {

  }

  const body = loading === false ?
    <div className="profileEditContainer">
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }} >
        <div style={{ fontSize: '2.5vw' }}>Общая информация</div>
        <div className="profileEditContainerInner">
          <div style={{ marginRight: '5vw' }}>
            <Image image={profilePic} width="15vw" />
            <button>Загрузить фотографию</button>
          </div>
          <div>
            Имя:
            <Input placeholder='Имя' value={user.name || ''} onChange={handleInput} name='name' />
            Фамилия:
            <Input placeholder='Фамилия' value={user.surname || ''} onChange={handleInput} name='surname' />
            Электронная почта:
            <Input placeholder='Электронная почта' value={user.mail || ''} onChange={handleInput} name='mail' />
          </div>
        </div>
        <button onClick={(e) => { console.log(user) }}>Сохранить</button>
      </div>
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <div style={{ fontSize: '2.5vw' }}>Информация сиделки</div>
        <div className="profileEditContainerInner">
          <div>
            Статус сиделки:
            <input type='checkbox' onChange={handleInput} name='sitterStatus' />
            {user.sitterStatus ? <>
              Город:
              <Input placeholder='Город' onChange={handleInput} name='city' />
              Телефон:
              <Input placeholder='Телефон' onChange={handleInput} name='mail' />
              О себе:
              <Input placeholder='О себе' onChange={handleInput} name='sitterInfoDto' />
            </>
              : ''}
            <button onClick={handleSitter}>Сохранить</button>
          </div>
        </div>
      </div>
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <div style={{ fontSize: '2.5vw' }}>Безопасность</div>
        <div className="profileEditContainerInner">
          <div>
            Новый пароль:
            <Input placeholder='Новый пароль' onChange={handleInput} name='password' />
            Старый пароль:
            <Input placeholder='Старый пароль' />

            <button>Сохранить</button>


            <button>Удалить аккаунт</button>
          </div>
        </div>
      </div>

    </div>
    :
    <div>
      loading....
    </div>

  return (
    <Template body={body} />
  )
}


export default ProfileEditPage;