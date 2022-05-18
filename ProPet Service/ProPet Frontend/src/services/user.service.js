import axios from "axios"
import api from "../configs/api"

axios.interceptors.request.use(request => {
  console.log('Starting Request', JSON.stringify(request, null, 1))
  return request
})

axios.interceptors.response.use(response => {
  console.log('Response:', JSON.stringify(response, null, 1))
  
  return response
})

export const getUser = (id, token, onError, onSuccess, by = 'mail') => {
  let url;
  switch (by) {
    case 'id':
      url = api.GET_USER_BY_ID;
      break;

    case 'login':
      url = api.GET_USER_BY_LOGIN;
      break;

    case 'mail':
    default:
      url = api.GET_USER_BY_MAIL
  }

  axios.get(url + id, {
    headers: {
      "JWT": token
    }
  }).then((response) => {
    onSuccess(response)
  }).catch((error) => {
    onError(error.response)
  })
}

export const getSitterInfo = (userId, JWT, onError, onSuccess) => {
  axios.get(api.GET_SITTER_INFO + userId, {
    headers: {
      JWT
    }
  }).then((response) => {
    onSuccess(response)
  }).catch((error) => {
    onError(error.response)
  })
}

export const getFullUserInfo = (id, JWT, onError, onSuccess, by = 'mail') => {
  let url;
  switch (by) {
    case 'id':
      url = api.GET_USER_BY_ID;
      break;

    case 'login':
      url = api.GET_USER_BY_LOGIN;
      break;

    case 'mail':
    default:
      url = api.GET_USER_BY_MAIL
  }

  const header = JWT ? { headers: { JWT } } : {}

  //Get base user info
  axios.get(url + id,
    header
  ).then((userResponse) => {
    //Get comments
    axios.get(api.GET_COMMENTS_BY_ID + userResponse.data.id,
      header
    ).then((commentResponse) => {
      onSuccess({ sitter: userResponse.data, comments: commentResponse.data })
    }).catch((error) => {
      onError(error.response)
    })
  }).catch((error) => {
    onError(error.response)
  })
}

export const changeUserInfo = (user, JWT, onError, onSuccess) => {
  axios.patch(api.CHANGE_USER_INFO, user, { headers: { "JWT": JWT } }).then((response) => {
    onSuccess(response.data)
  }).catch((error) => {
    console.log(error)
    onError(error.response)
  })
}

export const changeSitterInto = (sitter, JWT, onError, onSuccess) => {
  axios.patch(api.CHANGE_SITTER_INFO, sitter, { headers: { "JWT": JWT } }).then((response) => {
    onSuccess(response.data)
  }).catch((error) => {
    onError(error.response)
  })
}

export const isAuthenticated = () => {
  return localStorage.getItem('user') ? true : false
}

export const getAccessToken = () => {
  return isAuthenticated() ? getUserCredentials().accessToken : null
}

export const getUserCredentials = () => {
  return isAuthenticated() ? JSON.parse(localStorage.getItem('user')) : null
}

export const setUserCredentials = (credentials) => {
  localStorage.setItem('user', JSON.stringify(credentials))
}

export const changeCredentials = (newUserCredentials) => {
  if (isAuthenticated()) {
    newUserCredentials = { ...getUserCredentials(), ...newUserCredentials }
    localStorage.setItem('user', JSON.stringify(newUserCredentials))
  }
}

export const removeUserCredentials = () => {
  localStorage.removeItem('user')
}

export const deleteAccount = (id, token, onError, onSuccess) => {
  // return axios.delete(api.DELETE_ACCOUNT + id, {
  //   headers: {
  //     "JWT": token
  //   }
  // })
  axios.delete(api.DELETE_ACCOUNT + id,{
    headers: {
      "JWT": token
    }
  }).then((response) => {
    removeUserCredentials()
    onSuccess(response)
  }).catch((error) => {
    onError(error) 
  })
}