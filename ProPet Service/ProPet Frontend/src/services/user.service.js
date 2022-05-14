import axios from "axios"
import api from "../configs/api"

export const getUser = (mail, token, onError, onSuccess) => {
  // return await axios.get(api.GET_USER_BY_MAIL + mail, {
  // headers: {
  // "JWT": token
  // }
  // })

  axios.get(api.GET_USER_BY_MAIL + mail, {
    headers: {
      "JWT": token
    }
  }).then((response) => {
    onSuccess(response)
  }).catch((error) => {
    onError(error.response)
  })


  // axios.get(api.GET_USER_BY_MAIL + mail).then((response) => {
  // console.log("AAA " + response)
  // })
  // axios.get(api.GET_USER_BY_MAIL + mail, {
  //   headers: {
  //     "JWT": token
  //   }
  // }).then((response) => {
  //   console.log(response)
  //   return response.data
  // }).catch((e) => {
  //   console.log(e)
  // });
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

export const getFullUserInfo = (mail, JWT, onError, onSuccess) => {
  axios.get(api.GET_USER_BY_MAIL + mail, {
    headers: {
      JWT
    }
  }).then((userResponse) => {
    axios.get(api.GET_SITTER_INFO + userResponse.data.id, {
      headers: {
        JWT
      }
    }).then((sitterResponse) => {
      onSuccess({ user: userResponse.data, sitter: sitterResponse.data })
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

// axios.interceptors.request.use(request => {
//   console.log('Starting Request', JSON.stringify(request, null, 2))
//   return request
// })

// axios.interceptors.response.use(response => {
//   console.log('Response:', JSON.stringify(response, null, 2))
//   return response
// })

export const isAuthenticated = () => {
  return localStorage.getItem('user') ? true : false
}

export const getAccessToken = () => {
  return getUserCredentials().accessToken
}

export const getUserCredentials = () => {
  return isAuthenticated ? JSON.parse(localStorage.getItem('user')) : null
}

export const changeCredentials = (newUserCredentials) => {
  localStorage.setItem('user', JSON.stringify(Object.assign(JSON.parse(localStorage.getItem('user')), newUserCredentials)))
}