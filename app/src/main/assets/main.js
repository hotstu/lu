
console.log('Main start！！')
const h = require('snabbdom').h
const init = require('snabbdom').init
const $api = $context.getApi()
const Root = $context.getRootComponent()
const container = $api.createComponent('container')
Root.appendChild(container)
const phatomDomAPI = require('phatomDomAPI')($api)
const styles = {
    'header-title-o': {
        background: '#1293ea',
        height: '56',
        orientation: 'horizontal'
    },
    'header-d': {
        background: '#1293ea',
        height: '56',
        orientation: 'horizontal'
    },
    'row': {
        orientation: 'horizontal',
        height: '32',
    },
    'header-content-detail': {
        orientation: 'horizontal'
    },
    'rank': {
        width: '32',
        gravity: 'center',
        orientation: 'horizontal'
    },
    'close': {
        width: '32',
        gravity: 'center',
        orientation: 'horizontal'

    }
}

$context.attachStyles(styles)

var vnode;
const patch = init([
  require('snabbdom-eventlisteners').default,
//  require('snabbdom-class').default,
//  require('snabbdom-props').default,
], phatomDomAPI);



var data = {
  selected: undefined,
  entries: [
    {
      rank: 1,
      title: '[Lu]是一款我用业余时间开发的',
      desc: 'Lorem ipsum dolor sit amet, sed pede integer vitae bibendum, accumsan sit, vulputate aenean tempora ipsum. Lorem sed id et metus, eros posuere suspendisse nec nunc justo, fusce augue placerat nibh purus suspendisse. Aliquam aliquam, ut eget. Mollis a eget sed nibh tincidunt nec, mi integer, proin magna lacus iaculis tortor. Aliquam vel arcu arcu, vivamus a urna fames felis vel wisi, cursus tortor nec erat dignissim cras sem, mauris ac venenatis tellus elit.'
    },
    {
      rank: 2,
      title: '类似[Weex]、[ReactNative]的',
      desc: 'Consequuntur ipsum nulla, consequat curabitur in magnis risus. Taciti mattis bibendum tellus nibh, at dui neque eget, odio pede ut, sapien pede, ipsum ut. Sagittis dui, sodales sem, praesent ipsum conubia eget lorem lobortis wisi.'
    },
    {
      rank: 3,
      title: '基于virtualDom的原生界面渲染框架',
      desc: 'Quam lorem aliquam fusce wisi, urna purus ipsum pharetra sed, at cras sodales enim vestibulum odio cras, luctus integer phasellus.'
    },
    {
      rank: 4,
      title: '可以使用Js渲染我们的界面并处理各种事件',
      desc: 'Et orci hac ultrices id in. Diam ultrices luctus egestas, sem aliquam auctor molestie odio laoreet. Pede nam cubilia, diam vestibulum ornare natoque, aenean etiam fusce id, eget dictum blandit et mauris mauris. Metus amet ad, elit porttitor a aliquet commodo lacus, integer neque imperdiet augue laoreet, nonummy turpis lacus sed pulvinar condimentum platea. Wisi eleifend quis, tristique dictum, ac dictumst. Sem nec tristique vel vehicula fringilla, nibh eu et posuere mi rhoncus.'
    },
    {
      rank: 5,
      title: '设计开发理念是极简+探索',
      desc: 'Pede nam cubilia, diam vestibulum ornare natoque, aenean etiam fusce id, eget dictum blandit et mauris mauris. Metus amet ad, elit porttitor a aliquet commodo lacus, integer neque imperdiet augue laoreet, nonummy turpis lacus sed pulvinar condimentum platea. Wisi eleifend quis, tristique dictum, ac dictumst. Sem nec tristique vel vehicula fringilla, nibh eu et posuere mi rhoncus.'
    },
    {
      rank: 6,
      title: '但是总体架构我还是满意的',
      desc: 'Sapien laoreet, ligula elit tortor nulla pellentesque, maecenas enim turpis, quae duis venenatis vivamus ultricies, nunc imperdiet sollicitudin ipsum malesuada. Ut sem. Wisi fusce nullam nibh enim. Nisl hymenaeos id sed sed in. Proin leo et, pulvinar nunc pede laoreet.'
    },
    {
      rank: 7,
      title: '这个项目不仅实现了virtualDom的渲染引擎',
      desc: 'Accumsan quia, id nascetur dui et congue erat, id excepteur, primis ratione nec. At nulla et. Suspendisse lobortis, lobortis in tortor fringilla, duis adipiscing vestibulum voluptates sociosqu auctor.'
    },
    {
      rank: 8,
      title: '还实现了基于web的原生界面编辑器,封装了Rhino Js引擎',
      desc: 'Ante tellus egestas vel hymenaeos, ut viverra nibh ut, ipsum nibh donec donec dolor. Eros ridiculus vel egestas convallis ipsum, commodo ut venenatis nullam porta iaculis, suspendisse ante proin leo, felis risus etiam.'
    },
    {
      rank: 9,
      title: '可以在浏览器中编辑界面在手机中实时看到渲染效果',
      desc: 'Metus amet ad, elit porttitor a aliquet commodo lacus, integer neque imperdiet augue laoreet, nonummy turpis lacus sed pulvinar condimentum platea. Wisi eleifend quis, tristique dictum, ac dictumst.'
    },
    {
      rank: 10,
      title: '目前处理css还有些问题，支持的控件还不够多',
      desc: 'Et orci hac ultrices id in. Diam ultrices luctus egestas, sem aliquam auctor molestie odio laoreet. Pede nam cubilia, diam vestibulum ornare natoque, aenean etiam fusce id, eget dictum blandit et mauris mauris'
    },
  ]
};

function select(m) {
  data.selected = m;
  render();
}

function render() {
  vnode = patch(vnode, view(data));
}

const fadeInOutStyle = {
  opacity: '0', delayed: {opacity: '1'}, remove: {opacity: '0'}
};

const detailView = (movie) =>
  h('div.page', {style: fadeInOutStyle}, [
    h('div.header-d', [
      h('div.header-content-detail', {
        style: {opacity: '1', remove: {opacity: '0'}},
      }, [
        h('div.rank', [
          h('span.header-rank.hero', {hero: {id: 'rank' + movie.rank}}, movie.rank),
          h('div.rank-circle', {
            style: {
              transform: 'scale(0)',
              delayed: {transform: 'scale(1)'},
              destroy: {transform: 'scale(0)'}
            },
          }),
        ]),
        h('div.hero.header-title', {hero: {id: movie.title}}, movie.title),
        h('div.spacer'),
        h('div.close', {
          on: {click: [select, undefined]},
          style: {
            transform: 'scale(0)',
            delayed: {transform: 'scale(1)'},
            destroy: {transform: 'scale(0)'}
          },
        }, 'x'),
      ]),
    ]),
    h('div.page-content', [
      h('div.desc', {
        style: {
          opacity: '0', transform: 'translateX(3em)',
          delayed: {opacity: '1', transform: 'translate(0)'},
          remove: {
            opacity: '0', position: 'absolute', top: '0', left: '0',
            transform: 'translateX(3em)'
          }
        }
      }, [
        h('h2', 'Description:'),
        h('span', movie.desc),
      ]),
    ]),
  ]);

const overviewView = (entries) =>
  h('div.page', {style: fadeInOutStyle}, [
    h('div.header', [
      h('div.header-content.overview', {
        style: fadeInOutStyle,
      }, [
        h('div.header-title-o', {
          style: {
            transform: 'translateY(-2em)',
            delayed: {transform: 'translate(0)'},
            destroy: {transform: 'translateY(-2em)'}
          }
        }, 'Lu简介'),
        h('div.spacer'),
      ]),
    ]),
    h('!', '注释:'),
    h('div.page-content', [
      h('div.list', {
        style: {
          opacity: '0', delayed: {opacity: '1'},
          remove: {opacity: '0', position: 'absolute', top: '0', left: '0'}
        }
      }, entries.map((movie) =>
        h('div.row', {
          on: {click: () => select(movie)},
        }, [
          h('div.hero.rank', [
            h('span.hero', {hero: {id: 'rank' + movie.rank}}, movie.rank)
          ]),
          h('div.hero', {hero: {id: movie.title}}, movie.title)
        ])
      )),
    ]),
  ]);

const view = (data) =>
  h('div.page-container', [
    data.selected ? detailView(data.selected) : overviewView(data.entries),
  ]);


vnode = patch(container, view(data));
$api.debug(vnode)
