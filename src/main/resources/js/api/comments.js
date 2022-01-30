import Vue from 'vue'

const comments = Vue.resource('/api/comments')

export default {
    add: comment => comments.save({}, comment)
}