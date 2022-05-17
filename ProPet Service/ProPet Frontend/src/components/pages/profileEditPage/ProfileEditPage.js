import Template from "../template";
import Image from "../../atoms/image";
import profilePic from "../../../img/profile_pic.png";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import paths from "../../../configs/paths";
import { useEffect, useState } from "react";
import { changeCredentials, changeUserInfo, deleteAccount, getAccessToken, getUser, getUserCredentials, isAuthenticated } from "../../../services/user.service";
import Input from "../../atoms/input";
import { regexs } from "../../../configs/regexp";
import Button from "../../atoms/button";
import TextArea from "../../atoms/textArea/TextArea";

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
    if (e.target) {
      if (e.target.type === 'checkbox') {
        setSitter({ ...sitter, [e.target.name]: e.target.checked })
      } else {
        setSitter({ ...sitter, [e.target.name]: (e.target.value === "" ? null : e.target.value) })
      }
    } else {
      setSitter({ ...sitter, 'infoAbout': (e === "" ? null : e) })
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
      console.log(changedUser)
      changeUserInfo(changedUser, getAccessToken(), handleError, handleSuccesUserChanged)
    }
  }

  //Validate data
  const validate = () => {
    let errors = {}
    //Check if user want to be sitter and filled every sitter input field
    if (sitter.sitterStatus) {
      if (!user.city || !regexs.cityRegex.test(user.city)) {
        errors.city = 'Некорректный город'
      }

      if (!user.phone || user.phone.length !== 11 || !regexs.telephoneNumberRegex.test(user.phone)) {
        errors.phone = 'Некорректный телефон'
      }

      if (!sitter.age || sitter.age < 16 || sitter.age > 99 || !regexs.ageRegex.test(sitter.age)) {
        errors.age = 'Некорректный возраст'
      }

      if (!sitter.infoAbout || sitter.infoAbout.length < 50 || !regexs.aboutInfoRegex.test(sitter.infoAbout)) {
        errors.infoAbout = 'Некорректно заполнено поле "О себе". Нужно заполнить больше информации'
      }
    }

    if (user.name !== originalUser.name && !regexs.nameRegex.test(user.name)) {
      errors.name = 'Имя может содержать только русские и английские буквы, длина от 2 до 10 символов'
    }

    if (user.surname !== originalUser.surname && !regexs.surnameRegex.test(user.surname)) {
      errors.surname = 'Фамилия может содержать только русские и английские буквы, длина от 2 до 10 символов'
    }

    if (user.mail !== originalUser.mail && !regexs.emailRegex.test(user.mail)) {
      errors.mail = 'Неверная почта'
    }
    setErrorMessage(errors)
    return Object.keys(errors).length === 0
  }

  //Callback for user successfully changed
  const handleSuccesUserChanged = (response) => {
    if (user.mail !== originalUser.mail) {
      changeCredentials({ mail: user.mail })
    }
    let changedUser = user
    changedUser.sitterInfoDto = sitter
    setOriginalUser(changedUser)
    setUser(changedUser)

    setOriginalSitter(sitter)

    setLoading(false)
    alert('Изменения успешно применены')
  }

  //Deleting account
  const handleDeleteAccount = () => {
    if (window.confirm(`Вы действительно хотите покинуть наш замечательный сайт и остаться без аккаунта?`)) {
      deleteAccount(user.id, getAccessToken(), handleError, onSuccess => {
        navigate(paths.SIGN_IN, {
          state: {
            action: 'afterDeleteAccount'
          }
        })
      })
    }
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
            <div className='signInMessageError' style={{ marginLeft: '0.5vw', maxWidth: '27vw', display: errorMessage.name ? "block" : "none" }} >
              {errorMessage.name}
            </div>
            <Input placeholder='Иван' value={user.name || ''} onChange={handleUserInput} name='name' />
            Фамилия:
            <div className='signInMessageError' style={{ marginLeft: '0.5vw', maxWidth: '27vw', display: errorMessage.surname ? "block" : "none" }} >
              {errorMessage.surname}
            </div><Input placeholder='Иванов' value={user.surname || ''} onChange={handleUserInput} name='surname' />
            Электронная почта:
            <div className='signInMessageError' style={{ marginLeft: '0.5vw', maxWidth: '27vw', display: errorMessage.mail ? "block" : "none" }} >
              {errorMessage.mail}
            </div>
            <Input placeholder='ivan_ivanov@mail.ru' value={user.mail || ''} onChange={handleUserInput} name='mail' />
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
              Город:
              <div className='signInMessageError' style={{ marginLeft: '0.5vw', maxWidth: '27vw', display: errorMessage.city ? "block" : "none" }} >
                {errorMessage.city}
              </div>
              <Input placeholder='Казань' onChange={handleUserInput} name='city' value={user.city || ''} />
              Телефон:
              <div className='signInMessageError' style={{ marginLeft: '0.5vw', maxWidth: '27vw', display: errorMessage.phone ? "block" : "none" }} >
                {errorMessage.phone}
              </div>
              <Input placeholder='88005553535' onChange={handleUserInput} name='phone' value={user.phone || ''} />
              Возраст:
              <div className='signInMessageError' style={{ marginLeft: '0.5vw', maxWidth: '27vw', display: errorMessage.age ? "block" : "none" }} >
                {errorMessage.age}
              </div>
              <Input placeholder='20' onChange={handleSitterInput} name='age' value={sitter.age || ''} />
              О себе:
              <div className='signInMessageError' style={{ marginLeft: '0.5vw', maxWidth: '27vw', display: errorMessage.infoAbout ? "block" : "none" }} >
                {errorMessage.infoAbout}
              </div>
              <TextArea readOnly={false} cols={"80"} rows={"20"} onChange={handleSitterInput} name='infoAbout'>{sitter.infoAbout}</TextArea>
              {/* <Input placeholder='Очень люблю животных' onChange={handleSitterInput} name='infoAbout' value={sitter.infoAbout || ''} /> */}
            </>
              : ''}
            <Button fontSize='1.2vw' style='orange' width='10vw' onClick={handleChangeRequest}>Изменить</Button>
          </div>
        </div>
      </div>
      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <div style={{ fontSize: '2.5vw' }}>Безопасность</div>
        <div className="profileEditContainerInner">
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }} >
            Новый пароль:
            <Input placeholder='Новый пароль' onChange={handleUserInput} name='password' />
            Старый пароль:
            <Input placeholder='Старый пароль' />

            <Button fontSize='1.2vw' style='orange' width='15vw' onClick={handleDeleteAccount}>Изменить пароль</Button>
            <Button fontSize='1.2vw' style='red' width='15vw' onClick={handleDeleteAccount}>Удалить аккаунт</Button>
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