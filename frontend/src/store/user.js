const user = {
  state: {
    userId: '',                 //用户id
    username: '',               //用户账号
    avator: '',               //用户头像
    isVip: undefined,  //是不是vip
    birth:'',
    createTime:'',
    email:'',
    introduction:'',
    location:'',
    password:'',
    phoneNum:'',
    sex:undefined,
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
    },
    birth: state => {
      let birth = state.birth
      if (!birth) {
        birth = JSON.parse(window.sessionStorage.getItem('birth'))
      }
      return birth
    },
    createTime: state => {
      let createTime = state.createTime
      if (!createTime) {
        createTime = JSON.parse(window.sessionStorage.getItem('createTime'))
      }
      return createTime
    },
    email: state => {
      let email = state.email
      if (!email) {
        email = JSON.parse(window.sessionStorage.getItem('email'))
      }
      return email
    },
    introduction: state => {
      let introduction = state.introduction
      if (!introduction) {
        introduction = JSON.parse(window.sessionStorage.getItem('introduction'))
      }
      return introduction
    },
    location: state => {
      let location = state.location
      if (!location) {
        location = JSON.parse(window.sessionStorage.getItem('location'))
      }
      return location
    },
    password: state => {
      let password = state.password
      if (!password) {
        password = JSON.parse(window.sessionStorage.getItem('password'))
      }
      return password
    },
    phoneNum: state => {
      let phoneNum = state.phoneNum
      if (!phoneNum) {
        phoneNum = JSON.parse(window.sessionStorage.getItem('phoneNum'))
      }
      return phoneNum
    },
    sex: state =>{
      let sex = state.sex
      if (!sex) {
        sex = JSON.parse(window.sessionStorage.getItem('sex'))
      }
      return sex
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
    },
    setBirth: (state, birth) => {
      state.birth = birth
      window.sessionStorage.setItem('birth', JSON.stringify(birth))
    },
    setCreateTime: (state, createTime) => {
      state.createTime = createTime
      window.sessionStorage.setItem('createTime', JSON.stringify(createTime))
    },
    setEmail: (state, email) => {
      state.email = email
      window.sessionStorage.setItem('email', JSON.stringify(email))
    },
    setIntroduction: (state, introduction) => {
      state.introduction = introduction
      window.sessionStorage.setItem('introduction', JSON.stringify(introduction))
    },
    setLocation: (state, location) => {
      state.location = location
      window.sessionStorage.setItem('location', JSON.stringify(location))
    },
    setPassword: (state, password) => {
      state.password = password
      window.sessionStorage.setItem('password', JSON.stringify(password))
    },
    setPhoneNum: (state, phoneNum) => {
      state.phoneNum = phoneNum
      window.sessionStorage.setItem('phoneNum', JSON.stringify(phoneNum))
    },
    setSex:(state,sex)=>{
      state.sex=sex
      window.sessionStorage.setItem('sex', JSON.stringify(sex))
    }

  }
}

export default user
