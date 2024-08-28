const user = {
  state: {
    userId: '',                 //用户id
    username: '',               //用户账号
    avator: '',               //用户头像
    isVip: undefined  //是不是vip
  },
  getters: {
    userId: state => {
      let userId = state.userId
      if (!userId) {
        userId = JSON.parse(window.sessionStorage.getItem('userId'))
      }
      return userId
    },
    username: state => {
      let username = state.username
      if (!username) {
        username = JSON.parse(window.sessionStorage.getItem('username'))
      }
      return username
    },
    avator: state => {
      let avator = state.avator
      if (!avator) {
        avator = JSON.parse(window.sessionStorage.getItem('avator'))
      }
      return avator
    },
    isVip: state => {
      let isVip = state.isVip
      if (isVip === undefined) {
        isVip = JSON.parse(window.sessionStorage.getItem('isVip'))
      }
      return isVip
    }

  },
  mutations: {
    setUserId: (state, userId) => {
      state.userId = userId
      window.sessionStorage.setItem('userId', JSON.stringify(userId))
    },
    setUsername: (state, username) => {
      state.username = username
      window.sessionStorage.setItem('username', JSON.stringify(username))
    },
    setAvator: (state, avator) => {
      state.avator = avator
      window.sessionStorage.setItem('avator', JSON.stringify(avator))
    },
    userVipStatus: (state, isVip) => {
      console.log("----设置是否会员",isVip)
      state.isVip = isVip
      window.sessionStorage.setItem('isVip', JSON.stringify(isVip))
    }
  }
}

export default user
