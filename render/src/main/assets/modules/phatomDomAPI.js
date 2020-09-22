    module.exports = function($api) {
    return {
                 createElement: (tag) => {
                   console.log('createElement', tag);
                   return $api.createComponent(tag)
                 },
                 createTextNode: (text) => {
                   console.log('createTextNode', text);
                   return $api.createText(text)
                 },
                 createComment: (text) => {
                   console.log('createComment', text);
                   return $api.createComment(text)
                 },
                 insertBefore: (parent, child, sibling) => {
                   console.log('insertBefore', parent, child, sibling);
                   parent.insertBefore(child, sibling)
                 },
                 removeChild: (parent, child) => {
                   console.log('removeChild', parent, child);
                   parent.removeChild(child)
                 },
                 appendChild: (parent, child) => {
                   console.log('appendChild', parent, child);
                   return parent.appendChild(child)
                 },
                 parentNode: (node) => {
                   console.log('parentNode', node);
                   return node.findParent();
                 },
                 nextSibling: (node) => {
                   console.log('nextSibling', node);
                   return node.findParent() && node.findParent().findNextSibling(node);
                 },
                 tagName: (node) => {
                   console.log('tagName', node);
                   return node.tag;
                 },
                 setTextContent: (node, text) => {
                   console.log('setTextContent', node, text);
                   $api.setTextContent(node, text)
                 },
                 getTextContent: (node) => {
                   console.log('getTextContent', node);
                   $api.getTextContent(node)
                 },
                 isElement: (node) => {
                   console.log('isElement', node);
                   return $api.isElement(node)
                 },
                 isText: (node) => {
                   console.log('isText', node);
                   return $api.isText(node)
                 },
                 isComment: (node) => {
                   console.log('isComment', node);
                   return $api.isComment(node)
                 },
               };
    }



