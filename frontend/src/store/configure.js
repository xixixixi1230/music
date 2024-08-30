const SHOW_PLAYER_KEY = 'showPlayer'
const configure = {
  state: {
    HOST: 'http://192.168.134.90:9005/my-bucket',  //后台访问地址根目录
    activeName: '',                  //当前选中的菜单名
    showAside: false,               //是否显示播放中的歌曲列表
    loginIn: false,                 //用户是否已登录
    isActive: false,                //当前歌曲是否已收藏
    showPlayer: false  //是否显示播放器
  },
  getters: {
    playerStatus: state => {
      let showPlayer = state.showPlayer
      if (showPlayer === undefined) {
        showPlayer = JSON.parse(window.sessionStorage.getItem(SHOW_PLAYER_KEY))
      }
      return showPlayer
    },
    activeName: state => {
      let activeName = state.activeName
      if (!activeName) {
        activeName = JSON.parse(window.sessionStorage.getItem('activeName'))
      }
      return activeName
    },
    showAside: state => {
      let showAside = state.showAside
      if (!showAside) {
        showAside = JSON.parse(window.sessionStorage.getItem('showAside'))
      }
      return showAside
    },
    loginIn: state => {
      let loginIn = state.loginIn
      if (!loginIn) {
        loginIn = JSON.parse(window.sessionStorage.getItem('loginIn'))
      }
      return loginIn
    },
    isActive: state => {
      let isActive = state.isActive
      if (!isActive) {
        isActive = JSON.parse(window.sessionStorage.getItem('isActive'))
      }
      return isActive
    }
  },
  mutations: {
    //修改播放器显示状态
    updPlayerStatus: (state, status) => {
      state.showPlayer = status
      window.sessionStorage.setItem(SHOW_PLAYER_KEY, JSON.stringify(status))
    },
    setActiveName: (state, activeName) => {
      state.activeName = activeName
      window.sessionStorage.setItem('activeName', JSON.stringify(activeName))
    },
    setShowAside: (state, showAside) => {
      state.showAside = showAside
      window.sessionStorage.setItem('showAside', JSON.stringify(showAside))
    },
    setLoginIn: (state, loginIn) => {
      state.loginIn = loginIn
      window.sessionStorage.setItem('loginIn', JSON.stringify(loginIn))
    },
    setIsActive: (state, isActive) => {
      state.isActive = isActive
      window.sessionStorage.setItem('isActive', JSON.stringify(isActive))
    }
  }
}

export default configure
