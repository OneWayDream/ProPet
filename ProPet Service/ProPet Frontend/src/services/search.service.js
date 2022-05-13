import axios from "axios"
import api from "../configs/api"

export const searchSitters = (page, size, sortedBy, order, JWT, onError, onSuccess) => {
  const config = {params: {page, size, sortedBy, order}}
  if (JWT) {
    config.headers = {JWT}
  }
  axios.get(api.SEARCH_SITTERS, config)
  .then((response) => {
    onSuccess(response.data)
  }).catch((error) => {
    onError(error.response)
  })
}