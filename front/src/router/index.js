import { createRouter, createWebHistory } from 'vue-router'
import TheMainView from '@/views/TheMainView.vue'
import TheOauthView from '@/views/TheOauthView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: TheMainView
    },
    {
      path: "/review",
      name: "review",
      // component: TheReviewView,
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("@/views/TheReviewView.vue"),
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
    },
    {
      path: '/auth',
      name: 'auth',
      // component: TheAuthView,
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("@/views/TheAuthView.vue"),
      redirect: { name: "login" },
      children: [
        {
          path: "login",
          name: "login",
          component: () => import("@/components/auth/LoginForm.vue"),
        },
        {
          path: "signup",
          name: "signup",
          component: () => import("@/components/auth/SignupForm.vue"),
        }, 
        {
          path: "findid",
          name: "findid",
          component: () => import("@/components/auth/FindIdForm.vue"),
        },
      ]
    },
    {
      path: '/oauth/:oauth_server_type',
      name: 'oauth',
      component: TheOauthView,
    }, {
      path: '/auth',
      name: 'auth',
      // component: TheAuthView,
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("@/views/TheAuthView.vue"),
      redirect: { name: "login" },
      children: [
        {
          path: "login",
          name: "login",
          component: () => import("@/components/auth/LoginForm.vue"),
        },
        {
          path: "signup",
          name: "signup",
          component: () => import("@/components/auth/SignupForm.vue"),
        },
        {
          path: "findid",
          name: "findid",
          component: () => import("@/components/auth/FindIdForm.vue"),
        },
        {
          path: "find-password",
          name: "find-password",
          component: () => import("@/components/auth/FindPasswordForm.vue"),
        },
      ]
    }

  ]
})

export default router
