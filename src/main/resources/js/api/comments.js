import Vue from 'vue'

const comments = Vue.resource('/api/comments{/id}')

export default {
    add: comment => comments.save({}, comment),
    remove: id => comments.remove({id})
}