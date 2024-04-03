import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    id: '',
    token: getToken(),
    userName: '',
    avatar: '',
    userTel: '',
    userLevel: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_ID: (state, id) => {
    state.id = id
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.userName = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_TEL: (state, tel) => {
    state.userTel = tel
  },
  SET_LEVEL: (state, level) => {
    state.userLevel = level
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { name, password, userLevel } = userInfo
    return new Promise((resolve, reject) => {
      login({ name: name.trim(), password: password.trim(), userLevel: parseInt(userLevel) }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          return reject('Verification failed, please Login again.')
        }
        commit('SET_ID', data.id)
        commit('SET_NAME', data.userName)
        commit('SET_TEL', data.userTel)
        commit('SET_LEVEL', data.userLevel)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

