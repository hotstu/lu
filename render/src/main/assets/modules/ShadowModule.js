
const module = (calback) => {
  return {
    pre: (e) => {console.log('pre', '渲染前')},
    post: (e) => {console.log('post', '渲染后'), calback && calback()}
  }
}

export default module
