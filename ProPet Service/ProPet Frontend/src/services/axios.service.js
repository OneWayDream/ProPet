import axios from "axios";

const customAxios = () => {

  return axios

  // if (!localStorage.getItem('user')) {
  //   return axios
  // }

  // let refreshToken = JSON.parse(localStorage.getItem('user')).refreshToken
  // let accessToken = JSON.parse(localStorage.getItem('user')).accessToken
  // if (refreshToken) {
  //   if (!accessToken) {
  //     accessToken = await getAccessToken(refreshToken).then((response) => {
  //       return response.data
  //     })
  //     console.log(accessToken)
  //   }
  // } else {
  //   return axios;
  // }

  // console.log("2")

  // const options = {
  //   headers: {
  //     "JWT": accessToken
  //   }
  // }
  // let axiosInstanse = axios.create(options)
  // axiosInstanse.interceptors.request.use((config) => {
  //   config.headers.JWT = accessToken ? accessToken : ''
  //   return config
  // })

  // return axiosInstanse
}


// axios.interceptors.request.use(request => {
//   console.log('Starting Request', JSON.stringify(request, null, 2))
//   return request
// })

// const getAccessToken = aefreshToken) => {
// }

// async function getAccessToken(refreshToken) {
//   return await axios.post(api.GET_ACCESS_TOKEN,{},{
//     headers: {
//       'refresh-token': refreshToken
//     }
//   })
// }

export default customAxios();