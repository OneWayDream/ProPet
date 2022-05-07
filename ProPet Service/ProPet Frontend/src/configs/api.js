const BASE_URL = 'http://localhost:8080/backend'
const JWT_URL = 'http://localhost:8081/jwt'

export default {
  URL: BASE_URL,
  JWT_URL: JWT_URL,
  SIGN_UP: BASE_URL + '/registration',
  SIGN_IN: JWT_URL + '/login-user',
  GET_USER_BY_MAIL: BASE_URL + '/user/by-mail/',
  GET_ACCESS_TOKEN: JWT_URL + '/auth-user',
}