const SET_USER = "SET_USER"
const LOGOUT = "LOGOUT"

const defultState = {
  currentUser: {},
  isAuth: false,
}

const userReducer = (state = defultState, action) => {
  switch (action.type) {
    case SET_USER:
      localStorage.setItem('user', JSON.stringify(action.payload.user))
    return {
      ...state,
      currentUser: action.payload.user,
      isAuth: true
    }
    case LOGOUT:
      localStorage.removeItem('user')
    return {
      ...state,
      currentUser: {},
      isAuth: false
    }
    default:
      return state
  }
}


export const setUser = (user) => ({ type: SET_USER, payload: user })
export const logout = () => ({ type: LOGOUT })

export default userReducer