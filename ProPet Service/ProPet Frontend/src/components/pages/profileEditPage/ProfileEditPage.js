import Template from "../template";
import Image from "../../atoms/image";
import profilePic from "../../../img/profile_pic.png";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import paths from "../../../configs/paths";
import { useEffect, useState } from "react";
import { changeCredentials, changeUserInfo, getAccessToken, getUser, getUserCredentials, isAuthenticated } from "../../../services/user.service";
import Input from "../../atoms/input";
import { aboutInfoRegex, ageRegex, cityRegex, emailRegex, nameRegex, surnameRegex, telephoneNumberRegex } from "../../../configs/regexp";
import Button from "../../atoms/button";

const ProfileEditPage = () => {
  //some usefull hooks
  const dispatch = useDispatch()
  const navigate = useNavigate()

  //const for user that server gave
  const [originalUser, setOriginalUser] = useState()
  const [originalSitter, setOriginalSitter] = useState()

  //const for changing user info
  const [user, setUser] = useState()
  const [sitter, setSitter] = useState()

  //const for waiting server response
  const [loading, setLoading] = useState(true)

  //const for local errors
  const [errorMessage, setErrorMessage] = useState({})

  //const for errors from server
  const [errorServerMessage, setErrorServerMessage] = useState()

  //Redirect in not authenticate and get full information about user
  useEffect(() => {
    if (!isAuthenticated()) {
      navigate(paths.SIGN_IN)
    } else {
      const credentials = getUserCredentials()
      getUser(credentials.mail, credentials.accessToken, handleError, handleSuccessUserInfo)
    }
  }, [])

  //Handle some errors from server
  const handleError = (error) => {
    alert('Что-то пошло не так')
    console.log("Error: " + JSON.stringify(error))
    setLoading(false)
  }

  const handleSuccessUserInfo = (response) => {
    setOriginalUser(response.data)
    setUser(response.data)

    setOriginalSitter(response.data.sitterInfoDto)
    setSitter(response.data.sitterInfoDto)

    setLoading(false)
  }

  //Callback for Successfully get full user info
  const handleSuccessFullInfo = (response) => {
    setOriginalUser(response.user)
    setUser(response.user)

    setOriginalSitter(response.sitter)
    setSitter(response.sitter)

    setLoading(false)
  }

  //Changing user info
  const handleUserInput = (e) => {
    if (e.target.type === 'checkbox') {
      setUser({ ...user, [e.target.name]: e.target.checked })
    } else {
      setUser({ ...user, [e.target.name]: (e.target.value === "" ? null : e.target.value) });
    }
  };

  //Changeing sitter info
  const handleSitterInput = (e) => {
    if (e.target.type === 'checkbox') {
      setSitter({ ...sitter, [e.target.name]: e.target.checked })
    } else {
      setSitter({ ...sitter, [e.target.name]: (e.target.value === "" ? null : e.target.value) })
    }
  }

  //Handle update request
  const handleChangeRequest = () => {
    const userIsChanged = !(JSON.stringify(user) === JSON.stringify(originalUser));
    const sitterIsChanged = !(JSON.stringify(sitter) === JSON.stringify(originalSitter))

    if ((userIsChanged || sitterIsChanged) && validate()) {
      setLoading(true)
      let changedUser = user
      changedUser.sitterInfoDto = sitter
      setUser({ changedUser })
      changeUserInfo(user, getAccessToken(), handleError, handleSuccesUserChanged)
    }
  }

  //Validate data
  const validate = () => {
    let errors = {}

    if (user.name !== originalUser.name && !nameRegex.test(user.name)) {
      errors.name = 'Имя может содержать только русские и английские буквы'
    }

    if (user.surname !== originalUser.surname && !surnameRegex.test(user.surname)) {
      errors.surname = 'Имя может содержать только русские и английские буквы'
    }

    if (user.mail !== originalUser.mail && !emailRegex.test(user.mail)) {
      errors.mail = 'Неверная почта'
    }

    if (user.city !== originalUser.city && !cityRegex.test(user.city)) {
      errors.city = 'Некорректный город'
    }

    if (user.telephone !== originalUser.telephone && !telephoneNumberRegex.test(user.telephone)) {
      errors.telephone = 'Некорректный телефон'
    }

    if (user.age !== originalUser.age && !ageRegex.test(user.age)) {
      errors.telephone = 'Некорректный возраст'
    }

    if (sitter.aboutInfo !== originalSitter.aboutInfo && !aboutInfoRegex) {
      errors.aboutInfo = 'Некорректно заполнено поле "О себе"'
    }

    setErrorMessage(errors)
    return Object.keys(errors).length === 0
  }

  //Callback for user successfully changed
  const handleSuccesUserChanged = (response) => {
    if (user.mail !== originalUser.mail) {
      changeCredentials({ mail: user.mail })
    }
    setOriginalUser(user)
    setLoading(false)
    alert('Изменения успешно применены')
  }

  const body = loading === false ?
    <div className="profileEditContainer">
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }} >
        <div>Errors: {errorMessage ? JSON.stringify(errorMessage) : ''}</div>
        <div style={{ fontSize: '2.5vw' }}>Общая информация</div>
        <div className="profileEditContainerInner">
          <div style={{ marginRight: '5vw' }}>
            <Image image={profilePic} width="15vw" />
            <button>Загрузить фотографию</button>
          </div>
          <div>
            Имя:
            <Input placeholder='Имя' value={user.name || ''} onChange={handleUserInput} name='name' />
            Фамилия:
            <Input placeholder='Фамилия' value={user.surname || ''} onChange={handleUserInput} name='surname' />
            Электронная почта:
            <Input placeholder='Электронная почта' value={user.mail || ''} onChange={handleUserInput} name='mail' />
          </div>
        </div>
        <Button fontSize='1.2vw' style='orange' width='10vw' onClick={handleChangeRequest}>Изменить</Button>
      </div>
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <div style={{ fontSize: '2.5vw', paddingTop: '2vw' }}>Информация сиделки</div>
        <div className="profileEditContainerInner">
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }} >
            <div style={{ paddingBottom: '1.5vw' }}>
              Статус сиделки:
              <input type='checkbox' onChange={handleSitterInput} checked={sitter.sitterStatus} name='sitterStatus' />
            </div>
            {sitter.sitterStatus ? <>
              <div></div>
              Город:
              <Input placeholder='Город' onChange={handleUserInput} name='city' />
              Телефон:
              <Input placeholder='Телефон' onChange={handleUserInput} name='phone' />
              Возраст
              <Input placeholder='Возраст' onChange={handleSitterInput} name='age' />
              О себе:
              <Input placeholder='О себе' onChange={handleSitterInput} name='infoAbout' />
            </>
              : ''}
            <Button fontSize='1.2vw' style='orange' width='10vw' onClick={handleChangeRequest}>Изменить</Button>
          </div>
        </div>
      </div>
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <div style={{ fontSize: '2.5vw' }}>Безопасность</div>
        <div className="profileEditContainerInner">
          <div style={{ disply: 'flex', flexDirection: 'column' }} >
            Новый пароль:
            <Input placeholder='Новый пароль' onChange={handleUserInput} name='password' />
            Старый пароль:
            <Input placeholder='Старый пароль' />

            <button>Сохранить</button>
            <button>Удалить аккаунт</button>
          </div>
        </div>
      </div>

    </div >
    :
    <div>
      loading....
    </div>

  return (
    <Template body={body} />
  )
}


export default ProfileEditPage;