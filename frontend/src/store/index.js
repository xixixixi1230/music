import Vue from 'vue'
import Vuex from 'vuex'
import configure from './configure'
import user from './user'
import song from './song'


Vue.use(Vuex)

const state = {
  lyric: [],
  // other state properties
};

const mutations = {
  setLyric(state, lyric) {
    state.lyric = lyric;
  },
  // other mutations
};

const getters = {
  lyric: state => state.lyric,
  // other getters
};

const store = new Vuex.Store({
  modules: {
    configure,
    user,
    song,
    state,
    mutations,
    getters
  }
})

export default store
