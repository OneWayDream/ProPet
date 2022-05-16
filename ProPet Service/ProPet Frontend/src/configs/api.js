const BASE_URL = 'http://localhost:8080/backend'
const JWT_URL = 'http://localhost:8081/jwt'

export default {
  //Base servers URL
  URL: BASE_URL,
  JWT_URL: JWT_URL,

  //Auth urls
  SIGN_UP: BASE_URL + '/registration',
  SIGN_IN: JWT_URL + '/login-user',

  //Get user info urls
  GET_USER_BY_MAIL: BASE_URL + '/user/by-mail/',
  GET_USER_BY_ID: BASE_URL + '/user/by-id/',
  GET_USER_BY_LOGIN: BASE_URL + '/user/by-login/',
  GET_ACCESS_TOKEN: JWT_URL + '/auth-user',
  GET_SITTER_INFO: BASE_URL + '/sitter-info/by-user-id/',
  GET_COMMENTS_BY_ID: BASE_URL + '/comment-about-sitter/by-user-id/',

  //Change user urls
  CHANGE_USER_INFO: BASE_URL + '/user',
  CHANGE_SITTER_INFO: BASE_URL + '/sitter-info',

  //Delete
  DELETE_ACCOUNT: BASE_URL + '/user/',

  //Search
  SEARCH_SITTERS: BASE_URL + '/sitter-search',
}