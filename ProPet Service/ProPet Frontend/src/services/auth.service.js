import axios from "axios"
import api from "../configs/api"
import { setUser } from "../reducers/userReducer"
import { removeUserCredentials, setUserCredentials } from "./user.service"

export const registration = (login, mail, password, repeatedPassword, city, onError, onSuccess) => {
  axios.post(api.SIGN_UP, {
    login,
    mail,
    password,
    repeatedPassword,
    city
  }).then((response) => {
    onSuccess(response)
  }).catch((error) => {
    onError(error.response)
  })
}

export const authenticate = (mail, password, onError, onSuccess) => {
  axios.post(api.SIGN_IN, {
    mail,
    password
  }).then((response) => {
    axios.post(api.GET_ACCESS_TOKEN, {}, {
      headers: {
        'refresh-token': response.data.token
      }
    }).then((resp) => {
      const user = { mail: mail, refreshToken: response.data.token, accessToken: resp.data.token }
      setUserCredentials(user)
      onSuccess(resp)
    }).catch((e) => {
      onError(e.response)
    })
  }).catch((e) => {
    onError(e.response)
  })

  // return async dispatch => {
  // const response = axios.post(api.SIGN_IN, {
  //   mail,
  //   password
  // }).then((response) => {
  //   axios.post(api.GET_ACCESS_TOKEN, {}, {
  //     headers: {
  //       'refresh-token': response.data.token
  //     }
  //   }).then((resp) => {
  //     const user = { user: { mail: mail, refreshToken: response.data.token, accessToken: resp.data.token } }
  //     dispatch(setUser(user))
  //   })
  // }).catch((e) => {
  //   if (e.response) {
  //     onError(e.response)
  //   }
  // })
  // }
}

export const logout = () => {
  removeUserCredentials()
}