import Axios from 'axios'
import {get, post} from './http'

//============歌手相关================
//查询歌手
export const getAllSinger = () => get(`/singer`)
//根据性别查询歌手
export const getSingerOfSex = (sex) => get(`/singer/sex/detail?sex=${sex}`)

//============歌曲相关================
// 根据歌曲名字查询歌曲
export const songOfName = (name) => get(`/song/name/detail?name=${name}`)
//根据歌手id查询歌曲
export const songOfSingerId = (id) => get(`/song/singerId/detail?singerId=${id}`)
//根据歌曲id查询歌曲对象
export const songOfSongId = (id) => get(`/song/detail?id=${id}`)
//根据歌手名字模糊查询歌曲
export const likeSongOfName = (keywords) => get(`/song/singerName/detail?songName=${keywords}`)
//根据歌曲id增加歌曲播放次数
export const addSongNums = (id) => get(`song/addNums?songId=${id}`)
//查询歌单
export const topSong = () => get(`/song`)
//查询推荐歌曲
export const topRecommend = (userId) => get(`/collect/topRecommendOfUserId?userId=${userId}`)

//============歌单相关================
// 获取前10火的歌单
export const topSongList = () => get('/top10')
// 根据歌单ID获取歌曲ID列表
export const getSongIdBySongListId = (songListId) => get('/listSong/detail')
// 获取指定歌单的评分
export const getSongListScore = (songListId) => get(`/rankList?songListId=${songListId}`)
// 提交歌单评分
export const songListRank = (params) => post('/rankList/add',params)
// 获取指定用户对指定歌单的评分
export const getUserRank = (songListId,consumerId) => get(`/rankList/user?songListId=${songListId}&consumerId=${consumerId}`)
//查询歌单
export const getAllSongList = () => get(`/songList`)
//返回标题包含文字的歌单列表
export const getSongListOfLikeTitle = (keywords) => get(`/songList/title/detail?title=${keywords}`)
//根据风格模糊查询歌单列表
export const getSongListOfLikeStyle = (style) => get(`/songList/style/detail?style=${style}`)

//============歌单的歌曲相关============
//根据歌单id查询歌曲列表
export const listSongDetail = (songListId) => get(`/listSong/detail?songListId=${songListId}`)

//============用户相关================
//查询用户
export const getAllConsumer = () => get(`consumer/allConsumer`)
//注册
export const SignUp = (params) => post(`/user/add`, params)
//登录
export const loginIn = (params) => post(`/login`, params)
//根据用户id查询该用户的详细信息
export const getUserOfId = (userId) => get(`/user/detail?userId=${userId}`)
//更新用户信息
export const updateUserMsg = (params) => post(`/user/update`, params)
//更新用户头像
export const updateUserAvator = (params) => post(`/user/avatar/update`, params)
//使用手机号码进行登录
export const loginWithPhoneNum = (phoneNum) => post(`/consumer/${phoneNum}`)
//退出登录
export  const  logout = ()=>post(`/consumer/logout`)

//下载音乐
export const download = (url) => Axios({
  method: 'get',
  url: url,
  responseType: 'blob'
})

// 根据用户ID获取收藏列表
export const getCollectById = (userId) => get(`/collection/detail?userId=${userId}`)

//===========评价======================
//提交评分
export const setRank = (params) => post(`/rankList/add`, params)
//获取指定歌单的平均分
export const getRankOfSongListId = (songListId) => get(`/rankList?songListId=${songListId}`)
// 修改评价
export const modifyRank = (params) => post('/rankList/modify',params)

//===========评论======================
//提交评论
export const setComment = (params) => post(`/comment/add`, params)
//点赞
export const setLike = (params) => post(`/comment/like`,params)
//返回当前歌单或歌曲的评论列表
export const getAllComment = (type, id) => {
  if (type == 0) {              //歌曲
    return get(`/comment/song/detail?songId=${id}`)
  } else {                      //歌单
    return get(`/comment/songList/detail?songListId=${id}`)
  }
}

//===============收藏===================
//新增收藏
export const setCollect = (params) => post(`/collect/add`, params)
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`)
