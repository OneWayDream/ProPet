import axios from "axios"
import api from "../configs/api"
// import customAxios from "./axios.service"

export const getUser = async (mail, token) => {
  return await axios.get(api.GET_USER_BY_MAIL + mail, {
    headers: {
      "JWT": token
    }
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