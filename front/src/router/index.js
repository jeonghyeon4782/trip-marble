import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/TheMainView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView
    },
    {
      path: "/review",
      name: "review",
      // component: TheReviewView,
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/TheReviewView.vue"),
      redirect: { name: "review-list" },
      children: [
        {
          path: "list",
          name: "review-list",
          component: () => import("@/components/review/ReviewList.vue"),
        },
        {
          path: "view/:reviewid",
          name: "review-view",
          component: () => import("@/components/review/ReviewDetail.vue"),
        },
        {
          path: "write",
          name: "review-write",
          component: () => import("@/components/review/ReviewWrite.vue"),
        },
        {
          path: "modify/:reviewid",
          name: "review-modify",
          component: () => import("@/components/review/ReviewModify.vue"),
        }
      ]
    }
  ]
})

export default router
