const h = require('snabbdom').h
const init = require('snabbdom').init
const $api = $context.getApi()
const Root = $context.getRootComponent()
const container = $api.createComponent('container')
Root.appendChild(container)
const phatomDomAPI = require('phatomDomAPI')($api)

