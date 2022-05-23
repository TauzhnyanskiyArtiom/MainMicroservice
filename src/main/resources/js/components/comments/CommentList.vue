<template>
  <v-list two-line>
    <v-subheader class="text-body-1">
      Comments
    </v-subheader>

    <template
        v-for="(item, index) in comments"
    >
      <v-divider
          v-if="index > 0"
          :key="index"
      ></v-divider>

      <comment-item
          :comment="item"
          :deleteComment="deleteComment"
          :authorId="authorId"
          :key="'item' + index"
      ></comment-item>
    </template>

    <comment-form
        :message-id="messageId"
        :comments="comments"
    ></comment-form>
  </v-list>
</template>

<script>
import CommentForm from "./CommentForm.vue";
import CommentItem from "./CommentItem.vue";
import commentsApi from 'api/comments'

export default {
  name: 'CommentList',
  components: {CommentForm, CommentItem},
  props: ['comments', 'messageId', "authorId"],
  methods: {
    deleteComment(comment) {
      commentsApi.remove(comment.id).then(result => {
        if (result.ok) {
          const index = this.comments.findIndex(item => item.id === comment.id)
          if (index > -1)
            this.comments.splice(index, 1)
        }
      })
      this.message = null
    }
  },
}
</script>

<style scoped>
</style>