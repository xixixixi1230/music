//左侧导航栏
const navMsg = [
    {name: '首页',path: '/'},
    {name: '歌单',path: '/song-list'},
    {name: '歌手',path: '/singer'},
    {name: '我的音乐',path: '/my-music'}
]

//右侧导航栏
const loginMsg = [
    {name: '登录',path: '/login-in'},
    {name: '注册',path: '/sign-up'}
]

//用户下拉菜单
const menuList = [
    {name: '设置',path: '/setting'},
    {name: '退出',path: 0}
]

document.addEventListener('DOMContentLoaded', function () {
  const navItems = document.querySelectorAll('.navbar li');

  navItems.forEach(item => {
    item.addEventListener('click', function () {
      // 先移除其他项目的font-enlarge类
      navItems.forEach(i => i.classList.remove('font-enlarge'));

      // 为当前点击的项目添加font-enlarge类
      this.classList.add('font-enlarge');
    });
  });
});

export {
    navMsg,
    loginMsg,
    menuList
}
